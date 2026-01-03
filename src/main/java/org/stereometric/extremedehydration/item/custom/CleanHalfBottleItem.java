package org.stereometric.extremedehydration.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
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
import org.stereometric.extremedehydration.network.thirst.ThirstNetworking;
import org.stereometric.extremedehydration.util.thirst.ThirstHolder;

public class CleanHalfBottleItem extends Item {
    public CleanHalfBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        HitResult hit = user.raycast(2.7, 1.0f, true);

        if (hit.getType() == HitResult.Type.MISS) {
            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            user.setCurrentHand(hand);
            return ActionResult.CONSUME;
        }

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos pos = blockHit.getBlockPos();
            FluidState fluid = world.getFluidState(pos);

            if (fluid.isIn(FluidTags.WATER)) {

                user.setStackInHand(hand, new ItemStack(ModItems.BOTTLE));
                user.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            } else {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                user.setCurrentHand(hand);
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 50;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && user instanceof PlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            int thirst = ((ThirstHolder) player).getThirst();
            ((ThirstHolder) player).setThirst(thirst + 4);
            ThirstNetworking.sync(player);
        }

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            return new ItemStack(ModItems.EMPTY_BOTTLE);
        }

        return new ItemStack(ModItems.CLEAN_HALF_WATER_BOTTLE);
    }
}
