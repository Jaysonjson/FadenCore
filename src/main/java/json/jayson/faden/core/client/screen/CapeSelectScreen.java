package json.jayson.faden.core.client.screen;

import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.util.PlayerDataUtil;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import json.jayson.faden.core.client.PlayerModelCache;
import json.jayson.faden.core.client.screen.widgets.CapeListWidget;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.network.FadenCoreNetwork;
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

public class CapeSelectScreen extends Screen {

    float rot = 0;
    private CapeListWidget capes;
    public static FadenCoreCape PRE_SELECTED_CAPE = null;
    public ButtonWidget selectCapeWidget = null;
    public CheckboxWidget lockedWidget = null;
    public TextFieldWidget searchBox;
    public PlayerData playerData;
    public CapeSelectScreen() {
        super(Text.literal(""));
    }

    @Override
    protected void init() {
        capes = new CapeListWidget(this.client, 250, this.height - 100, 50, 40, this);
        capes.setX(15);
        this.addDrawableChild(selectCapeWidget = ButtonWidget.builder(Text.translatable("button.faden.select_cape"), button -> {
                    if(PRE_SELECTED_CAPE != null) {
                        FadenCoreNetwork.Client.requestCapeUpdate(client.player.getUuid(), PRE_SELECTED_CAPE.getIdentifier().toString());
                        close();
                    }
                })
                .position(176, client.getWindow().getScaledHeight() - 45)
                .size(90, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("button.faden.remove_cape"), button -> {
                    FadenCoreNetwork.Client.requestCapeUpdate(client.player.getUuid(), "");
                    close();
                })
                .position(14, client.getWindow().getScaledHeight() - 45)
                .size(90, 20)
                .build());

        this.addDrawableChild(lockedWidget = CheckboxWidget.builder(Text.translatable("screen.faden.show_locked"), textRenderer)
                        .pos(14, 27)
                        .checked(false)
                .build());

        this.addDrawableChild(searchBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, lockedWidget.getX() + textRenderer.getWidth(Text.translatable("screen.faden.show_locked")) + 25, lockedWidget.getY(), 95, 18, Text.of("")));

        this.addSelectableChild(this.capes);
        playerData = PlayerDataUtil.getClientOrServer(client.player.getUuid());
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(mouseX >= lockedWidget.getX() && mouseX <= lockedWidget.getX() + lockedWidget.getWidth() && mouseY >= lockedWidget.getY() && mouseY <= lockedWidget.getY() + lockedWidget.getHeight()) {
            capes.reload(!lockedWidget.isChecked(), searchBox.getText());
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        capes.reload(!lockedWidget.isChecked(), searchBox.getText());
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(capes.getSelectedOrNull() != null && playerData != null) {
            selectCapeWidget.visible = playerData.getCapes().contains(capes.getSelectedOrNull().cape.getIdentifier().toString());
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        capes.render(context, mouseX, mouseY, delta);
        if(capes.getSelectedOrNull() != null) {
            FadenCoreCape cape = capes.getSelectedOrNull().cape;
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
                PlayerModelCache.elytraEntityModel.render(context.getMatrices(), ItemRenderer.getArmorGlintConsumer(context.getVertexConsumers(), RenderLayer.getArmorCutoutNoCull(cape.getTexture()), false), 15728880, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
                context.getMatrices().pop();
            }
        }
    }
}
