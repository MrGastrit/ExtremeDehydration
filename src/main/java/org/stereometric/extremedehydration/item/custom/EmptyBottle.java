package org.stereometric.extremedehydration.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.stereometric.extremedehydration.effect.ModEffects;
import org.stereometric.extremedehydration.item.ModItems;

public class EmptyBottle extends Item {
    public EmptyBottle(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        HitResult hit = user.raycast(2.7, 1.0f, true);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos pos = blockHit.getBlockPos();

        if (!world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return ActionResult.PASS;
        }

        if (!world.isClient()) {
            user.giveItemStack(new ItemStack(ModItems.HALF_BOTTLE));
            user.getStackInHand(hand).decrement(1);
            user.getEntityWorld().playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        user.getEntityWorld().playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            return new ItemStack(ModItems.BOTTLE);
        }

        return new ItemStack(ModItems.BOTTLE);
    }
}
