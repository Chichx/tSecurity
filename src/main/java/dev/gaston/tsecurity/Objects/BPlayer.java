package dev.gaston.tsecurity.Objects;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BPlayer {
    private String ip;
    private Integer attempts = 0;
    private static HashMap<String, BPlayer> players = new HashMap<String, BPlayer>();
    private static HashMap<String, List<String>> allowedIP = new HashMap<String, List<String>>();
    private static List<String> bannedips = new ArrayList<String>();


    public BPlayer(String ip){
        this.ip = ip;
        players.put(this.ip, this);
    }

    public static BPlayer getBPlayer(ProxiedPlayer pp){
        if(players.containsKey(pp.getAddress().getHostString())){
            return players.get(pp.getAddress().getHostString());
        }
        return new BPlayer(pp.getAddress().getHostString());
    }

    public static BPlayer getBPlayer(String ip){
        if(players.containsKey(ip)){
            return players.get(ip);
        }
        return new BPlayer(ip);
    }


    public static List<String> getBannedips() {
        return bannedips;
    }

    public static void setBannedips(List<String> bannedips) {
        BPlayer.bannedips = bannedips;
    }

    public boolean isBanned() {
        return bannedips.contains(this.ip);
    }

    public void setBanned(Boolean bol) {
        if(bol){
            if(!bannedips.contains(ip)){
                bannedips.add(this.ip);
            }
        }else{
            bannedips.remove(this.ip);
        }

    }


    public Integer getAttempts() {
        return attempts;
    }

    public void addAttempts() {
        this.attempts = this.attempts+1;
    }

    public static HashMap<String, List<String>> getAllowedIP() {
        return allowedIP;
    }

}
