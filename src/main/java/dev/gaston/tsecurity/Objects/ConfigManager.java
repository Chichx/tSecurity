package dev.gaston.tsecurity.Objects;

import dev.gaston.tsecurity.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    Configuration config;
    Configuration logger;
    Configuration message;

    public Configuration getConfig(){
        if(config == null){
            reloadConfig();
        }
        return this.config;
    }
    

    public void reloadConfig() {
        try{
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void registerConfig() {
        if(!Main.getInstance().getDataFolder().exists()) {
            Main.getInstance().getDataFolder().mkdir();
        }
            File file = new File(Main.getInstance().getDataFolder(), "config.yml");
            if(!file.exists()){
                try {
                    InputStream in = Main.getInstance().getResourceAsStream("config.yml");
                    Files.copy(in, file.toPath());
                    this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }

    //logger
    public Configuration getLogger(){
        if(logger == null){
            reloadLogger();
        }
        return this.logger;
    }


    public void reloadLogger() {
        try{
            this.logger = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "logger.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void registerLogger() {
            File file = new File(Main.getInstance().getDataFolder(), "logger.yml");
            if(!file.exists()){
                try {
                    InputStream in = Main.getInstance().getResourceAsStream("logger.yml");
                    Files.copy(in, file.toPath());
                    this.logger = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "logger.yml"));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }


    //message
    public Configuration getMessage(){
        if(message == null){
            reloadMessage();
        }
        return this.message;
    }


    public void reloadMessage() {
        try{
            this.message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "message.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void registerMessage() {
        File file = new File(Main.getInstance().getDataFolder(), "message.yml");
        if(!file.exists()){
            try {
                InputStream in = Main.getInstance().getResourceAsStream("message.yml");
                Files.copy(in, file.toPath());
                this.message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "message.yml"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}

