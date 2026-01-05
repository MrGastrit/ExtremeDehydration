package org.stereometric.extremedehydration;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stereometric.extremedehydration.effect.ModEffects;
import org.stereometric.extremedehydration.item.ModItems;
import org.stereometric.extremedehydration.item.ModItemsGroups;
import org.stereometric.extremedehydration.network.thirst.ThirstNetworking;
import org.stereometric.extremedehydration.network.thirst.ThirstSyncPayload;
import org.stereometric.extremedehydration.util.thirst.ThirstHolder;

public class Extremedehydration implements ModInitializer {
    public static final String MOD_ID = "extremedehydration";
    public static Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModItemsGroups.registerItemGroups();
        ModEffects.registerEffects();

        PayloadTypeRegistry.playS2C().register(
                ThirstSyncPayload.ID,
                ThirstSyncPayload.CODEC
        );

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != Hand.MAIN_HAND || !player.isSneaking()) return ActionResult.PASS;
            return handleWaterInteraction(player, world, hand);
        });
    }

    private ActionResult handleWaterInteraction(PlayerEntity player, World world, Hand hand) {

        if (!player.getStackInHand(hand).isEmpty()) {
            return ActionResult.PASS;
        }

        HitResult hit = player.raycast(4.0D, 0.0F, true);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (!state.getFluidState().isIn(FluidTags.WATER)) {
            return ActionResult.PASS;
        }

        if (world.isClient()) {
            player.swingHand(hand);

            world.addParticleClient(ParticleTypes.SPLASH, player.getX(), player.getY() + 1.0, player.getZ(), 0, 0.1, 0);

            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);

            return ActionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return ActionResult.PASS;
        }

        ThirstHolder thirst = (ThirstHolder) serverPlayer;

        if (thirst.getThirst() >= 20) {
            serverPlayer.sendMessage(Text.literal("Вам пока незачем пить"), true);
            return ActionResult.SUCCESS;
        }

        thirst.setThirst(Math.min(20, thirst.getThirst() + 2));
        ThirstNetworking.sync(serverPlayer);

        serverPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.THIRST, 400));

        return ActionResult.SUCCESS;
    }
}
