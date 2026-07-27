package net.midpoint.visuals.module;

import net.midpoint.visuals.setting.ModeSetting;
import net.midpoint.visuals.setting.NumberSetting;

public class BlockOverlayModule extends Module {
    public final ModeSetting colorMode = new ModeSetting("Цвет", "Белый", "Белый", "Желтый", "Красный", "Фиолетовый");
    public final NumberSetting alpha = new NumberSetting("Прозрачность", 0.4, 0.0, 1.0);
    public final NumberSetting thickness = new NumberSetting("Толщина линий", 2.0, 1.0, 5.0);

    public BlockOverlayModule() {
        super("Block Overlay", true);
        addSetting(colorMode);
        addSetting(alpha);
        addSetting(thickness);
    }

    public int getColorRGBA() {
        int a = (int) (alpha.getValue() * 255) << 24;
        return switch (colorMode.getValue()) {
            case "Желтый" -> a | (255 << 16) | (255 << 8) | 0;
            case "Красный" -> a | (255 << 16) | (0 << 8) | 0;
            case "Фиолетовый" -> a | (180 << 16) | (0 << 8) | 255;
            default -> a | (255 << 16) | (255 << 8) | 255; // Белый
        };
    }
}
