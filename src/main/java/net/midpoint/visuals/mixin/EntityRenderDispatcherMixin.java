package net.midpoint.visuals.mixin;

import net.midpoint.visuals.module.HitboxModule;
import net.midpoint.visuals.module.ModuleManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "renderHitbox", at = @At("HEAD"), cancellable = true)
    private static void renderCustomHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, Box box, float tickDelta, float red, float green, float blue, CallbackInfo ci) {
        HitboxModule mod = ModuleManager.get(HitboxModule.class);
        if (mod != null && mod.isEnabled()) {
            int color = mod.getColorRGBA();
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            float a = ((color >> 24) & 0xFF) / 255.0f;
            
            // Перерисовываем хитбокс сущности в кастомный цвет мода Midpoint Visuals
            net.minecraft.client.render.WorldRenderer.drawBox(matrices, vertices, box, r, g, b, a);
            ci.cancel(); // Отключаем стандартный белый хитбокс майна
        }
    }
}
