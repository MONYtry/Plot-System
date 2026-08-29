package de.main.plotSettings;

import de.main.plotSettings.Achviment.Achivment;
import de.main.plotSettings.Achviment.AchivmentListener;
import de.main.plotSettings.Achviment.AchivmentManager;
import de.main.plotSettings.Commands.PlotSettingsCommand;
import de.main.plotSettings.Listener.GUI.*;
import de.main.plotSettings.Listener.HopperlimitListener;
import de.main.plotSettings.Listener.PlotCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.io.IOException;

public final class PlotSettings extends JavaPlugin {


    private static PlotSettings instance;
    public FileConfiguration gui;
    public FileConfiguration plot;
    public FileConfiguration achivment;
    public FileConfiguration messages;
    public FileConfiguration playerData;

    private File playerDataFile;
    private File plotFile;

    private static Economy economy;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();

        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {



        // Listener
        Bukkit.getPluginManager().registerEvents(new SettingsGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new BiomeGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new HopperlimitListener(),this);
        Bukkit.getPluginManager().registerEvents(new MainGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new BorderGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new MusicGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new WallGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new AchivmentListener(),this);
        Bukkit.getPluginManager().registerEvents(new WeatherGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new TimeGUIListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlotCommand(),this);

        // Command
        getCommand("Plotsettings").setExecutor(new PlotSettingsCommand());

        instance = this;

        AchivmentManager.registerAchivments();

        saveResource("gui.yml",false);
        saveResource("plot.yml",false);
        saveResource("Achivments.yml",false);
        saveResource("messages.yml",false);
        saveResource("playerData.yml",false);

        gui = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"gui.yml"));
        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"messages.yml"));
        plot = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "plot.yml"));
        achivment = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "Achivments.yml"));
        playerData = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"playerData.yml"));

        plotFile = new File(getDataFolder(), "plot.yml");
        playerDataFile = new File(getDataFolder(),"playerData.yml");

        if (!plotFile.exists()) {
            try {
                plotFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!playerDataFile.exists())
        {
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        plot = YamlConfiguration.loadConfiguration(plotFile);
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        // Speichern
        saveDefaultConfig();

        if (!setupEconomy())
        {
            getLogger().warning("Vault/Economy wurde nicht gefunden!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Instance setzen
    public static PlotSettings getInstance() {
        return instance;
    }

    public void savePlot() {
        try {
            plot.save(plotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savePlayerData()
    {
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
