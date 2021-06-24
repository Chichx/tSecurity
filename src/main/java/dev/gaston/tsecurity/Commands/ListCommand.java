package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.CommandSender;

public class ListCommand extends SubCommands {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("tsecurity.list")){
            sender.sendMessage(plugin.getMessageByConfig("message.nopermission"));
            return;
        }
        sender.sendMessage(Main.getInstance().getMessageByConfig("message.list.header"));
        for(String str : Main.getInstance().players){
            sender.sendMessage(Main.getInstance().getMessageByConfig("message.list.rows")
            .replaceAll("%player%", str));
        }

        sender.sendMessage(Main.getInstance().getMessageByConfig("message.list.footer"));

    }

    @Override
    public String name() {
        return plugin.commandManager.list;
    }

    @Override
    public String info() {
        return "&a/tsecurity list        &7Show the list of accounts.";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }
}
