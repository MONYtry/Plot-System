package de.main.plotSettings.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.Level.PlotLevelManager;
import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainGUI {

    public static void test(Player p)
    {
        Inventory mainGUI = Bukkit.createInventory(null,36,"Hautpmenü");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(mainGUI,"mainGUI");

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        Plot plot = plotPlayer.getCurrentPlot();

        // Erstellt Dinge :3
        createRatings(plot, mainGUI);
        createPlotsListGUI(p,mainGUI);
    }

    public static void createPlotsListGUI(Player p,Inventory inventory)
    {
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        if (plotPlayer == null) return;

        List<Plot> plots = new ArrayList<>(plotPlayer.getPlots());

        // Größte Merges zuerst
        plots.sort(Comparator.comparingInt((Plot plot) -> plot.getConnectedPlots().size()).reversed());

        // Plots, die übersprungen werden sollen
        List<Plot> blacklist = new ArrayList<>();

        int slot = 11;
        int anzahl = 0;

        for (Plot plot : plots)
        {
            // Ist dieses Plot auf der Blacklist?
            if (blacklist.contains(plot))
            {
                continue;
            }

            // Alle verbundenen Plots auf die Blacklist
            blacklist.addAll(plot.getConnectedPlots());

            // Item erstellen
            ItemStack item = new ItemStack(Material.GRASS_BLOCK);

            ItemMeta meta = item.getItemMeta();

            int mergeCount = plot.getConnectedPlots().size();

            meta.setDisplayName("§a#" + (anzahl + 1) + " §7Plot §e" + plot.getId());

            List<String> lore = new ArrayList<>();

            lore.add("");
            lore.add("§7ID: §e" + plot.getId());
            lore.add("§7Owner: §e" + p.getName());
            lore.add("§7Merge: §e" + mergeCount);
            lore.add("");
            lore.add("§eKlicke, um das Plot auszuwählen!");

            meta.setLore(lore);
            String action = "open_plot_" + plot.getId();
            meta.getPersistentDataContainer().set(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING, action);

            item.setItemMeta(meta);

            inventory.setItem(slot, item);

            slot++;
            anzahl++;

            // Maximal 5 anzeigen
            if (anzahl >= 5)
            {
                break;
            }
        }

        p.openInventory(inventory);
    }

    private static void createRatings(Plot plot, Inventory inventory)
    {
        ItemStack ratingsItem = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta ratingsItemMeta = ratingsItem.getItemMeta();

        ratingsItemMeta.setDisplayName("§6Plot-Infomationen");

        // Rating
        double averageRating = plot.getAverageRating();

        // Lore erstellen
        ArrayList biomeItemLore = new ArrayList<>();
        biomeItemLore.add("");
        biomeItemLore.add("§7Level: §9" + PlotLevelManager.getLevel(plot));

        if (!Double.isNaN(averageRating))
        {
            int new_averageRating = (int) averageRating;
            biomeItemLore.add("§7Bewertung: §e" + new_averageRating + "/10");
        }
        else
        {
            biomeItemLore.add("§7Bewertung: §cNoch keine Bewertung erhalten!");
        }
        ratingsItemMeta.setLore(biomeItemLore);

        ratingsItem.setItemMeta(ratingsItemMeta);
        inventory.setItem(30,ratingsItem);
    }


}
