package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Objects.BPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class BanCommand extends SubCommands {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.ban")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        if(args.length <=0){
            sender.sendMessage(plugin.getMessageByConfig("message.ban.arguments"));
        return;
        }
        if(BPlayer.getBannedips().contains(args[0])){
            sender.sendMessage(plugin.getMessageByConfig("message.ban.already"));
            return;
        }
        BPlayer.getBannedips().add(args[0]);
        plugin.saveBannedIPs();
        sender.sendMessage(plugin.getMessageByConfig("message.ban.success").replaceAll("%ip%" , args[0]));
    }

    @Override
    public String name() {
        return plugin.commandManager.ban;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity ban <ip>     &7Ban an IP Address.");
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
