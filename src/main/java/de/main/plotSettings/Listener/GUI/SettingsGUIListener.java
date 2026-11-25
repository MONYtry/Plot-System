package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.GUI.*;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SettingsGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("§7» §ePloteinstellungen")) return;

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
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);
        if (action == null)
            return;

        p.getInventory().close();
        Location pLoc = p.getLocation();

        switch (action) {

            case ("open_wallGUI"):
                WallGUI.open(p);
                break;

            case ("open_biomeGUI"):
                BiomeGUI.createBiomeGUI(p);
                break;

            case ("open_borderGUI"):
                BorderGUI.createBorderGUI(p);
                break;

            case ("open_musicGUI"):
                MusicGUI.createMusicGUI(p);
                break;

            case ("open_TimeGUI"):
                TimeGUI.createTimeGUI(p);
                break;

            case ("open_WeatherGUI"):
                WeatherGUI.createWeatherGUI(p);
                break;
        }
    }
}

