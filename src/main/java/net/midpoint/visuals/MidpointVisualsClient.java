package net.midpoint.visuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.midpoint.visuals.module.BlockOverlayModule;
import net.midpoint.visuals.module.ModuleManager;
import net.midpoint.visuals.util.AnimationMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import com.mojang.blaze3d.systems.RenderSystem;

public class MidpointVisualsClient implements ClientModInitializer {
    private float hudAlpha = 0.0f;
    private float watermarkY = -20.0f;
    public static float zoomProgress = 0.0f;

    @Override
    public void onInitializeClient() {
        ModuleManager.init();

        // 1. Рендеринг Ватермарка на экране (HudRenderCallback)
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.options.hudHidden || client.currentScreen != null) return;

            float delta = renderTickCounter.getTickDelta(true);
            hudAlpha = AnimationMath.lerp(hudAlpha, 255.0f, 0.08f, delta);
            watermarkY = AnimationMath.lerp(watermarkY, 10.0f, 0.08f, delta);

            renderModernWatermark(drawContext, client, (int) watermarkY, (int) hudAlpha);
        });

        // 2. Рендеринг продвинутого Block Overlay в 3D мире
        WorldRenderEvents.END.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            BlockOverlayModule mod = ModuleManager.get(BlockOverlayModule.class);
            if (!mod.isEnabled() || client.crosshairTarget == null) return;

            if (client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hit = (BlockHitResult) client.crosshairTarget;
                BlockPos pos = hit.getBlockPos();
                
                MatrixStack matrices = context.matrixStack();
                Box box = new Box(pos).offset(context.camera().getPos().negate());
                
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableCull();

                int color = mod.getColorRGBA();
                float r = ((color >> 16) & 0xFF) / 255.0f;
                float g = ((color >> 8) & 0xFF) / 255.0f;
                float b = (color & 0xFF) / 255.0f;
                float a = ((color >> 24) & 0xFF) / 255.0f;

                VertexConsumerProvider.Immediate consumers = client.getBufferBuilders().getEntityVertexConsumers();
                VertexConsumer lineBuffer = consumers.getBuffer(RenderLayer.getLines());
                
                // Отрисовка красивых внешних контуров блока
                WorldRenderer.drawBox(matrices, lineBuffer, box, r, g, b, a);
                consumers.draw();
                
                RenderSystem.enableCull();
                RenderSystem.disableBlend();
            }
        });
    }

    private void renderModernWatermark(DrawContext context, MinecraftClient client, int y, int alpha) {
        String text = "Midpoint Visuals | " + client.getCurrentFps() + " FPS | " + client.getSession().getUsername();
        int width = client.textRenderer.getWidth(text) + 12;
        int x = 10;

        RenderSystem.enableBlend();
        int bg = ((alpha / 2) << 24) | (160 << 16) | (10 << 8) | 10; // Стильный красный полупрозрачный фон
        int border = (alpha << 24) | (240 << 16) | (20 << 8) | 20;   // Яркая неоново-красная окантовка

        context.fill(x, y, x + width, y + 16, bg);
        context.drawBorder(x, y, width, 16, border);
        context.drawText(client.textRenderer, text, x + 6, y + 4, (alpha << 24) | 0xFFFFFF, false);
        RenderSystem.disableBlend();
    }
          }
