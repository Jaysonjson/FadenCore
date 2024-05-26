package net.fuchsia.common.npc;

import net.minecraft.util.Identifier;

public class NPCTexture {
	private String id;
	private Identifier texture;
	boolean slim = false;
	
	public NPCTexture() {
		
	}
	
	public NPCTexture(String id, boolean slim) {
		this.id = id;
		this.slim = slim;
	}
}
