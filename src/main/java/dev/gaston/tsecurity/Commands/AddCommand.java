package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCommand extends SubCommands{

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.add")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=1){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.added.arguments"));
            return;
        }
        if(args[1] == null || args[1].equalsIgnoreCase("")){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.added.arguments"));
            return;
        }
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            plugin.players.remove(args[0]);
            plugin.players.add(args[0]);
            List<String> data = new ArrayList<String>();
            data.add(args[1]);
            Configuration cgf = cp.load(file);
            cgf.set("config.players",plugin.players);
            cgf.set("config.accounts-ip." + args[0], data);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(plugin.getDataFolder(), "config.yml"));
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.added.success")
            .replaceAll("%player%", args[0]).replaceAll("%ip%" , args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String name() {
        return plugin.commandManager.add;
    }

    @Override
    public String info() {
        return "&a/tsecurity add <player> <ip>    &7Add a player and his ip to the list.";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
