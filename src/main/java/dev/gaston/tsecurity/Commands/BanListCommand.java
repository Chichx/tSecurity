package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Objects.BPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class BanListCommand extends SubCommands{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.banlist")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
            for(String str : BPlayer.getBannedips()){
                sender.sendMessage(ChatColor.GREEN + str);
            }
    }

    @Override
    public String name() {
        return plugin.commandManager.banlist;
    }

    @Override
    public String info() {
        return ChatColor.translateAlternateColorCodes('&', "&a/tsecurity banlist    &7Show the list of banned IP's");
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
