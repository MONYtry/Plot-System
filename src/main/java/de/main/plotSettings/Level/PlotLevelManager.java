package de.main.plotSettings.Level;

import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.PlotSettings;
import org.bukkit.configuration.file.FileConfiguration;

public class PlotLevelManager {

    public static double getLevel(Plot plot)
    {
        String path = "plots." + plot.getId();
        FileConfiguration plotCFG = PlotSettings.getInstance().plot;

        int currentLevel = plotCFG.getInt(path + ".level",1);
        return currentLevel;
    }

    public static void addLevel(Plot plot)
    {
        String path = "plots." + plot.getId();
        FileConfiguration plotCFG = PlotSettings.getInstance().plot;
        int currentLevel = plotCFG.getInt(path + ".level",1);

        currentLevel++;

        plotCFG.set(path + ".level",currentLevel);
        PlotSettings.getInstance().savePlot();

    }
}
