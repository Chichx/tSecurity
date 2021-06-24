package dev.gaston.tsecurity.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class HelpCommand extends SubCommands {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.help")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "=================================");
        for(SubCommands sb : plugin.commandManager.getCommands().values()){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',sb.info()));
        }

        sender.sendMessage(ChatColor.GREEN + "=================================");
    }

    @Override
    public String name() {
        return plugin.commandManager.help;
    }

    @Override
    public String info() {
        return "&a/tsecurity help     &7Show the list of commands.";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
