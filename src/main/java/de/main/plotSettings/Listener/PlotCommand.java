package de.main.plotSettings.Listener;

import de.main.plotSettings.GUI.Plot.PlotInfoGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlotCommand implements Listener {

    @EventHandler
    public void onPlotCommand(PlayerCommandPreprocessEvent e)
    {
        if (e.getMessage().equalsIgnoreCase("/plot"))
        {
            e.setCancelled(true);
            PlotInfoGUI.createInfoGUI(e.getPlayer());

        }
    }
}
