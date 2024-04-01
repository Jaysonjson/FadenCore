package json.jayson.client.handler;

import net.minecraft.item.Item;

import java.util.ArrayList;

public class FadenItemModelHandler {
    private static ArrayList<Data> MODELS = new ArrayList<>();

    /*
     * model = the 3D path to the model json
     * gui = the texture path to be shown in GUI
     * */
    public static void add(Item item, String model, String gui) {
        Data data = new Data();
        data.gui = gui;
        data.model = model;
        data.item = item;
        MODELS.add(data);
    }

    public static void add(Item item, String gui) {
        Data data = new Data();
        data.gui = gui;
        data.model = "";
        data.item = item;
        MODELS.add(data);
    }


    public static ArrayList<Data> getModels() {
        return MODELS;
    }

    /*
     * USED FOR EXTERNAL ADDONS TRYING TO USE THIS SYSTEM
     * IT DOESNT MAKE A GENERATOR ENTRY
     * */
    public static void addExternal(Item item, String model) {
        Data data = new Data();
        data.gui = "";
        data.model = model;
        data.item = item;
        MODELS.add(data);
    }

    public static class Data {
        public Item item;
        public String model;
        public String gui;
    }
}
