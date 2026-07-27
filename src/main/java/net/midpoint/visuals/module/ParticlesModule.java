package net.midpoint.visuals.module;

import net.midpoint.visuals.setting.ModeSetting;
import net.midpoint.visuals.setting.NumberSetting;

public class ParticlesModule extends Module {
    public final ModeSetting trailType = new ModeSetting("Тип Следа", "Линия", "Matrix Glitch", "Void Vapor");
    public final ModeSetting critType = new ModeSetting("Удары", "Shire Stars", "Blood Orchid");
    public final NumberSetting density = new NumberSetting("Количество", 15.0, 5.0, 50.0);

    public ParticlesModule() {
        super("Particles and Trails", true);
        addSetting(trailType);
        addSetting(critType);
        addSetting(density);
    }
}
