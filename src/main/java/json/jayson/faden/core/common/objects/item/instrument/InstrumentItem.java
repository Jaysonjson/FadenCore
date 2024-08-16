package json.jayson.faden.core.common.objects.item.instrument;

import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.init.FadenCoreMusicInstances;
import json.jayson.faden.core.common.objects.music_instance.MusicInstance;
import json.jayson.faden.core.network.FadenCoreNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

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
                handItem.set(FadenCoreDataComponents.MUSIC_INSTANCE, MusicInstance.createInstance(user, getInstrumentType()).getUuid().toString());
                user.setStackInHand(hand, handItem);
            }

            if(handItem.contains(FadenCoreDataComponents.MUSIC_INSTANCE)) {
                MusicInstance instance = FadenCoreMusicInstances.getInstance(UUID.fromString(handItem.get(FadenCoreDataComponents.MUSIC_INSTANCE)));
                if (instance != null) instance.addNewInstrument(getInstrumentType(), user);
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
                        instance.getInstruments().remove(getInstrumentType().getTypeId());
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
