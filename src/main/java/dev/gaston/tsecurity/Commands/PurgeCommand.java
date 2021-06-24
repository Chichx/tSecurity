package dev.gaston.tsecurity.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PurgeCommand extends SubCommands{

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.purge")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(!plugin.logger){
            sender.sendMessage(plugin.getMessageByConfig("message.history.notenable"));
            return;
        }

        if(args.length <=0){
            sender.sendMessage(plugin.getMessageByConfig("message.purge.arguments"));
            return;
        }
        if(!plugin.data.containsKey(args[0])){
            sender.sendMessage(plugin.getMessageByConfig("message.purge.error"));
            return;
        }
        plugin.data.get(args[0].toLowerCase()).clear();
        File file = new File(plugin.getDataFolder(), "logger.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("logger.try." + args[0].toLowerCase(), null);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(plugin.getDataFolder(), "logger.yml"));
            sender.sendMessage(plugin.getMessageByConfig("message.purge.success")
            .replaceAll("%player%", args[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String name() {
        return plugin.commandManager.purge;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity purge <player>   &7Purge all history of failed IP's");
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
