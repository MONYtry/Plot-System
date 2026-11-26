package de.main.plotSettings.Helper;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;

import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;

public class PlotHelper {

    public static Plot getPlot(Player p)
    {
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null)
            return null;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) {
            sendPlayerMessage(p,"§cDu stehst auf keinem Plot!");
            return null;
        }
        return plot;
    }

    public static PlotPlayer<?> getPlotPlayer(Player p)
    {
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null)
            return null;

        return plotPlayer;
    }
}
