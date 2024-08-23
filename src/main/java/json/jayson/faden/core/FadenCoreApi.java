package json.jayson.faden.core;

public interface FadenCoreApi {

    default void onInitalize() {}

    /*
    * Disable if you dont want to save player data to a file, useful for single addons that dont require it.
    * If 1 addon uses this, all addons will use this.
    * */
    default boolean enablePlayerData() {
        return true;
    }


    /*
     * Disable if you dont want to save quest data to a file, useful for single addons that dont require it.
     * If 1 addon uses this, all addons will use this.
     * */
    default boolean enableQuests() {
        return true;
    }

}
