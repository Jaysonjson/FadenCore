package net.fuchsia.common.race;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.server.PlayerData;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityAttachments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Race {

    protected Identifier identifier = Identifier.of("");
    protected String[] subIds = new String[0];
    protected Vector3f size = new Vector3f();
    protected EntityDimensions entityDimensions = EntityDimensions.changing(0.6F, 1.8F).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
    protected ImmutableMap<Object, Object> poseDimensions;
    protected HashMap<String, byte[]> skinMap = new HashMap<>();

    public Race(Identifier identifier, String[] subIds, Vector3f size, EntityDimensions entityDimensions, ImmutableMap<Object, Object> poseDimensions) {
        this.identifier = identifier;
        this.subIds = subIds;
        this.size = size;
        this.entityDimensions = entityDimensions;
        this.poseDimensions = poseDimensions;
    }


    public Race(Identifier identifier, String[] subIds, Vector3f size) {
        this.identifier = identifier;
        this.subIds = subIds;
        this.size = size;
        this.entityDimensions = EntityDimensions.changing(size.x == 1 ? 0.6F : 0.76F * size.x, size.x == 1 ? 1.9F : 1.95F * size.y).withEyeHeight(1.62F * size.y).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
        this.poseDimensions = ImmutableMap.builder().put(EntityPose.STANDING, dimensions()).put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F * size.x, 0.2F * size.y).withEyeHeight(0.2F * size.y)).put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.y)).put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.y)).put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F * size.x, 0.6F * size.y).withEyeHeight(0.4F * size.x)).put(EntityPose.CROUCHING, EntityDimensions.changing(size.x == 1 ? 0.6F : 0.76F * size.x, 1.5F * size.y).withEyeHeight(1.27F * size.y).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS))).put(EntityPose.DYING, EntityDimensions.fixed(0.2F * size.x, 0.2F * size.y).withEyeHeight(1.62F * size.y)).build();
    }

    public Identifier getIcon() {
        return null;
    }

    public HashMap<String, byte[]> getSkinMap() {
        return skinMap;
    }

    /*public String getId() {
        return identifier.getPath();
    }*/

    public Identifier getIdentifier() {
        return identifier;
    }

    public String[] subIds() {
        return subIds;
    }

    public Vector3f size() {
        return size;
    }

    public EntityDimensions dimensions() {
        return entityDimensions;
    }

    public ImmutableMap<Object, Object> poseDimensions() {
        return poseDimensions;
    }

    public float waterMovementSpeed() {
        return 0;
    }

    protected void addEntityAttributes(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers) {

    }

    public void applyEntityAttributes(ServerPlayerEntity serverPlayerEntity) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeMap = MultimapBuilder.hashKeys().arrayListValues().build();
        //System.out.println("Movement Speed1: " + serverPlayerEntity.getAttributes().getValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
        addEntityAttributes(attributeMap);
        serverPlayerEntity.getAttributes().addTemporaryModifiers(attributeMap);
        //Attribute Listing test, can later be used for race selection just in a GUI
        for (EntityAttribute entityAttribute : Registries.ATTRIBUTE) {
            if(!serverPlayerEntity.getAttributes().hasAttribute(Registries.ATTRIBUTE.getEntry(entityAttribute))) continue;
            //System.out.println(entityAttribute.getTranslationKey() + ":" + serverPlayerEntity.getAttributes().getValue(Registries.ATTRIBUTE.getEntry(entityAttribute)));
            //System.out.println("water_movement: " + waterMovementSpeed());
        }
    }

    //Uses 1 single Cosmetic for each type, override for special races like the tabaxi
    public PlayerData.RaceDataCosmetics randomizeCosmetics(String subId) {
        ArrayList<RaceCosmetic> headCosmetics = new ArrayList<>();
        ArrayList<RaceCosmetic> chestCosmetics = new ArrayList<>();
        ArrayList<RaceCosmetic> legCosmetics = new ArrayList<>();
        ArrayList<RaceCosmetic> bootsCosmetics = new ArrayList<>();
        for (RaceCosmetic cosmetic : getCosmeticPalette().getCosmetics(subId)) {
            switch (cosmetic.getSlot()) {
                case HEAD -> headCosmetics.add(cosmetic);
                case CHEST -> chestCosmetics.add(cosmetic);
                case LEG -> legCosmetics.add(cosmetic);
                case BOOTS -> bootsCosmetics.add(cosmetic);
            }
        }
        Random random = new Random();
        PlayerData.RaceDataCosmetics dataCosmetics = new PlayerData.RaceDataCosmetics();
        if(!headCosmetics.isEmpty()) dataCosmetics.getHead().add(headCosmetics.get(random.nextInt(headCosmetics.size())).getId());
        if(!chestCosmetics.isEmpty()) dataCosmetics.getChest().add(chestCosmetics.get(random.nextInt(chestCosmetics.size())).getId());
        if(!legCosmetics.isEmpty()) dataCosmetics.getLeg().add(legCosmetics.get(random.nextInt(legCosmetics.size())).getId());
        if(!bootsCosmetics.isEmpty()) dataCosmetics.getBoots().add(bootsCosmetics.get(random.nextInt(bootsCosmetics.size())).getId());
        return dataCosmetics;
    }

    public abstract RaceCosmeticPalette getCosmeticPalette();
    public abstract RaceModelType model();

    public void applyStats(ServerPlayerEntity player) {
        applyEntityAttributes(player);
        //A bit hacky, but re-makes dimensions
        player.setSneaking(true);
        player.setPose(EntityPose.CROAKING);
    }

}
