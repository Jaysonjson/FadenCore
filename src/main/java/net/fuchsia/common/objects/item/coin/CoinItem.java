package net.fuchsia.common.objects.item.coin;

import java.util.Optional;

import net.minecraft.item.tooltip.TooltipData;
import org.joml.Matrix4f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.item.ItemToolTipRenderer;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CoinItem extends Item implements ItemToolTipRenderer {
    private final int value;
    public CoinItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new FadenTooltipData(stack));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            //QUESTS SHOULD BE IN A STATIC CONTEXT LATER, JUST FOR TESTING
            FadenQuests.TEST.checkAndRewardStep(user, FadenIdentifier.create("use_coin"));
        }
        return super.use(world, user, hand);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(String.valueOf(value * component.data.itemStack.getCount()), x + 10, y, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    @Override
    public void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        context.drawItem(FadenItems.COPPER_COIN.getDefaultStack(), x * 2, y * 2);
        context.getMatrices().pop();
    }

    @Override
    public int toolTipHeight(FadenTooltipComponent component,int height) {
        return 10;
    }

    @Override
    public int toolTipWidth(TextRenderer renderer, int width) {
        return renderer.getWidth(String.valueOf(value));
    }
}
