package de.main.plotSettings.Manager;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.AnimalCapFlag;
import com.plotsquared.core.plot.flag.implementations.EntityCapFlag;
import com.plotsquared.core.plot.flag.implementations.MobCapFlag;

public class PlotFlagHelper {

    public static int getEntityCap(Plot plot)
    {
        return plot.getFlag(EntityCapFlag.class);
    }

    public static int getAnimalCap(Plot plot)
    {
        return plot.getFlag(AnimalCapFlag.class);
    }
}
