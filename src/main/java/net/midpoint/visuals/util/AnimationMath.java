package net.midpoint.visuals.util;

import net.minecraft.util.math.MathHelper;

public class AnimationMath {
    public static float lerp(float current, float target, float speed, float delta) {
        return current + (target - current) * MathHelper.clamp(speed * delta, 0.0f, 1.0f);
    }
    
    public static float easeInOut(float value) {
        return value < 0.5f ? 4.0f * value * value * value : 1.0f - (float) Math.pow(-2.0f * value + 2.0f, 3.0f) / 2.0f;
    }
}
