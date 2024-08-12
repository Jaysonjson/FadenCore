package net.fuchsia.config;

public class FadenOptions {

    private static FadenConfig CONFIG = new FadenConfig();

    public FadenOptions() {

    }

    public static FadenConfig getConfig() {
        return CONFIG;
    }

    public static void setConfig(FadenConfig CONFIG) {
        FadenOptions.CONFIG = CONFIG;
    }
}
