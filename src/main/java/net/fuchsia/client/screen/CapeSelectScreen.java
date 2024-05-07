package net.fuchsia.client.screen;

import com.terraformersmc.modmenu.config.ModMenuConfig;
import com.terraformersmc.modmenu.gui.ModsScreen;
import com.terraformersmc.modmenu.gui.widget.ModListWidget;
import net.fuchsia.client.mixin.PlayerEntityRendererMixin;
import net.fuchsia.client.screen.widgets.CapeListWidget;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.objects.item.coin.IValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Random;
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
        capes = new CapeListWidget(this.client, 250, this.height - 100, 50, 15);
        capes.setX(15);
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
        }
    }
}
