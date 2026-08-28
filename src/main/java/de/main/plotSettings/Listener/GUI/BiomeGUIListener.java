package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import de.main.plotSettings.GUI.BiomeGUI;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BiomeGUIListener implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        // Prüfen, ob es das Biome-GUI ist
        if (!e.getView().getTitle().equalsIgnoreCase("§eBiome")) {
            return;
        }

        e.setCancelled(true);

        // Nur Spieler
        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        // Item prüfen
        ItemStack item = e.getCurrentItem();

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        // Action-Key holen
        NamespacedKey key = new NamespacedKey(
                PlotSettings.getInstance(),
                "action"
        );

        String action = itemMeta.getPersistentDataContainer().get(
                key,
                PersistentDataType.STRING
        );

        if (action == null) {
            return;
        }

        // Prüfen, ob es eine Biome-Action ist
        if (action.startsWith("set_biome_")) {
            setBiome(p, action);
        }
    }


    private void setBiome(Player p, String action) {

        // PlotPlayer holen
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        if (plotPlayer == null) {
            return;
        }

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();

        if (plot == null) {
            p.sendMessage("§cDu stehst auf keinem Plot!");
            return;
        }

        String biomeName = action.substring("set_biome_".length()).toLowerCase();

        // Biome suchen
        BiomeType biome = BiomeTypes.get(biomeName);

        if (biome == null) {
            p.sendMessage("§cDieses Biom existiert nicht!");
            return;
        }

        // Preis des Biomes holen
        Double price = BiomeGUI.biomeListe.get(biome);

        if (price == null) {
            p.sendMessage("§cFür dieses Biom wurde kein Preis festgelegt!");
            return;
        }

        // Permission erstellen
        String permission = "plotsettings.biome." + biomeName;

        // Prüfen, ob Spieler das Biom bereits besitzt
        if (!p.hasPermission(permission)) {

            double currentCash = PlotSettings.getEconomy().getBalance(p);

            // Nicht genug Geld
            if (currentCash < price) {
                p.sendMessage("§cDu hast nicht genug Geld!");
                return;
            }

            // Geld abziehen
            PlotSettings.getEconomy().withdrawPlayer(p, price);

            // Permission über LuckPerms setzen
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set " + permission + " true");
        }

        // Biome auf dem Plot setzen
        plot.getPlotModificationManager().setBiome(biome, () -> {
                    p.sendMessage("§ePlot-Biom §7wurde erfolgreich geändert!");
                    p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
                }
        );
    }
}