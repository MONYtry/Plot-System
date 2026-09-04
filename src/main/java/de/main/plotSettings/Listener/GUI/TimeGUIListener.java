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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class TimeGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!e.getView().getTitle().equalsIgnoreCase("§eUhrzeit")) return;

        if (e.getView().getTitle() == null) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(player.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Verhindert Fremdzugriff
        if (!plot.isOwner(plotPlayer.getUUID()))
        {
            player.sendMessage("§cNur der Besitzter des Grundstücks darf dies tun!");
            return;
        }

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);

        Player p = ((Player) e.getWhoClicked());

        if (action.equals("open.main"))
        {
            MainGUI.createMainGUI(p);
            p.playSound(p.getLocation(), Sound.BLOCK_BARREL_CLOSE,1,1);
        }
        if (action.startsWith("set_time_"))
        {
            String uhrzeitName = action.substring("set_time_".length());

            int price = TimeGUI.timeListe.get(uhrzeitName);

            // Keine Musik/kein Preis gefunden
            if (price == 0)
            {
                player.sendMessage("§cFür dieses Wetter wurde kein Preis festgelegt!");
                return;
            }

            setTime(plot, uhrzeitName, player,price);
        }
    }

    private void setTime(Plot plot,String uhrzeitName, Player p,double price)
    {

        String permission = "plotsettings.time." + uhrzeitName;
        int time = 0;
        double currentCash = PlotSettings.getEconomy().getBalance(p);

        if (!p.hasPermission(permission))
        {
            if (currentCash < price)
            {
                p.sendMessage("§cDu hast nicht genug Guthaben! §7(Preis: " + price +"$)");
                return;
            }

            PlotSettings.getEconomy().withdrawPlayer(p,price);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage("§7Du hast §c-" + price + "$ §7bezahlt!");

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

                p.sendMessage("§cZeit-Feature wurde entfernt!");
                p.closeInventory();
                return;

        }

        String newTime = String.valueOf(time);

        plot.setFlag(TimeFlag.class,newTime);
        p.sendMessage("§cZeit wurde erfolgreich geändert!");
        p.closeInventory();
    }
}
