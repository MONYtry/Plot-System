package de.main.plotSettings.Manager;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.AnimalCapFlag;
import com.plotsquared.core.plot.flag.implementations.EntityCapFlag;
import com.plotsquared.core.plot.flag.implementations.MobCapFlag;
import de.main.plotSettings.PlotSettings;
import org.bukkit.configuration.file.FileConfiguration;

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

    public static int getEntityCap(Plot plot)
    {
        return plot.getFlag(EntityCapFlag.class);
    }

    public static int getAnimalCap(Plot plot)
    {
        return plot.getFlag(AnimalCapFlag.class);
    }
}
