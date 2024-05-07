package net.fuchsia.client.screen;

import com.terraformersmc.modmenu.config.ModMenuConfig;
import com.terraformersmc.modmenu.config.ModMenuConfigManager;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.fuchsia.client.PlayerModelCache;
import net.fuchsia.client.screen.widgets.CapeListWidget;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CapeSelectScreen extends Screen {

    float rot = 0;
    private CapeListWidget capes;
    public static FadenCape PRE_SELECTED_CAPE = null;

    public CapeSelectScreen() {
        super(Text.literal(""));
    }

    @Override
    protected void init() {
        capes = new CapeListWidget(this.client, 250, this.height - 100, 50, 13);
        capes.setX(15);
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("button.faden.select_cape"), button -> {
                    FadenNetwork.Client.requestCapeUpdate(client.player.getUuid(), PRE_SELECTED_CAPE.getId());
                    close();
                })
                .position(176, client.getWindow().getScaledHeight() - 45)
                .size(90, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("button.faden.remove_cape"), button -> {
                    FadenNetwork.Client.requestCapeUpdate(client.player.getUuid(), "");
                    close();
                })
                .position(14, client.getWindow().getScaledHeight() - 45)
                .size(90, 20)
                .build());

        this.addSelectableChild(this.capes);
    }

    @Override
    public void close() {
        PRE_SELECTED_CAPE = null;
        super.close();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        capes.render(context, mouseX, mouseY, delta);
        if(capes.getSelectedOrNull() != null) {
            FadenCape cape = capes.getSelectedOrNull().cape;
            PRE_SELECTED_CAPE = cape;
            rot += 3.5f * delta;
            Quaternionf d = new Quaternionf();
            d.rotationXYZ(180f * 0.017453292F, rot * 0.017453292F, 0);
            InventoryScreen.drawEntity(context, context.getScaledWindowWidth() / 2, context.getScaledWindowHeight() / 2, 75, new Vector3f(1.8f, 0.75f,0), d, null, MinecraftClient.getInstance().player);
            if(rot >= 360) {
                rot = 0;
            }

            if(PlayerModelCache.wideModel != null) {
                context.getMatrices().push();
                context.getMatrices().scale(55, 55, 55);
                context.getMatrices().multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                context.getMatrices().translate(-5, 1.25f, 12.5);
                PlayerModelCache.wideModel.renderCape(context.getMatrices(), context.getVertexConsumers().getBuffer(RenderLayer.getEntitySolid(cape.getTexture())), 15728880, OverlayTexture.DEFAULT_UV);
                context.getMatrices().pop();
            }

            if(PlayerModelCache.elytraEntityModel != null) {
                context.getMatrices().push();
                context.getMatrices().scale(55, 55, 55);
                context.getMatrices().multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                context.getMatrices().translate(-5, 2, 12.5);
                PlayerModelCache.elytraEntityModel.render(context.getMatrices(), ItemRenderer.getArmorGlintConsumer(context.getVertexConsumers(), RenderLayer.getArmorCutoutNoCull(cape.getTexture()), false, false), 15728880, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
                context.getMatrices().pop();
            }
        }
    }
}
