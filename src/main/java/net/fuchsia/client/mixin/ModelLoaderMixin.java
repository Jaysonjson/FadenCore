package net.fuchsia.client.mixin;

import net.fuchsia.client.FadenClient;
import net.fuchsia.client.registry.FadenItemModelRegistry;
import net.fuchsia.common.init.FadenRaces;
import net.fuchsia.race.Race;
import net.fuchsia.race.RaceEnum;
import net.fuchsia.race.cosmetic.RaceCosmetic;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.data.client.Model;
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

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER, by = 1))
    public void addModels(BlockColors blockColors, Profiler profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        for (Item item : FadenClient.getItemModels().getModels().keySet()) {
            Identifier itemId = Registries.ITEM.getId(item);
            FadenItemModelRegistry.ModelData data = FadenClient.getItemModels().getModel(item);
            this.loadItemModel(new ModelIdentifier(Identifier.of(itemId.getNamespace(), data.getPath().isEmpty() ? "model/" + itemId.getPath() : data.getPath()), data.getVariant()));
        }

        for (Race value : FadenRaces.getRegistry().values()) {
            for (ArrayList<RaceCosmetic> raceCosmetics : value.getCosmeticPalette().getCosmetics().values()) {
                for (RaceCosmetic raceCosmetic : raceCosmetics) {
                    this.loadItemModel(raceCosmetic.getModel());
                }
            }
        }
    }

}
