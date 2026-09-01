package de.main.plotSettings.Commands;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.GUI.MainGUI;
import de.main.plotSettings.GUI.SettingsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlotSettingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("Plotsettings") && sender instanceof Player p)
        {
            // Holt sich die UUID des Spieler der auf dem Plot steht
            PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

            // Wenn es keine UUID gibt abbruch
            if (plotPlayer == null) return true;

            // Aktuelles Plot holen
            Plot plot = plotPlayer.getCurrentPlot();

            // Spieler ist nicht auf einem Grundstück!
            if (plot == null)
            {
                p.sendMessage("§7Kein Grundstück gefunden!");
                return true;
            }


            // Spieler ist nicht Owner des Grundstücks
            if (!plot.isOwner(p.getUniqueId()) && p.hasPermission("plotsettings.*"))
            {
                p.sendMessage("§cDieses Grundstück gehört dir nicht!");
                return true;
            }


            MainGUI.createMainGUI(p);
        }
        else
        {
            sender.sendMessage("Keine Erlaubnis");
        }

        return true;
    }
}
