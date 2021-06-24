package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RemoveCommand extends SubCommands {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.remove")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=0){
            sender.sendMessage(plugin.getMessageByConfig("message.remove.arguments"));
            return;
        }
        if(!plugin.players.contains(args[0])){
            sender.sendMessage(plugin.getMessageByConfig("message.remove.error"));
            return;
        }
        plugin.players.remove(args[0]);
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("config.players",plugin.players);
            cgf.set("config.accounts-ip." + args[0].toLowerCase(), null);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(plugin.getDataFolder(), "config.yml"));
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.remove.success")
            .replaceAll("%player%" , args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String name() {
        return plugin.commandManager.remove;
    }

    @Override
    public String info() {
        return "&a/tsecurity remove <player>   &7Remove a player of the list.";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
