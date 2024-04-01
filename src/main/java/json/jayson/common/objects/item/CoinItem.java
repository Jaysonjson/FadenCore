package json.jayson.common.objects.item;

import json.jayson.common.objects.CoinMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class CoinItem extends Item {
    public CoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        /*if(!world.isClient) {
            Integer amount = new Random().nextInt(5000);
            CoinMap.addCurrency(user, amount);
            user.sendMessage(Text.literal("Gave Amount: " + amount));
        }*/
        user.sendMessage(Text.literal("Amount in Inventory: " + CoinMap.countCurrency(user.getInventory())));
        return super.use(world, user, hand);
    }
}
