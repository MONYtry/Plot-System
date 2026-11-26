package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.GUI.*;
import de.main.plotSettings.PlotSettings;
import de.main.plotSettings.Rewards.rewardGUI;
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
import static de.main.plotSettings.Helper.placeholder.replacePlaceholderChat;


public class MainGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.main","Hauptmenü");
        title = replacePlaceholderChat(title,(Player) e.getWhoClicked());

        Player p = getPlayerFromClick(e,title);
        if (p == null)
            return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null || !item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key holen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);
        if (action == null)
            return;


        Location pLoc = p.getLocation();
        p.playSound(pLoc, Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);

        switch (action) {

            case ("open_settingsGUI"):
                SettingsGUI.createMainGUI(p);
                break;

            case ("open_achievementGUI"):
                AchievementGUI.createAchievementGUI(p);
                break;

            case ("open_rewardGUI"):
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

