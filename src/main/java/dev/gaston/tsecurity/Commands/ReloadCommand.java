package dev.gaston.tsecurity.Commands;

import net.md_5.bungee.api.CommandSender;

public class ReloadCommand extends SubCommands {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.reload")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        plugin.reloadConfig();
        sender.sendMessage(plugin.getMessageByConfig("message.reload"));
        sender.sendMessage(plugin.getMessageByConfig("message.ips").replaceAll("%ip_int%", Integer.toString(plugin.players.size())));
    }

    @Override
    public String name() {
        return plugin.commandManager.reload;
    }

    @Override
    public String info() {
        return "&a/tsecurity reload     &7Reload the config file of the plugin";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
