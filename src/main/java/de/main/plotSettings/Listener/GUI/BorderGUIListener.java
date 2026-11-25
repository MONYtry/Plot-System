package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.util.PatternUtil;
import de.main.plotSettings.GUI.MainGUI;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;
import static de.main.plotSettings.Helper.permissionHelper.hasPermission;

public class BorderGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("Ränder"))
            return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null)
            return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null)
            return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null || !item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();


        MainGUI.createMainGUI(p);
        p.playSound(p.getLocation(), Sound.BLOCK_BARREL_CLOSE,1,1);
        setBorder(e,plot);
    }

    public static void setBorder(InventoryClickEvent e, Plot plot)
    {
        // Nullpointer vermeiden
        if (e.getCurrentItem() == null)
            return;

        // Variablen erstellen
        Material type = e.getCurrentItem().getType();
        Player p = (Player) e.getWhoClicked();

        String perm = "plotsettings.border." + type.name().toLowerCase();

        // Wenn keine Rechte
        if (!hasPermission(p,perm))
        {
            sendPlayerMessage(p,"§cKeine Rechte?");
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

        String displayItem = type.toString().replace("_", " ");
        sendPlayerMessage(p,"§aRand wurde erfolgreich auf: "  + displayItem + " gesetzt");
        p.closeInventory();

    }
}
