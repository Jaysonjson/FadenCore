package json.jayson.client.registry;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class FadenItemModelRegistry {
    private Map<Item, ModelData> models = new HashMap<>();

    public void addModel(Item item) {
        models.put(item, new ModelData());
    }

    public void addModel(Item item, ModelData modelData) {
        models.put(item, modelData);
    }

    public void addModel(Item item, String path) {
        models.put(item, new ModelData(path));
    }

    public ModelData getModel(Item item) {
        return models.get(item);
    }

    public boolean hasModel(Item item) {
        return models.containsKey(item);
    }

    public Map<Item, ModelData> getModels() {
        return models;
    }

    public static class ModelData {
        private String variant = "inventory";
        /* If you want to set a custom model path, normalily its assets/<item_mod_id>/models/item/model/<item_name>.json */
        private String path = "";

        /* The Mode to override its not at */
        private ModelTransformationMode mode = ModelTransformationMode.GUI;
        public ModelData(String variant, String path, ModelTransformationMode mode) {
            this.variant = variant;
            this.path = path;
            this.mode = mode;
        }

        public ModelData(String path) {
            this.path = path;
        }

        public ModelData() {}

        public String getPath() {
            return path;
        }

        public String getVariant() {
            return variant;
        }

        public ModelTransformationMode getMode() {
            return mode;
        }
    }
}
