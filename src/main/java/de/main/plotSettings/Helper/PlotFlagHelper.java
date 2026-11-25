package de.main.plotSettings.Helper;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.AnimalCapFlag;
import com.plotsquared.core.plot.flag.implementations.EntityCapFlag;
import com.plotsquared.core.plot.flag.implementations.MusicFlag;
import com.sk89q.worldedit.world.item.ItemType;
import com.sk89q.worldedit.world.item.ItemTypes;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlotFlagHelper {

    public static String getHopperCap(Plot plot)
    {
        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;

        String path = "plots." + plot.getId();

        // Erstellt anhand des Paths Variablen
        int hopper_limit = plot_hopper_config.getInt(path + ".settings.hopper-limit", 20);
        int hoppers_placed = plot_hopper_config.getInt(path + ".settings.hopper-placed");

        String hopperText = hoppers_placed + "/" + hopper_limit;

        return hopperText;
    }

    public static boolean getMusic(Plot plot)
    {
        ItemType music = plot.getFlag(MusicFlag.class);
        return music != null && !music.equals(ItemTypes.AIR);
    }
    public static int getEntityCap(Plot plot)
    {
        return plot.getFlag(EntityCapFlag.class);
    }

    public static int getAnimalCap(Plot plot)
    {
        return plot.getFlag(AnimalCapFlag.class);
    }
}
