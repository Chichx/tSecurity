package dev.gaston.tsecurity.Commands;


import dev.gaston.tsecurity.Main;
import dev.gaston.tsecurity.Objects.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public abstract class SubCommands {
    protected ConfigManager config = new ConfigManager();

    protected ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);

    protected Main plugin = Main.getInstance();

    public SubCommands(){

    }

    public abstract void execute(CommandSender sender, String[] args);
    public abstract String name();
    public abstract String info();
    public abstract String[] alias();
}
