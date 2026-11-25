package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.plot.flag.implementations.MusicFlag;
import com.plotsquared.core.plot.flag.implementations.TimeFlag;
import com.plotsquared.core.plot.flag.implementations.WeatherFlag;
import com.plotsquared.core.util.PatternUtil;
import de.main.plotSettings.GUI.MainGUI;
import de.main.plotSettings.GUI.MusicGUI;
import de.main.plotSettings.GUI.TimeGUI;
import de.main.plotSettings.GUI.WeatherGUI;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

import static de.main.plotSettings.Helper.GUIHelper.checkQuitButton;
import static de.main.plotSettings.Helper.GUIHelper.getPlayerFromClick;
import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;
import static de.main.plotSettings.Helper.permissionHelper.hasPermission;

public class TimeGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.time","§eUhrzeit");

        Player p = getPlayerFromClick(e,title);
        if (p == null)
            return;

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Verhindert Fremdzugriff
        if (!plot.isOwner(plotPlayer.getUUID()))
        {
            p.sendMessage("§cNur der Besitzter des Grundstücks darf dies tun!");
            return;
        }

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);

        if (checkQuitButton(item,p))
            return;

        if (action.startsWith("set_time_"))
        {
            String uhrzeitName = action.substring("set_time_".length());

            int price = TimeGUI.timeListe.get(uhrzeitName);

            // Keine Musik/kein Preis gefunden
            if (price == 0)
            {
                p.sendMessage("§cFür dieses Wetter wurde kein Preis festgelegt!");
                return;
            }

            setTime(plot, uhrzeitName, p,price);
        }
    }

    private void setTime(Plot plot,String uhrzeitName, Player p,double price)
    {
        String permission = "plotsettings.time." + uhrzeitName;
        int time = 0;
        double currentCash = PlotSettings.getEconomy().getBalance(p);

        if (!hasPermission(p,permission))
        {
            if (currentCash < price)
            {
                sendPlayerMessage(p,"§cDu hast nicht genug Guthaben! §7(Preis: " + price +"$)");
                return;
            }

            PlotSettings.getEconomy().withdrawPlayer(p,price);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + p.getName() + " permission set " + permission + " true");
            sendPlayerMessage(p,"§7Du hast §c-" + price + "$ §7bezahlt!");

        }

        switch (uhrzeitName)
        {
            case ("Mittags"):
                time = 2000;
                break;

            case ("Nachts"):
                time = 14000;
                break;

            case ("Frühs"):
                time =  23000;
                break;

            case ("remove"):
                plot.removeFlag(TimeFlag.class);
                p.resetPlayerTime();

                sendPlayerMessage(p,"§cZeit-Feature wurde entfernt!");
                p.closeInventory();
                return;
        }

        String newTime = String.valueOf(time);

        plot.setFlag(TimeFlag.class,newTime);
        sendPlayerMessage(p,"§cZeit wurde erfolgreich geändert!");
        p.closeInventory();
    }
}
