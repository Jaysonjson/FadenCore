package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.server.command.ServerCommandSource;

public class FadenCommands implements CommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        CurrencyCommand.register(dispatcher);
        RaceCommand.register(dispatcher);
        ReloadCommand.register(dispatcher);
        CapeCommand.register(dispatcher);

        //Disabled for now, as I think having the ItemValues on the git is better, since the list will be larger than 50kb and sending this Packet everytime a player joins
        //might have a big impact on the servers performance.

        //ItemValueCommand.register(dispatcher, registryAccess);
    }
}
