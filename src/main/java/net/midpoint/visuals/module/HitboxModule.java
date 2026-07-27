package net.midpoint.visuals.module;

import net.midpoint.visuals.setting.ModeSetting;
import net.midpoint.visuals.setting.NumberSetting;

public class HitboxModule extends Module {
    public final ModeSetting color = new ModeSetting("Цвет Хитбокса", "Красный", "Белый", "Желтый", "Синий", "Фиолетовый", "Черный");
    public final NumberSetting alpha = new NumberSetting("Яркость хитбокса", 0.4, 0.1, 1.0);

    public HitboxModule() {
        super("Hitbox Customizer", true);
        addSetting(color);
        addSetting(alpha);
    }

    public int getColorRGBA() {
        int a = (int) (alpha.getValue() * 255) << 24;
        return switch (color.getValue()) {
            case "Белый" -> a | 0xFFFFFF;
            case "Желтый" -> a | 0xFFFF00;
            case "Синий" -> a | 0x0000FF;
            case "Фиолетовый" -> a | 0x9400D3;
            case "Черный" -> a | 0x111111;
            default -> a | 0xFF0000; // Красный
        };
    }
}
