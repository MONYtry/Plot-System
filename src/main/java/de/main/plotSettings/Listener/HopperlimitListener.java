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

public class HopperlimitListener implements Listener {

    @EventHandler
    public void onHopperPlace(BlockPlaceEvent e)
    {
        // Wenn kein Hopper platziert wurde -> Abbruch
        if (e.getBlockPlaced().getType() != Material.HOPPER) return;

        // Holt sich den Spieler
        Player p = e.getPlayer();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // ################ ANTI - TRUST ################ //
        if (!plotPlayer.getCurrentPlot().isOwner(p.getUniqueId()) && !p.hasPermission("plotsettings.hopper-admin"))
        {
            e.setCancelled(true);
            p.sendMessage("§cNur der Besitzer des Plots darf diesen Block platzieren!");
            return;
        }

        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;
        FileConfiguration messages = PlotSettings.getInstance().messages;

        String prefix = messages.getString("messages.prefix");
        String path = "plots." + plot.getId();

        int hopper_limit = plot_hopper_config.getInt(path + ".settings.hopper-limit",20);
        int hoppers_placed = plot_hopper_config.getInt(path + ".settings.hopper-placed");


        if (hoppers_placed >= hopper_limit) {
            e.setCancelled(true);
            p.sendMessage(prefix + "§cDu kannst maximal §e" + hopper_limit + " §cHopper auf diesem Plot haben!");
            return;
        }

        hoppers_placed++;
        String current_state = "§e[#" + hoppers_placed + "] §7/ §e[#" + hopper_limit + "]";
        p.sendMessage(prefix + "§7Hopper wurde erfolgreich Platziert "+ current_state);

        plot_hopper_config.set(path + ".settings.hopper-placed",hoppers_placed);
        PlotSettings.getInstance().savePlot();
    }

    @EventHandler
    public void onHopperBreak(BlockBreakEvent e)
    {
        if (e.getBlock().getType() != Material.HOPPER) return;

        Player p = e.getPlayer();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null);

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;
        FileConfiguration messages = PlotSettings.getInstance().messages;

        String prefix = messages.getString("messages.prefix");
        String path = "plots." + plot.getId();


        int hopper_limit = plot_hopper_config.getInt(path + ".settings.hopper-limit",20);
        int hoppers_placed = plot_hopper_config.getInt(path + ".settings.hopper-placed");

        hoppers_placed--;
        String current_state = "§e[#" + hoppers_placed + "] §7/ §e[#" + hopper_limit + "]";
        p.sendMessage(prefix + "§7Hopper wurde erfolgreich entfernt " + current_state);

        plot_hopper_config.set(path + ".settings.hopper-placed",hoppers_placed);
        PlotSettings.getInstance().savePlot();
    }
}
