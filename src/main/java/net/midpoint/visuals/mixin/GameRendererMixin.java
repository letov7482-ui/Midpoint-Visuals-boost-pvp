package net.midpoint.visuals.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void injectZoomFov(CallbackInfoReturnable<Double> info) {
        MinecraftClient client = MinecraftClient.getInstance();
        // В Майнкрафте 1.21.4 отслеживаем зажатие клавиши 'C' (Классический зум)
        boolean isZooming = client.options.zoomKey.isPressed();
        
        if (isZooming) {
            net.midpoint.visuals.MidpointVisualsClient.zoomProgress = net.midpoint.visuals.util.AnimationMath.lerp(
                net.midpoint.visuals.MidpointVisualsClient.zoomProgress, 1.0f, 0.1f, 1.0f
            );
        } else {
            net.midpoint.visuals.MidpointVisualsClient.zoomProgress = net.midpoint.visuals.util.AnimationMath.lerp(
                net.midpoint.visuals.MidpointVisualsClient.zoomProgress, 0.0f, 0.1f, 1.0f
            );
        }

        if (net.midpoint.visuals.MidpointVisualsClient.zoomProgress > 0.0f) {
            double currentFov = info.getReturnValue();
            // Плавное приближение через кубическую кривую интерполяции
            float factor = net.midpoint.visuals.util.AnimationMath.easeInOut(net.midpoint.visuals.MidpointVisualsClient.zoomProgress);
            double targetFov = currentFov * (1.0 - (factor * 0.75)); // Приближение на 75%
            info.setReturnValue(targetFov);
        }
    }
}
