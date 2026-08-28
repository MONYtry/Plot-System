package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.MusicFlag;
import com.sk89q.worldedit.world.item.ItemTypes;
import de.main.plotSettings.GUI.*;
import de.main.plotSettings.PlotSettings;
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

        if (e.getView().getTitle() == null) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(player.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        //plot.setFlag(MusicFlag.class, "minecraft:music_disc_cat");

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(
                new NamespacedKey(PlotSettings.getInstance(), "action"),
                PersistentDataType.STRING
        );

        Player p = ((Player) e.getWhoClicked());

        if (action == null) return;
        switch (action) {

            case ("open_wallGUI"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                WallGUI.open(p);
                break;

            case ("open_biomeGUI"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                BiomeGUI.createBiomeGUI(p);
                break;

            case ("open_borderGUI"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                BorderGUI.createBorderGUI(p);
                break;

            case ("open_musicGUI"):
                p.getInventory().close();
                p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,1,1);
                MusicGUI.createMusicGUI(p);
                break;
        }
    }
}

