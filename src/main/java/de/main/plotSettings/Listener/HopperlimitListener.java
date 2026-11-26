package de.main.plotSettings.Listener;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static de.main.plotSettings.Helper.PlotHelper.getPlot;
import static de.main.plotSettings.Helper.PlotHelper.getPlotPlayer;
import static de.main.plotSettings.Helper.messageHelper.getPrefix;

public class HopperlimitListener implements Listener {

    @EventHandler
    public void onHopperPlace(BlockPlaceEvent e)
    {
        // Wenn kein Hopper platziert wurde -> Abbruch
        if (e.getBlockPlaced().getType() != Material.HOPPER) return;

        // Holt sich den Spieler
        Player p = e.getPlayer();

        PlotPlayer<?> plotPlayer = getPlotPlayer(p);
        if (plotPlayer == null)
            return;

        Plot plot = getPlot(p);
        if (plot == null)
            return;

        if (!plotPlayer.getCurrentPlot().isOwner(p.getUniqueId()) && !p.hasPermission("plotsettings.hopper-admin"))
        {
            e.setCancelled(true);
            p.sendMessage("§cNur der Besitzer des Plots darf diesen Block platzieren!");
            return;
        }

        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;
        String path = "plots." + plot.getId() + ".settings.";

        int hopper_limit = plot_hopper_config.getInt(path + "hopper-limit",20);
        int hoppers_placed = plot_hopper_config.getInt(path + "hopper-placed");

        if ( handleHopper(hopper_limit,hoppers_placed,false,p) == -1)
            return;

        hoppers_placed = handleHopper(hopper_limit,hoppers_placed,false,p);

        plot_hopper_config.set(path + "hopper-placed",hoppers_placed);
        PlotSettings.getInstance().savePlot();
    }

    @EventHandler
    public void onHopperBreak(BlockBreakEvent e)
    {
        if (e.getBlock().getType() != Material.HOPPER)
            return;

        Player p = e.getPlayer();

        Plot plot = getPlot(p);
        if (plot == null)
            return;

        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;

        String path = "plots." + plot.getId() + ".settings.";

        int hopper_limit = plot_hopper_config.getInt(path + "hopper-limit",20);
        int hoppers_placed = plot_hopper_config.getInt(path + "hopper-placed");

        if ( handleHopper(hopper_limit,hoppers_placed,true,p) == -1)
            return;

        hoppers_placed = handleHopper(hopper_limit,hoppers_placed,true,p);

        plot_hopper_config.set(path + "hopper-placed",hoppers_placed);
        PlotSettings.getInstance().savePlot();
    }

    private int handleHopper(int hopper_limit, int hoppers_placed, boolean isBlockBroken,Player p)
    {
        if (isBlockBroken)
        {
            if (0 >= hoppers_placed)
            {
                return -1;
            }
            hoppers_placed--;
            return hoppers_placed;
        }
        else
        {
            // Falls Limit erreicht wurde, abbruch
            if (hoppers_placed >= hopper_limit) {
                p.sendMessage(getPrefix() + "§cDu kannst maximal §e" + hopper_limit + " §cHopper auf diesem Plot haben!");
                return -1;
            }

            hoppers_placed++;
            return hoppers_placed;
        }
    }
}
