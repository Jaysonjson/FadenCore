package json.jayson.faden.core.config;

public class FadenCoreOptions {

    private static FadenCoreConfig CONFIG = new FadenCoreConfig();

    public FadenCoreOptions() {

    }

    public static FadenCoreConfig getConfig() {
        return CONFIG;
    }

    public static void setConfig(FadenCoreConfig CONFIG) {
        FadenCoreOptions.CONFIG = CONFIG;
    }
}
