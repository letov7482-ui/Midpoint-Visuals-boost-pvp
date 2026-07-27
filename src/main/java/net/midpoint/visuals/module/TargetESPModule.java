package net.midpoint.visuals.module;

import net.midpoint.visuals.setting.ModeSetting;
import net.midpoint.visuals.setting.NumberSetting;

public class TargetESPModule extends Module {
    public final ModeSetting colorMode = new ModeSetting("Цвет", "Неон", "Неон", "Радуга", "Красный");
    public final NumberSetting fillAlpha = new NumberSetting("Прозрачность", 0.25, 0.0, 1.0);
    public final NumberSetting speed = new NumberSetting("Скорость волны", 2.0, 0.5, 5.0);

    public TargetESPModule() {
        super("Target ESP", true);
        addSetting(colorMode);
        addSetting(fillAlpha);
        addSetting(speed);
    }
}
