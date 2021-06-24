package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RemoveIPCommand extends SubCommands{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.ipremove")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=1){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.removeip.arguments"));
            return;
        }
        if(!plugin.players.contains(args[0].toLowerCase())){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.removeip.errorv2"));
            return;
        }
        List<String> data = config.getConfig().getStringList("config.accounts-ip." + args[0].toLowerCase());
        //validando si contiene la ip
        if(!data.contains(args[1])){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.removeip.error")
            .replaceAll("%player%", args[0]));
            return;
        }
        data.remove(args[1]);
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("config.accounts-ip." + args[0], data);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(plugin.getDataFolder(), "config.yml"));
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.removeip.success")
                    .replaceAll("%player%", args[0]).replaceAll("%ip%" , args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String name() {
        return plugin.commandManager.removeip;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity removeip <player> <ip>  &7remove one IP of a player.");
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
