package json.jayson.faden.core.client.handler;

import net.minecraft.item.Item;

import java.util.ArrayList;

public class FadenItemModelHandler {
    private static final ArrayList<Data> MODELS = new ArrayList<>();

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

    public static class Data {
        public Item item;
        public String model;
        public String gui;
    }
}
