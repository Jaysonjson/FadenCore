package json.jayson.faden.core.common.race;

@Deprecated
public enum RaceEnum /* implements IRace */ {
	
	/*
	HUMAN(FadenIdentifier.create("textures/item_tier/rare.png"), new RaceCosmeticPalette(), new String[]{"default"}),
	HARENGON(FadenIdentifier.create("textures/item_tier/rare.png"), RaceCosmetics.HARENGON, new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.80f, 0.78f, 0.80f), RaceModelType.SLIM),
	TABAXI,
	ELF(FadenIdentifier.create("textures/item_tier/rare.png"), RaceCosmetics.ELF, new String[]{"pale", "drow"}, new Vector3f(0.95f, 0.95f, 0.95f), RaceModelType.SLIM);

	private HashMap<String, byte[]> skinMap;
	private RaceCosmeticPalette palette;
	private String[] subIds;
	private Vector3f size = new Vector3f(1, 1, 1);
	private RaceModelType modelType = RaceModelType.BOTH;
	private EntityDimensions dimensions;
	private ImmutableMap<Object, Object> poseDimensions;
	private Identifier icon;

	RaceEnum(Identifier icon, RaceCosmeticPalette palette, String[] subIds) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		dimensions = EntityDimensions.changing(0.6F, 1.8F).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
		poseDimensions = null;
		this.icon = icon;
	}

	RaceEnum(Identifier icon, RaceCosmeticPalette palette, String[] subIds, Vector3f size, RaceModelType slim) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		this.size = size;
		this.modelType = slim;
		dimensions = EntityDimensions.changing(0.6F * size.x, 1.9F * size.y).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
		poseDimensions = ImmutableMap.builder().put(EntityPose.STANDING, dimensions).put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F, 0.2F).withEyeHeight(0.2F)).put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.CROUCHING, EntityDimensions.changing(0.6F, 1.5F).withEyeHeight(1.27F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS))).put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F).withEyeHeight(1.62F)).build();
		this.icon = icon;
	}
	@Deprecated
	RaceEnum() {
		skinMap = new HashMap<>();
		this.palette = new RaceCosmeticPalette();
		this.subIds = new String[]{};
		this.icon = FadenIdentifier.create("textures/item_tier/rare.png");
	}

	@Override
	public Identifier getIcon() {
		return null;
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
	}*/
}
