package net.fuchsia.common.objects.item.instrument;

import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.init.FadenMusicInstances;
import net.fuchsia.common.init.FadenSoundEvents;
import net.fuchsia.common.objects.music_instance.BurningMemory;
import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.common.objects.music_instance.MusicInstance;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.lwjgl.openal.AL10;

import java.util.Random;
import java.util.UUID;

public class InstrumentItem extends Item {
    private InstrumentType instrumentType;
    public InstrumentItem(Settings settings, InstrumentType type) {
        super(settings);
        this.instrumentType = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            //HOLY SHIT WHAT A NESTED CLUSTERFUCK
            ItemStack handItem = user.getStackInHand(hand);
            for (Entity otherEntity : world.getOtherEntities(user, new Box(user.getPos().x - 15, user.getPos().y - 15, user.getPos().z - 15, user.getPos().x + 15, user.getPos().y + 15, user.getPos().z + 15))) {
                if (otherEntity instanceof PlayerEntity player) {
                    ItemStack otherStack = player.getMainHandStack();
                    if (otherStack.getItem() instanceof InstrumentItem instrumentItem && otherStack.contains(FadenDataComponents.MUSIC_INSTANCE)) {
                        MusicInstance instance = FadenMusicInstances.getInstance(UUID.fromString(otherStack.get(FadenDataComponents.MUSIC_INSTANCE)));
                        if(instance != null) {
                            handItem.set(FadenDataComponents.MUSIC_INSTANCE, instance.getUuid().toString());
                            user.setStackInHand(hand, handItem);
                        }
                        break;
                    } else {
                        UUID uuid = UUID.randomUUID();
                        MusicInstance musicInstance = new MusicInstance();
                        musicInstance.setUuid(uuid);
                        musicInstance.setPosition(user.getPos().toVector3f());
                        musicInstance.getInstruments().add(getInstrumentType());
                        for (InstrumentType type : new BurningMemory().getInstrumentTypes().keySet()) {
                            musicInstance.getSoundEvents().put(type, new BurningMemory().getInstrumentTypes().get(type).getId().toString());
                        }
                        FadenMusicInstances.getInstances().put(uuid, musicInstance);
                        handItem.set(FadenDataComponents.MUSIC_INSTANCE, uuid.toString());
                        user.setStackInHand(hand, handItem);
                        for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                            FadenNetwork.Server.sendMusicInstance(serverPlayerEntity, musicInstance);
                        }
                        break;
                    }
                }
            }

            MusicInstance instance = FadenMusicInstances.getInstance(UUID.fromString(handItem.get(FadenDataComponents.MUSIC_INSTANCE)));
            if (instance != null) {
                System.out.println("Playing: " + instance.getUuid());
                if (!instance.getInstruments().contains(getInstrumentType())) {
                    instance.getInstruments().add(getInstrumentType());
                    //JUST SEND IT TO ALL, SO PLAYERS CAN JOIN
                    for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                        FadenNetwork.Server.sendMusicInstance(serverPlayerEntity, instance);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity player) {
            if(!stack.isOf(player.getMainHandStack().getItem())) {
                if(stack.contains(FadenDataComponents.MUSIC_INSTANCE)) {
                    MusicInstance instance = FadenMusicInstances.getInstance(UUID.fromString(stack.get(FadenDataComponents.MUSIC_INSTANCE)));
                    if (instance != null) {
                        if(instance.getInstruments().contains(getInstrumentType())) {
                            instance.getInstruments().remove(getInstrumentType());
                        }
                    }
                    if(!world.isClient) {
                        System.out.println("NOT CLIENT");
                        for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                            FadenNetwork.Server.sendMusicInstance(serverPlayerEntity, instance);
                        }
                    }
                    stack.remove(FadenDataComponents.MUSIC_INSTANCE);
                }
            }
        }
    }


    public InstrumentType getInstrumentType() {
        return instrumentType;
    }
}
