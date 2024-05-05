package net.fuchsia.compat.rei;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.client.categories.beacon.DefaultBeaconPaymentCategory;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.beacon.DefaultBeaconPaymentDisplay;
import net.fuchsia.common.init.FadenItems;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Objects;

public class REIBuyCategory implements DisplayCategory<REIBuyDisplay> {

    @Override
    public CategoryIdentifier<? extends REIBuyDisplay> getCategoryIdentifier() {
        return FadenREIServerPlugin.BUY_DISPLAY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.faden.rei.buy_value");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(FadenItems.COPPER_COIN);
    }

    @Override
    public DisplayRenderer getDisplayRenderer(REIBuyDisplay display) {
        Text name = getTitle();
        return new DisplayRenderer() {
            @Override
            public int getHeight() {
                return 10 + MinecraftClient.getInstance().textRenderer.fontHeight;
            }

            @Override
            public void render(DrawContext graphics, Rectangle rectangle, int mouseX, int mouseY, float delta) {
                graphics.drawText(MinecraftClient.getInstance().textRenderer, name, rectangle.x + 5, rectangle.y + 6, -1, false);
                //graphics.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal(Text.translatable("text.faden.value").getString() + ": " + display.amount).toString(), graphics.getScaledWindowWidth() / 2, graphics.getScaledWindowHeight() / 2, 0xFFFFFFFF);
            }
        };
    }

    @Override
    public List<Widget> setupDisplay(REIBuyDisplay display, Rectangle bounds) {
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createSlot(new Point(bounds.getCenterX() - 75, bounds.y + 3)).entry(EntryStacks.of(display.out)));
        widgets.add(Widgets.createSlot(new Point(bounds.getCenterX() - 55 + (((display.amount) / 1000)), bounds.y + 3)).entry(EntryStacks.of(new ItemStack(FadenItems.COPPER_COIN, display.amount))));
        Rectangle rectangle = new Rectangle(bounds.getCenterX() - (bounds.width / 2) - 1, bounds.y + 23, bounds.width + 2, bounds.height - 28);
        widgets.add(Widgets.createSlotBase(rectangle));
        widgets.add(new ScrollableSlotsWidget(rectangle, CollectionUtils.map(display.getEntries(), t -> Widgets.createSlot(new Point(0, 0)).disableBackground().entry(t))));
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 140;
    }

    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }

    private static class ScrollableSlotsWidget extends WidgetWithBounds {
        private Rectangle bounds;
        private List<Slot> widgets;
        private final ScrollingContainer scrolling = new ScrollingContainer() {
            @Override
            public Rectangle getBounds() {
                Rectangle bounds = ScrollableSlotsWidget.this.getBounds();
                return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
            }

            @Override
            public int getMaxScrollHeight() {
                return MathHelper.ceil(widgets.size() / 8f) * 18;
            }
        };

        public ScrollableSlotsWidget(Rectangle bounds, List<Slot> widgets) {
            this.bounds = Objects.requireNonNull(bounds);
            this.widgets = Lists.newArrayList(widgets);
        }

        @Override
        public boolean mouseScrolled(double double_1, double double_2, double amountX, double amountY) {
            if (containsMouse(double_1, double_2) && amountY != 0) {
                scrolling.offset(ClothConfigInitializer.getScrollStep() * -amountY, true);
                return true;
            }
            return false;
        }

        @Override
        public Rectangle getBounds() {
            return bounds;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (scrolling.updateDraggingState(mouseX, mouseY, button))
                return true;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        @Override
        public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
            scrolling.updatePosition(delta);
            Rectangle innerBounds = scrolling.getScissorBounds();
            try (CloseableScissors scissors = scissor(graphics, innerBounds)) {
                for (int y = 0; y < MathHelper.ceil(widgets.size() / 8f); y++) {
                    for (int x = 0; x < 8; x++) {
                        int index = y * 8 + x;
                        if (widgets.size() <= index)
                            break;
                        Slot widget = widgets.get(index);
                        widget.getBounds().setLocation(bounds.x + 1 + x * 18, bounds.y + 1 + y * 18 - scrolling.scrollAmountInt());
                        widget.render(graphics, mouseX, mouseY, delta);
                    }
                }
            }
            try (CloseableScissors scissors = scissor(graphics, scrolling.getBounds())) {
                scrolling.renderScrollBar(graphics, 0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
            }
        }

        @Override
        public List<? extends Element> children() {
            return widgets;
        }
    }
}
