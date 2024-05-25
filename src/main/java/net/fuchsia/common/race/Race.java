package net.fuchsia.common.race;

import java.util.HashMap;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityAttachments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;

public enum Race implements IRace {
	
	
	HUMAN(new RaceCosmeticPalette(), new String[]{"default"}),
	HARENGON(RaceCosmetics.HARENGON, new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.80f, 0.78f, 0.80f), RaceModelType.SLIM),
	TABAXI,
	ELF(RaceCosmetics.ELF, new String[]{"pale"}, new Vector3f(0.95f, 0.95f, 0.95f), RaceModelType.SLIM);

	private HashMap<String, byte[]> skinMap;
	private RaceCosmeticPalette palette;
	private String[] subIds;
	private Vector3f size = new Vector3f(1, 1, 1);
	private RaceModelType modelType = RaceModelType.BOTH;
	private EntityDimensions dimensions;
	private ImmutableMap<Object, Object> poseDimensions;
	Race(RaceCosmeticPalette palette, String[] subIds) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		dimensions = EntityDimensions.changing(0.6F, 1.8F).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
		poseDimensions = null;
	}

	Race(RaceCosmeticPalette palette, String[] subIds, Vector3f size, RaceModelType slim) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		this.size = size;
		this.modelType = slim;
		dimensions = EntityDimensions.changing(0.6F * size.x, 1.9F * size.y).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
		poseDimensions = ImmutableMap.builder().put(EntityPose.STANDING, dimensions).put(EntityPose.SLEEPING, PlayerEntity.SLEEPING_DIMENSIONS).put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.CROUCHING, EntityDimensions.changing(0.6F, 1.5F).withEyeHeight(1.27F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS))).put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F).withEyeHeight(1.62F)).build();
	}

	Race() {
		skinMap = new HashMap<>();
		this.palette = new RaceCosmeticPalette();
		this.subIds = new String[]{};
	}
	
	@Override
	public HashMap<String, byte[]> getSkinMap() {
		return skinMap;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public RaceCosmeticPalette getCosmeticPalette() {
		return palette;
	}

	@Override
	public String[] subIds() {
		return subIds;
	}

	@Override
	public Vector3f size() {
		return size;
	}

	@Override
	public RaceModelType model() {
		return modelType;
	}

	@Override
	public EntityDimensions dimensions() {
		return dimensions;
	}

	@Override
	public ImmutableMap<Object, Object> poseDimensions() {
		return poseDimensions;
	}
}
