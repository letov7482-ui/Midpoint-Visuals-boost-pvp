package net.midpoint.visuals.module;

import net.midpoint.visuals.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private boolean toggle;
    private final List<Setting<?>> settings = new ArrayList<>();

    public Module(String name, boolean defaultToggle) {
        this.name = name;
        this.toggle = defaultToggle;
    }

    public String getName() { return name; }
    public boolean isEnabled() { return toggle; }
    public void setEnabled(boolean toggle) { this.toggle = toggle; }
    public void toggle() { this.toggle = !this.toggle; }

    public void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }
}
