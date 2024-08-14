package json.jayson.faden.core.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

import java.util.List;

public class ScreenUtil {



    /*
    Used from DrawingUtil from ModMenu by Prospector
    https://github.com/TerraformersMC/ModMenu?tab=MIT-1-ov-file#readme

    MIT License

    Copyright (c) 2018-2020 Prospector

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
     */

    public static void drawWrappedString(DrawContext DrawContext, String string, int x, int y, int wrapWidth, int lines, int color) {
        while (string != null && string.endsWith("\n")) {
            string = string.substring(0, string.length() - 1);
        }
        List<StringVisitable> strings = MinecraftClient.getInstance().textRenderer.getTextHandler().wrapLines(Text.literal(string), wrapWidth, Style.EMPTY);
        for (int i = 0; i < strings.size(); i++) {
            if (i >= lines) {
                break;
            }
            StringVisitable renderable = strings.get(i);
            if (i == lines - 1 && strings.size() > lines) {
                renderable = StringVisitable.concat(strings.get(i), StringVisitable.plain("..."));
            }
            OrderedText line = Language.getInstance().reorder(renderable);
            int x1 = x;
            if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
                int width = MinecraftClient.getInstance().textRenderer.getWidth(line);
                x1 += (float) (wrapWidth - width);
            }
            DrawContext.drawText(MinecraftClient.getInstance().textRenderer, line, x1, y + i * MinecraftClient.getInstance().textRenderer.fontHeight, color, false);
        }
    }
}
