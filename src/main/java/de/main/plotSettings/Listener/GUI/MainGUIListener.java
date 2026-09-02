package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.GUI.*;
import de.main.plotSettings.PlotSettings;
import de.main.plotSettings.Rewards.rewardGUI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class MainGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("Hautpmenü") &&
                !e.getView().getTitle().equalsIgnoreCase("§7» §ePloteinstellungen")) return;

        if (e.getView().getTitle() == null) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(player.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key holen
        String action = itemMeta.getPersistentDataContainer().get(
                new NamespacedKey(PlotSettings.getInstance(), "action"),
                PersistentDataType.STRING
        );

        Player p = ((Player) e.getWhoClicked());

        if (action == null) return;
        switch (action) {

            case ("open_settingsGUI"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                SettingsGUI.createMainGUI(p);
                break;

            case ("open_achievementGUI"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                AchievementGUI.createAchievementGUI(p);
                break;

            case ("open_WeatherGUI"):
                p.getInventory().close();
                p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_CLOSE,1,1);
                WeatherGUI.createWeatherGUI(p);
                break;

            case ("open_TimeGUI"):
                p.getInventory().close();
                p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_CLOSE,1,1);
                TimeGUI.createTimeGUI(p);
                break;

            case ("open_rewardGUI"):
                p.getInventory().close();
                p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_CLOSE,1,1);
                rewardGUI.createRewardGUI(p);
                break;
            default:
                if (action.startsWith("open_plot_"))
                {
                    String plotId = action.substring("open_plot_".length());
                    p.performCommand("p v " + plotId);
                }
        }
    }
}

