package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.util.List;

public class ListIPCommand extends SubCommands{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.iplist")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=0){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.listip.arguments"));
            return;
        }
        if(!plugin.players.contains(args[0])){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.listip.error"));
            return;
        }
        config.reloadConfig();
        List<String> data = config.getConfig().getStringList("config.accounts-ip." + args[0].toLowerCase());
        sender.sendMessage(Main.getInstance().getMessageByConfig("message.listip.header"));
        for(String str : data){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.listip.rows")
            .replaceAll("%ip%", str));
        }
        sender.sendMessage(Main.getInstance().getMessageByConfig("message.listip.footer"));




    }

    @Override
    public String name() {
        return plugin.commandManager.listip;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity listip <player>   &7List all IP's of a player.");

    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
