package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.GUI.*;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static de.main.plotSettings.Helper.GUIHelper.getPlayerFromClick;
import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;

public class SettingsGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.settings","§7» §ePloteinstellungen");

        Player p = getPlayerFromClick(e,title);
        if (p == null)
            return;

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
        p.playSound(pLoc, Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);

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

            default:
                sendPlayerMessage(p,"Hey es ist ein Fehler aufgetreten bitte konaktiere einen Administrator!");
                break;
        }
    }
}

