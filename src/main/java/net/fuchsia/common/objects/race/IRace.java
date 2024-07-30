package net.fuchsia.common.objects.race;

import java.util.HashMap;

import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
import net.minecraft.entity.EntityDimensions;

public interface IRace {
	Identifier getIcon();
	HashMap<String, byte[]> getSkinMap();
	String getId();
	RaceCosmeticPalette getCosmeticPalette();
	String[] subIds();
	Vector3f size();
	RaceModelType model();
	EntityDimensions dimensions();
	ImmutableMap<Object, Object> poseDimensions();
	Identifier getIdentifier();
}
