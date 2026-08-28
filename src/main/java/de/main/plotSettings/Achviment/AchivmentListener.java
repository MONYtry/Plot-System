package de.main.plotSettings.Achviment;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import de.main.plotSettings.PlotSettings;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class AchivmentListener implements Listener {

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent e)
    {
        //if (!e.getBlockPlaced().getWorld().getName().equalsIgnoreCase("Citybuild")) return;

        Player p = e.getPlayer();

        FileConfiguration playerDataFile = PlotSettings.getInstance().playerData;

        org.bukkit.Location bukkitLocation = e.getBlockPlaced().getLocation();

        Location psLocation = Location.at(
                bukkitLocation.getWorld().getName(),
                bukkitLocation.getBlockX(),
                bukkitLocation.getBlockY(),
                bukkitLocation.getBlockZ()
        );

        Plot plot = Plot.getPlot(psLocation);
        if (plot == null) return;

        String plotPath = "players." + p.getUniqueId();
        Block placedBlock = e.getBlockPlaced();
        String blockName = placedBlock.getType().name();
        String path = plotPath + ".blocks.placed." + blockName;

        // Wenn File gefunden wurde
        int placedBlockCount = playerDataFile.getInt(path);
        placedBlockCount++;

        playerDataFile.set(path,placedBlockCount);
        PlotSettings.getInstance().savePlayerData();


    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e) {
        //if (!e.getBlock().getWorld().getName().equalsIgnoreCase("Citybuild")) return;

        Player p = e.getPlayer();

        FileConfiguration playerData = PlotSettings.getInstance().playerData;

        org.bukkit.Location bukkitLocation = e.getBlock().getLocation();

        Location psLocation = Location.at(
                bukkitLocation.getWorld().getName(),
                bukkitLocation.getBlockX(),
                bukkitLocation.getBlockY(),
                bukkitLocation.getBlockZ()
        );

        Plot plot = Plot.getPlot(psLocation);
        if (plot == null) return;

        String plotPath = "players." + p.getUniqueId();
        Block placedBlock = e.getBlock();
        String blockName = placedBlock.getType().name();
        String path = plotPath + ".blocks.destoryed." + blockName;

        // Wenn File gefunden wurde
        int brokenBlockCount = playerData.getInt(path);
        brokenBlockCount++;

        if (brokenBlockCount == 0)
        {
            playerData.set(path, null);
            PlotSettings.getInstance().savePlayerData();
            return;
        }
        
        playerData.set(path, brokenBlockCount);
        PlotSettings.getInstance().savePlayerData();

        AchivmentManager.checkBuildingAchivment(placedBlock.getType(),p);
    }

}
