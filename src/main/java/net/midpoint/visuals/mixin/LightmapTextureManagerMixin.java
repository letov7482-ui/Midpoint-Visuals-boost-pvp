package net.midpoint.visuals.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;set(FFF)Lorg/joml/Vector3f;", remap = false))
    private void injectFullbright(Args args) {
        // Выкручиваем каналы яркости RGB света в текстурной карте на максимум (1.0f)
        args.set(0, 1.0f);
        args.set(1, 1.0f);
        args.set(2, 1.0f);
    }
}
