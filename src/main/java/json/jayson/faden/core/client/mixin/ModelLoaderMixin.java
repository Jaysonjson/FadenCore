package json.jayson.faden.core.client.mixin;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.client.FadenCoreClient;
import json.jayson.faden.core.client.registry.FadenItemModelRegistry;
import json.jayson.faden.core.common.race.Race;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmetic;
import json.jayson.faden.core.config.FadenCoreOptions;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void loadItemModel(ModelIdentifier modelId);

    @Shadow abstract UnbakedModel getOrLoadModel(Identifier id);

    @Shadow protected abstract void add(ModelIdentifier id, UnbakedModel model);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER, by = 1))
    public void addModels(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        for (Item item : FadenCoreClient.getItemModels().getModels().keySet()) {
            Identifier itemId = Registries.ITEM.getId(item);
            FadenItemModelRegistry.ModelData data = FadenCoreClient.getItemModels().getModel(item);
            this.loadItemModel(new ModelIdentifier(Identifier.of(itemId.getNamespace(), data.getPath().isEmpty() ? "model/" + itemId.getPath() : data.getPath()), data.getVariant()));
        }

        if(FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_COSMETICS) {
            for (Race value : FadenCoreRegistry.RACE) {
                for (ArrayList<RaceCosmetic> raceCosmetics : value.getCosmeticPalette().getCosmetics().values()) {
                    for (RaceCosmetic raceCosmetic : raceCosmetics) {
                        loadModel(raceCosmetic.getModel());
                    }
                }
            }
        }
    }

    public void loadModel(ModelIdentifier id) {
        Identifier identifier = id.id().withPrefixedPath("cosmetic/");
        UnbakedModel unbakedModel = getOrLoadModel(identifier);
        add(id, unbakedModel);
    }
}
