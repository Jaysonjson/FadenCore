package net.fuchsia.client.render.post;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Iterator;

public class FadenPostProcessor extends PostEffectProcessor {

    public FadenPostProcessor(TextureManager textureManager, ResourceFactory resourceFactory, Framebuffer framebuffer, Identifier id) throws IOException, JsonSyntaxException {
        super(textureManager, resourceFactory, framebuffer, id);
    }

}
