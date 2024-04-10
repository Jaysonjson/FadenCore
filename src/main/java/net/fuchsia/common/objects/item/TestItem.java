package net.fuchsia.common.objects.item;

import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        /*if(!world.isClient) {
            Integer amount = new Random().nextInt(5000);
            CoinMap.addCurrency(user, amount);
            user.sendMessage(Text.literal("Gave Amount: " + amount));
        }*/

        if(!world.isClient) {
            RaceUtil.setPlayerRace((ServerPlayerEntity) user, Race.ELF);
        }
        return super.use(world, user, hand);
    }
}
