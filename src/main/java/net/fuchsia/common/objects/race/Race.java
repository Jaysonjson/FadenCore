package net.fuchsia.common.objects.race;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityAttachments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Race implements IRace {

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
        this.entityDimensions = EntityDimensions.changing(0.6F * size.x, 1.9F * size.y).withEyeHeight(1.62F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS));
        this.poseDimensions = ImmutableMap.builder().put(EntityPose.STANDING, dimensions()).put(EntityPose.SLEEPING, EntityDimensions.fixed(0.2F, 0.2F).withEyeHeight(0.2F)).put(EntityPose.FALL_FLYING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SWIMMING, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.SPIN_ATTACK, EntityDimensions.changing(0.6F, 0.6F).withEyeHeight(0.4F)).put(EntityPose.CROUCHING, EntityDimensions.changing(0.6F, 1.5F).withEyeHeight(1.27F).withAttachments(EntityAttachments.builder().add(EntityAttachmentType.VEHICLE, PlayerEntity.VEHICLE_ATTACHMENT_POS))).put(EntityPose.DYING, EntityDimensions.fixed(0.2F, 0.2F).withEyeHeight(1.62F)).build();
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
        return identifier.getPath();
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
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
    public EntityDimensions dimensions() {
        return entityDimensions;
    }

    @Override
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
            System.out.println(entityAttribute.getTranslationKey() + ":" + serverPlayerEntity.getAttributes().getValue(Registries.ATTRIBUTE.getEntry(entityAttribute)));
            System.out.println("water_movement: " + waterMovementSpeed());
        }
    }
}
