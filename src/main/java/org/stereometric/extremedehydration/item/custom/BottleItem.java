package org.stereometric.extremedehydration.item.custom;

import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.stereometric.extremedehydration.effect.ModEffects;
import org.stereometric.extremedehydration.item.ModItems;
import org.stereometric.extremedehydration.network.thirst.ThirstNetworking;
import org.stereometric.extremedehydration.util.thirst.ThirstHolder;

public class BottleItem extends Item {
    public BottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
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
            user.addStatusEffect(new StatusEffectInstance(ModEffects.THIRST, 300, 0, false, false));

            ServerPlayerEntity player = (ServerPlayerEntity) user;
            int thirst = ((ThirstHolder) player).getThirst();
            ((ThirstHolder) player).setThirst(thirst + 2);
            ThirstNetworking.sync(player);
        }

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            return new ItemStack(ModItems.HALF_BOTTLE);
        }

        return new ItemStack(ModItems.BOTTLE);
    }
}
