package net.fuchsia.common.objects.item.gear.bracelet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.Faden;
import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.ItemToolTipRenderer;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.util.Optional;

public class WarriorBracelet extends BraceletItem implements ItemToolTipRenderer {
    public WarriorBracelet(Settings settings) {
        super(settings);
    }



    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new FadenTooltipData(stack));
    }


    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        itemStack.set(DataComponentTypes.MAX_DAMAGE, Faden.RANDOM.nextInt(50 + (int)(50.0f * itemTier.getDurabilityMultiplier()), (int)(500.0f * itemTier.getDurabilityMultiplier())));
        itemStack.set(FadenDataComponents.ITEM_TIER, itemTier.name());
        itemStack.set(FadenDataComponents.DAMAGE_INCREASE_VALUE, 1.2f);
        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(Text.translatable("tooltip.faden.damage_increase_value").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))), x + 10, (y + component.heightAfterCoinAndTier()), 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    @Override
    public int toolTipHeight(FadenTooltipComponent component, int height) {
        return component.data.itemStack.contains(FadenDataComponents.ITEM_TIER) ? 20 : 14;
    }

    @Override
    public int toolTipWidth(TextRenderer renderer, int width) {
        return renderer.getWidth(Text.translatable("tooltip.faden.damage_increase_value"));
    }

    @Override
    public void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        context.drawItem(Items.IRON_SWORD.asItem().getDefaultStack(), x * 2, (y + component.heightAfterCoinAndTier()) * 2);
        context.getMatrices().pop();
    }
}
