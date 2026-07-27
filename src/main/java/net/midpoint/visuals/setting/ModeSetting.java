package net.midpoint.visuals.setting;

public class ModeSetting extends Setting<String> {
    private final String[] modes;
    private int index;

    public ModeSetting(String name, String defaultValue, String... modes) {
        super(name, defaultValue);
        this.modes = modes;
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equalsIgnoreCase(defaultValue)) this.index = i;
        }
    }

    public String[] getModes() { return modes; }
    
    public void cycle() {
        index = (index + 1) % modes.length;
        setValue(modes[index]);
    }
}
