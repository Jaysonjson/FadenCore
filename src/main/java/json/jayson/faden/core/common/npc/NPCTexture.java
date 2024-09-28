package json.jayson.faden.core.common.npc;

import net.minecraft.util.Identifier;

public class NPCTexture {
	private String id;
	private Identifier texture;
	boolean slim = false;
	
	public NPCTexture() {
		
	}
	
	public NPCTexture(String id, boolean slim, Identifier texture) {
		this.id = id;
		this.slim = slim;
		this.texture = texture;
	}

	public Identifier getLocation() {
		return texture;
	}

	public String getId() {
		return id;
	}

	public boolean isSlim() {
		return slim;
	}
}
