package org.stereometric.extremedehydration.util.thirst;

public interface PlayerEntityMixinAccessor {
    void incThirstTimer();
    int getThirstTimer();
    void resetThirstTimer();
}
