package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Objects.BPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class UnbanCommand extends SubCommands{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.unban")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=0){
            sender.sendMessage(plugin.getMessageByConfig("message.unban.arguments"));
            return;
        }
        if(!BPlayer.getBannedips().contains(args[0])){
            sender.sendMessage(plugin.getMessageByConfig("message.unban.error"));
            return;
        }
        BPlayer.getBannedips().remove(args[0]);
        plugin.saveBannedIPs();
        sender.sendMessage(plugin.getMessageByConfig("message.unban.success").replaceAll("%ip%", args[0]));

    }

    @Override
    public String name() {
        return plugin.commandManager.unban;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity unban <ip>     &7Unban an IP Address.");
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
