package org.stereometric.extremedehydration.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.stereometric.extremedehydration.effect.ModEffects;
import org.stereometric.extremedehydration.network.thirst.ThirstNetworking;
import org.stereometric.extremedehydration.util.thirst.ThirstHolder;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ThirstHolder {

    @Unique
    private int thirst = 20;
    @Unique
    private float thirstExhaustion = 0f;
    @Unique
    private int thirstTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickThirst(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (player.getEntityWorld().isClient()) return;
        if (player.getAbilities().creativeMode || player.isSpectator()) return;

        thirstTimer++;

        if (thirstTimer % 20 == 0) {
            ThirstNetworking.sync(player);
            
            
        }

        if (player.getActiveStatusEffects().containsKey(ModEffects.THIRST)) {

            if (thirstTimer % 40 == 0) {
                thirstExhaustion += 0.2f;
            }

            if (player.isSprinting()) {
                thirstExhaustion += 0.37f;
            }

            if (player.isSwimming()) {
                thirstExhaustion += 0.3f;
            }

            if (player.fallDistance > 0f) {
                thirstExhaustion += 0.32f;
            }
        } else {

            if (thirstTimer % 40 == 0) {
                thirstExhaustion += 0.02f;
            }

            if (player.isSprinting()) {
                thirstExhaustion += 0.07f;
            }

            if (player.isSwimming()) {
                thirstExhaustion += 0.05f;
            }

            if (player.fallDistance > 0f) {
                thirstExhaustion += 0.06f;
            }
        }

        if (thirstExhaustion >= 25.0f) {
            thirstExhaustion -= 25.0f;
            thirst = Math.max(thirst - 1, 0);
            ThirstNetworking.sync(player);
        }
    }

    @Override
    public int getThirst() {
        return thirst;
    }

    @Override
    public void setThirst(int value) {
        thirst = MathHelper.clamp(value, 0, 20);
    }

    @Override
    public float getThirstExhaustion() {
        return thirstExhaustion;
    }

    @Override
    public void addThirstExhaustion(float value) {
        thirstExhaustion += value;
    }

    @Inject(method = "writeCustomData", at = @At("HEAD"))
    private void writeThirst(WriteView view, CallbackInfo ci) {
        view.putInt("ExtremeDehydrationThirst", thirst);
        view.putFloat("ThirstExhaustion", thirstExhaustion);
    }

    @Inject(method = "readCustomData", at = @At("HEAD"))
    private void readThirst(ReadView view, CallbackInfo ci) {
        if (view.contains("ExtremeDehydrationThirst")) {
            thirst = view.getInt("ExtremeDehydrationThirst", 20);
            thirstExhaustion = view.getFloat("ThirstExhaustion", 0.0f);
        }
    }
}
