package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.util.PatternUtil;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import static de.main.plotSettings.Helper.GUIHelper.checkQuitButton;
import static de.main.plotSettings.Helper.GUIHelper.getPlayerFromClick;
import static de.main.plotSettings.Helper.PlotHelper.getPlot;
import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;
import static de.main.plotSettings.Helper.permissionHelper.hasPermission;
import static de.main.plotSettings.Helper.placeholder.replacePlaceholderChat;

public class BorderGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.border","Ränder");
        title = replacePlaceholderChat(title,(Player) e.getWhoClicked());

        Player p = getPlayerFromClick(e,title);
        if (p == null)
            return;

        Plot plot = getPlot(p);
        if (plot == null)
            return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null || !item.hasItemMeta())
            return;

        if (checkQuitButton(item,p))
            return;

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
