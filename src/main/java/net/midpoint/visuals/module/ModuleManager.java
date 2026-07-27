package net.midpoint.visuals.module;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {
    private static final Map<Class<? extends Module>, Module> modules = new HashMap<>();

    public static void init() {
        register(new BlockOverlayModule());
        register(new TargetESPModule());
        register(new HitboxModule());
        register(new ParticlesModule());
    }

    private static void register(Module module) {
        modules.put(module.getClass(), module);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T get(Class<T> clazz) {
        return (T) modules.get(clazz);
    }
}
