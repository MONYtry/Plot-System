package de.main.plotSettings.Listener;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.util.PatternUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BorderGUIListener implements Listener {

    public static void setBorder(InventoryClickEvent e, Plot plot)
    {
        // Nullpointer vermeiden
        if (e.getCurrentItem() == null) return;

        // Variablen erstellen
        Material type = e.getCurrentItem().getType();
        Player p = (Player) e.getWhoClicked();

        // Permission variable
        String perm = "plotsettings.border." + type.name().toLowerCase();
        String displayItem = type.toString().replace("_", " ");

        // Wenn keine Rechte
        if (!p.hasPermission(perm))
        {
            p.sendMessage("§cDu hast keine Rechte dafür!");
            p.closeInventory();
            e.setCancelled(true);
            return;
        }

        // Plot getten und Change von Border
        PlotManager manager = plot.getArea().getPlotManager();
        for (Plot merged : plot.getConnectedPlots()) {
            manager.setComponent(
                    merged.getId(),
                    "BORDER",
                    PatternUtil.parse(null, "minecraft:" + type.name().toLowerCase()),
                    null,
                    null
            );
        }

        p.sendMessage("§aRand wurde erfolgreich auf: "  + displayItem + " gesetzt");

        p.closeInventory();

        e.setCancelled(true);
    }
}
