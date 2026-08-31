package de.main.plotSettings.Level;

import de.main.plotSettings.PlotSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlotLevelManager {

    public static int getLevel(UUID uuid) {

        if (uuid == null)
        {
            return 1;
        }
        String path = "players." + uuid + ".plotlevel.level";
        FileConfiguration plotCFG = PlotSettings.getInstance().playerData;

        return plotCFG.getInt(path, 1);
    }

    public static void addLevel(Player p) {
        if (p == null)
        {
            p.sendMessage("§7Ein §ekritischer Fehler §7ist aufgetreten!");
            p.sendMessage("§cKontaktiere einen Admin!");
        }
        String path = "players." + p.getUniqueId() + ".plotlevel.level";
        FileConfiguration plotCFG = PlotSettings.getInstance().playerData;

        int currentLevel = plotCFG.getInt(path, 1);
        currentLevel++;

        plotCFG.set(path, currentLevel);
        PlotSettings.getInstance().savePlayerData();
    }
}