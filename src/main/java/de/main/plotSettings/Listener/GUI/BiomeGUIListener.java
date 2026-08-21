package de.main.plotSettings.Listener;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
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

public class BiomeGUIListener implements Listener
{
    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e)
    {
        if (!e.getView().getTitle().equalsIgnoreCase("§eBiome")) return;
        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;

        Player p = ((Player) e.getWhoClicked());


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


        if (action == null) return;

        if (action.startsWith("set_biome_"))
        {
            String[] data = action.split("_");
            setBiome(p,data);
        }
    }



    private void setBiome(Player p,String[] data)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();

        // Holt sich den Namen von Data
        String biomeName = data[2].toLowerCase();

        // Versucht nun das Biome zu finden
        BiomeType biome = BiomeTypes.get(biomeName);

        if (data.length > 3)
        {
            String lastBiomeName = data[3].toLowerCase();

            // Versucht nun das Biome zu finden
            biome = BiomeTypes.get(biomeName + "_" + lastBiomeName);
        }

        // Es wird das biome gechanged
        // Nach ausführen wird eine Nachricht ausgegeben
        plot.getPlotModificationManager().setBiome(biome, () ->
        {
            p.sendMessage("§ePlot Biom §7wurde erfolgreich geändert!");
            p.playSound(p.getLocation(),Sound.ITEM_GOAT_HORN_SOUND_0,1,1);
        });
    }
}
