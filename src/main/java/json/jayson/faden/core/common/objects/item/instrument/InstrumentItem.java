package json.jayson.faden.core.common.objects.item.instrument;

import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.init.FadenCoreMusicInstances;
import json.jayson.faden.core.common.objects.music_instance.InstrumentedMusic;
import json.jayson.faden.core.common.objects.music_instance.MusicInstance;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

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
            boolean createInstance = !handItem.contains(FadenCoreDataComponents.MUSIC_INSTANCE);
            if(createInstance) {
                for (Entity otherEntity : world.getOtherEntities(user, new Box(user.getPos().x - 15, user.getPos().y - 15, user.getPos().z - 15, user.getPos().x + 15, user.getPos().y + 15, user.getPos().z + 15))) {
                    if (otherEntity instanceof PlayerEntity player) {
                        ItemStack otherStack = player.getMainHandStack();
                        if (otherStack.getItem() instanceof InstrumentItem instrumentItem && otherStack.contains(FadenCoreDataComponents.MUSIC_INSTANCE)) {
                            MusicInstance instance = FadenCoreMusicInstances.getInstance(UUID.fromString(otherStack.get(FadenCoreDataComponents.MUSIC_INSTANCE)));
                            if (instance != null) {
                                handItem.set(FadenCoreDataComponents.MUSIC_INSTANCE, instance.getUuid().toString());
                                user.setStackInHand(hand, handItem);
                                createInstance = false;
                            }
                            break;
                        }
                    }
                }
            }

            if(createInstance) {
                UUID uuid = UUID.randomUUID();
                MusicInstance musicInstance = new MusicInstance();
                musicInstance.setUuid(uuid);
                musicInstance.setPosition(user.getPos().toVector3f());
                musicInstance.getInstruments().add(getInstrumentType());
                InstrumentedMusic music = FadenCoreRegistry.INSTRUMENTED_MUSIC.get(new Random().nextInt(FadenCoreRegistry.INSTRUMENTED_MUSIC.size()));
                for (InstrumentType type : music.getInstrumentTypes().keySet()) musicInstance.getSoundEvents().put(type, music.getInstrumentTypes().get(type).getId().toString());
                musicInstance.setMusicId(music.getId());
                FadenCoreMusicInstances.getInstances().put(uuid, musicInstance);
                handItem.set(FadenCoreDataComponents.MUSIC_INSTANCE, uuid.toString());
                user.setStackInHand(hand, handItem);
                for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                    FadenCoreNetwork.Server.sendMusicInstance(serverPlayerEntity, musicInstance);
                }
            }

            if(handItem.contains(FadenCoreDataComponents.MUSIC_INSTANCE)) {
                MusicInstance instance = FadenCoreMusicInstances.getInstance(UUID.fromString(handItem.get(FadenCoreDataComponents.MUSIC_INSTANCE)));
                if (instance != null) {
                    System.out.println("Playing: " + instance.getUuid());
                    if (!instance.getInstruments().contains(getInstrumentType())) {
                        instance.getInstruments().add(getInstrumentType());
                        //JUST SEND IT TO ALL, SO PLAYERS CAN JOIN
                        for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                            FadenCoreNetwork.Server.sendMusicInstance(serverPlayerEntity, instance);
                        }
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
                if(stack.contains(FadenCoreDataComponents.MUSIC_INSTANCE)) {
                    MusicInstance instance = FadenCoreMusicInstances.getInstance(UUID.fromString(stack.get(FadenCoreDataComponents.MUSIC_INSTANCE)));
                    if (instance != null) {
                        if(instance.getInstruments().contains(getInstrumentType())) {
                            instance.getInstruments().remove(getInstrumentType());
                        }
                    }
                    if(!world.isClient) {
                        for (ServerPlayerEntity serverPlayerEntity : world.getServer().getPlayerManager().getPlayerList()) {
                            FadenCoreNetwork.Server.sendMusicInstance(serverPlayerEntity, instance);
                        }
                    }
                    stack.remove(FadenCoreDataComponents.MUSIC_INSTANCE);
                }
            }
        }
    }


    public InstrumentType getInstrumentType() {
        return instrumentType;
    }
}
