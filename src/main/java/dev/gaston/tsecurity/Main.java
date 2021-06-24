package dev.gaston.tsecurity;

import dev.gaston.tsecurity.Commands.CommandManager;
import dev.gaston.tsecurity.Objects.BPlayer;
import dev.gaston.tsecurity.Objects.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public final class Main extends Plugin implements Listener {
    private ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);
    public ArrayList<String> players = new ArrayList<String>();
    public HashMap<String, ArrayList<String>> data = new HashMap<String,ArrayList<String>>();
    private static Main instance;
    private ConfigManager config;
    public CommandManager commandManager;
    public boolean logger, onlyconsole;
    private String kickmessage, permission, alert;
    private Integer attempts;
    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigManager();
        config.registerConfig();
        config.registerMessage();
        commandManager = new CommandManager();
        commandManager.setup();
        // setting up
        kickmessage = ChatColor.translateAlternateColorCodes('&', config.getMessage().getString("message.kick"));
        permission = config.getConfig().getString("config.permission");
        alert = config.getMessage().getString("message.alert");
        attempts = Integer.parseInt(config.getConfig().getString("config.ban_after"));
        onlyconsole = Boolean.parseBoolean(config.getConfig().getString("config.onlyconsole"));
        logger = Boolean.parseBoolean(config.getConfig().getString("config.logger"));
        players = (ArrayList<String>) config.getConfig().getStringList("config.players");
        BPlayer.setBannedips(config.getConfig().getStringList("config.banned_ips"));
        if(logger){
            config.registerLogger();
            for(String path : players){
                    data.put(path, (ArrayList<String>) config.getLogger().getStringList("logger.try." + path));
            }

        }
        getProxy().getConsole().sendMessage(getMessageByConfig("message.ips").replaceAll("%ip_int%", Integer.toString(players.size())));
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);


    }
    private void saveConfig(){
        File file = new File(getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getMessageByConfig(String path){
        return ChatColor.translateAlternateColorCodes('&',config.getMessage().getString("message.prefix")  + config.getMessage().getString(path));
    }
    public void reloadConfig(){
        saveConfig();
        config.reloadConfig();
        players.clear();
        for(String str : config.getConfig().getStringList("config.players")){
            players.add(str.toLowerCase());
        }
        attempts = Integer.parseInt(config.getConfig().getString("config.ban_after"));
        kickmessage = ChatColor.translateAlternateColorCodes('&', config.getMessage().getString("message.kick"));
        permission = config.getConfig().getString("config.permission");
        alert = ChatColor.translateAlternateColorCodes('&',  config.getMessage().getString("message.alert"));
        logger = Boolean.parseBoolean(config.getConfig().getString("config.logger"));
        BPlayer.setBannedips(config.getConfig().getStringList("config.banned_ips"));
        if(logger){
            config.registerLogger();
            for(String path : players){
                data.put(path, (ArrayList<String>) config.getLogger().getStringList("logger.try." + path));
            }

        }
        getProxy().getConsole().sendMessage(ChatColor.GREEN + Integer.toString(players.size()) +" players added to the tSecurity list.");

    }


    public void saveBannedIPs() {
        File file = new File(getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("config.banned_ips", BPlayer.getBannedips());
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "config.yml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        config.reloadConfig();
    }

    @EventHandler
    public void blockedip(PreLoginEvent e){
        BPlayer bPlayer = BPlayer.getBPlayer(e.getConnection().getAddress().getHostString());
        if(bPlayer.isBanned()){
            e.getConnection().disconnect(getMessageByConfig("message.default_banned_message"));
        }
    }

   @EventHandler
    public void postlogin(PostLoginEvent e){
       getProxy().getScheduler().runAsync(Main.getInstance(), new Runnable() {
           @Override
           public void run() {
               try {
                   BPlayer bPlayer = BPlayer.getBPlayer(e.getPlayer());
                   String name = e.getPlayer().getName();
                   String ip = e.getPlayer().getAddress().getHostString();
                   if(players.contains(name.toLowerCase())){
                       config.reloadConfig();
                       BPlayer.getAllowedIP().put(name.toLowerCase(), config.getConfig().getStringList("config.accounts-ip." + name.toLowerCase()));
                       // validar la ip

                       if(!BPlayer.getAllowedIP().get(name.toLowerCase()).contains(ip)){
                           bPlayer.addAttempts();
                           if(bPlayer.getAttempts() >= getAttempts()){
                               getProxy().getPluginManager().dispatchCommand(getProxy().getConsole(), config.getConfig().getString("config.ban_command")
                               .replaceAll("%ip%", ip));
                           }
                           e.getPlayer().disconnect(ChatColor.translateAlternateColorCodes('&',config.getMessage().getString("message.prefix") + getKickmessage()));
                           ProxyServer.getInstance().getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', getAlert()
                                   .replaceAll("%ip%", ip)
                                   .replaceAll("%account%", name)));

                           for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()){
                               if(pp.hasPermission(getPermission())){
                                   pp.sendMessage(ChatColor.translateAlternateColorCodes('&', getAlert()
                                           .replaceAll("%ip%", ip)
                                           .replaceAll("%account%", name)));

                               }
                           }
                           if(logger){
                               LocalDateTime now = LocalDateTime.now();
                               if(!data.containsKey(name)){
                                   data.put(name, new ArrayList<String>());
                               }
                               data.get(name).add(ip + ", tried to join at: " + now);
                               File file = new File(getDataFolder(), "logger.yml");
                               try {
                                   Configuration cgf = cp.load(file);
                                   for(String str : data.keySet()){
                                       cgf.set("logger.try." + str, data.get(str));
                                   }
                                   ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "logger.yml"));

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                   }
               } catch(Exception ex) {
                   ex.printStackTrace();
               }
           }
       });





   }


    public String getKickmessage() {
        return kickmessage;
    }

    public String getPermission() {
        return permission;
    }

    public String getAlert() {
        return alert;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public static Main getInstance(){
        return instance;
    }
}
