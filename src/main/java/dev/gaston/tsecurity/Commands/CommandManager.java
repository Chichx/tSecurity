package dev.gaston.tsecurity.Commands;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommandManager extends Command {
    private static HashMap<String, SubCommands> commands = new HashMap<String, SubCommands>();
    private Main plugin = Main.getInstance();

    public CommandManager(){
        super("tsecurity");
    }

    //my subcommands

    public String reload = "reload";
    public String add = "add";
    public String remove = "remove";
    public String help = "help";
    public String list = "list";
    public String ban = "ban";
    public String unban = "unban";
    public String banlist = "banlist";
    public String history = "history";
    public String purge = "purge";
    public String addip = "addip";
    public String removeip = "removeip";
    public String listip = "listip";

    public void setup(){
        plugin.getProxy().getPluginManager().registerCommand(plugin, this);
        this.getCommands().put(reload, new ReloadCommand());
        this.getCommands().put(add, new AddCommand());
        this.getCommands().put(remove, new RemoveCommand());
        this.getCommands().put(help, new HelpCommand());
        this.getCommands().put(list, new ListCommand());
        this.getCommands().put(ban , new BanCommand());
        this.getCommands().put(unban, new UnbanCommand());
        this.getCommands().put(banlist, new BanListCommand());
        this.getCommands().put(history, new HistoryCommand());
        this.getCommands().put(purge, new PurgeCommand());
        this.getCommands().put(addip, new AddIPCommand());
        this.getCommands().put(removeip, new RemoveIPCommand());
        this.getCommands().put(listip, new ListIPCommand());
    }
    public HashMap<String,SubCommands> getCommands(){
        return commands;
    }

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            if(plugin.onlyconsole) {
                sender.sendMessage(Main.getInstance().getMessageByConfig("message.noconsole"));
                return;
            }
        }

        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Not enough arguments, type /tsecurity help");
            return;
        }
        SubCommands sbcomand = this.getCommands().get(args[0].toLowerCase());
        if(sbcomand == null){
            sender.sendMessage(ChatColor.RED + "This command doesnt exist.");
            return;
        }

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(args));
        arrayList.remove(0);
        String[] str = new String[arrayList.size()];
        for(int x=0; x<arrayList.size();x++){
            str[x] = arrayList.get(x);
        }


        try{
            sbcomand.execute(sender, str);
        }catch (Exception e){
            e.printStackTrace();

        }
    }


}
