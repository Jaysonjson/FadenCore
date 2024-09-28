package json.jayson.faden.core.common.race;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmetic;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmeticPalette;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmeticSlot;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.server.PlayerData;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityAttachments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.*;

public abstract class FadenCoreRace {

    protected String[] subIds = new String[0];
    protected Vector3f size = new Vector3f();
    protected EntityDimensions entityDimensions = EntityDimensions.changing(0.6F, 1.8F).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
    protected ImmutableMap<Object, Object> poseDimensions;
    protected HashMap<Identifier, byte[]> skinMap = new HashMap<>();

    public FadenCoreRace(String[] subIds, Vector3f size, EntityDimensions entityDimensions, ImmutableMap<Object, Object> poseDimensions) {
        this.subIds = subIds;
        this.size = size;
        this.entityDimensions = entityDimensions;
        this.poseDimensions = poseDimensions;
    }


    public FadenCoreRace(String[] subIds, Vector3f size) {
        this.subIds = subIds;
        this.size = size;
        this.entityDimensions = EntityDimensions.changing(size.x == 1 ? 0.6F : 0.76F * size.x, size.x == 1 ? 1.9F : 1.95F * size.y).withEyeHeight(1.62F * size.y).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
        this.poseDimensions = ImmutableMap.builder().put(EntityPose.STANDING, getDimensions()).put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F * size.x, 0.2F * size.y).withEyeHeight(0.2F * size.y)).put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.y)).put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.y)).put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.x)).put(EntityPose.CROUCHING, EntityDimensions.changing(size.x == 1 ? 0.6F : 0.76F * size.x, 1.5F * size.y).withEyeHeight(1.27F * size.y).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS))).put(EntityPose.DYING, EntityDimensions.fixed(0.2F * size.x, 0.2F * size.y).withEyeHeight(1.62F * size.y)).build();
    }

    public Identifier getIcon() {
        return null;
    }

    public HashMap<Identifier, byte[]> getSkinMap() {
        return skinMap;
    }

    /*public String getId() {
        return identifier.getPath();
    }*/

    public Identifier getIdentifier() {
        return FadenCoreRegistry.RACE.getId(this);
    }

    public String[] subIds() {
        return subIds;
    }

    public Vector3f getSize() {
        return size;
    }

    public EntityDimensions getDimensions() {
        return entityDimensions;
    }

    public ImmutableMap<Object, Object> getPoseDimensions() {
        return poseDimensions;
    }

    public float waterMovementSpeed() {
        return 0;
    }

    protected void addEntityAttributes(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers) {

    }

    public void applyEntityAttributes(ServerPlayerEntity serverPlayerEntity) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeMap = MultimapBuilder.hashKeys().arrayListValues().build();
        addEntityAttributes(attributeMap);
        serverPlayerEntity.getAttributes().addTemporaryModifiers(attributeMap);
    }

    public Map<RaceCosmeticSlot, List<RaceCosmetic>> categorizeCosmetics(List<RaceCosmetic> cosmetics) {
        Map<RaceCosmeticSlot, List<RaceCosmetic>> categorizedCosmetics = new HashMap<>();
        for (RaceCosmeticSlot slot : RaceCosmeticSlot.values()) {
            categorizedCosmetics.put(slot, new ArrayList<>());
        }
        for (RaceCosmetic cosmetic : cosmetics) {
            categorizedCosmetics.get(cosmetic.getSlot()).add(cosmetic);
        }
        return categorizedCosmetics;
    }

    //Uses 1 single Cosmetic for each type, override for special races like the tabaxi
    public PlayerData.RaceDataCosmetics randomizeCosmetics(String subId) {
        Map<RaceCosmeticSlot, List<RaceCosmetic>> categorizedCosmetics = categorizeCosmetics(getCosmeticPalette().getCosmetics(subId));
        Random random = new Random();
        PlayerData.RaceDataCosmetics dataCosmetics = new PlayerData.RaceDataCosmetics();

        categorizedCosmetics.forEach((slot, cosmetics) -> {
            if (!cosmetics.isEmpty()) {
                RaceCosmetic selectedCosmetic = cosmetics.get(random.nextInt(cosmetics.size()));
                switch (slot) {
                    case HEAD -> dataCosmetics.getHead().add(selectedCosmetic.getId());
                    case CHEST -> dataCosmetics.getChest().add(selectedCosmetic.getId());
                    case LEG -> dataCosmetics.getLeg().add(selectedCosmetic.getId());
                    case BOOTS -> dataCosmetics.getBoots().add(selectedCosmetic.getId());
                }
            }
        });

        return dataCosmetics;
    }

    public abstract RaceCosmeticPalette getCosmeticPalette();
    public abstract RaceModelType getModelType();

    public void applyStats(ServerPlayerEntity player) {
        applyEntityAttributes(player);
        //A bit hacky, but re-makes dimensions
        player.setSneaking(true);
        player.setPose(EntityPose.CROAKING);
    }

    public boolean hasSkins() {
        return true;
    }

}
