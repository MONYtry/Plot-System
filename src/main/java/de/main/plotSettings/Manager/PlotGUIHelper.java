package de.main.plotSettings.Manager;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.Rating;
import de.main.plotSettings.Level.PlotLevelManager;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static de.main.plotSettings.Manager.PlotDataHelper.*;
import static de.main.plotSettings.Manager.PlotDataHelper.getHopperCap;
import static de.main.plotSettings.Manager.PlotFlagHelper.getEntityCap;

public class PlotGUIHelper {

    // * WICHTIG *
    //  In dieser Klasse werden nur Plot-GUI orientierte
    //  funktionen erstellt!
    // * WICHTIG *
    public static void createCapBlock(Inventory inventory, Player p,int slot, Plot plot)
    {
        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        // Item erstellen
        displayMaterial = Material.ENCHANTING_TABLE;

        ItemStack capInfoItem = new ItemStack(displayMaterial);
        ItemMeta capInfoIteMeta = capInfoItem.getItemMeta();

        capInfoIteMeta.setDisplayName("§ePlot-Limits");

        String hopperText = getHopperCap(plot);
        int entityLimit = getEntityCap(plot);
        // Lore erstellen
        ArrayList<String> capInfoLore = new ArrayList<>();
        capInfoLore.add("§7Hopper-Limit: §b" + hopperText);
        capInfoLore.add("§7Entity-Limit: §b" + entityLimit);

        capInfoIteMeta.setLore(capInfoLore);

        capInfoItem.setItemMeta(capInfoIteMeta);
        inventory.setItem(slot,capInfoItem);
    }

    public static void createPlotMemberBlock(Inventory inventory, Player p,int Slot,Plot plot)
    {
        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        // Item erstellen
        displayMaterial = Material.YELLOW_STAINED_GLASS_PANE;

        ItemStack memberItem = new ItemStack(displayMaterial);
        ItemMeta memberItemMeta = memberItem.getItemMeta();

        memberItemMeta.setDisplayName("§eMitglieder: ");

        Set<Plot> mergedPlots = plot.getConnectedPlots();
        mergedPlots.size();

        // Lore erstellen
        ArrayList<String> memberLore = getPlotMembers(plot,p);
        memberItemMeta.setLore(memberLore);

        memberItem.setItemMeta(memberItemMeta);
        inventory.setItem(Slot,memberItem);
    }

    public static void createDenyBlock(Inventory inventory, Player p,int Slot,Plot plot)
    {
        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        // Item erstellen
        displayMaterial = Material.RED_STAINED_GLASS_PANE;

        ItemStack deniedItem = new ItemStack(displayMaterial);
        ItemMeta deniedItemMeta = deniedItem.getItemMeta();

        deniedItemMeta.setDisplayName("§cGebannt:");


        // Lore erstellen
        ArrayList<String> deniedLore = getDeniedPlayer(plot);
        deniedItemMeta.setLore(deniedLore);

        deniedItem.setItemMeta(deniedItemMeta);
        inventory.setItem(Slot,deniedItem);
    }

    public static void createTrustedBlock(Inventory inventory, Player p,int Slot,Plot plot)
    {
        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        // Item erstellen
        displayMaterial = Material.GREEN_STAINED_GLASS_PANE;

        ItemStack memberItem = new ItemStack(displayMaterial);
        ItemMeta memberItemMeta = memberItem.getItemMeta();

        memberItemMeta.setDisplayName("§aVertraut:");


        // Lore erstellen
        ArrayList<String> trustedLore = getPlotTrusted(plot);
        memberItemMeta.setLore(trustedLore);

        memberItem.setItemMeta(memberItemMeta);
        inventory.setItem(Slot,memberItem);
    }

    public static void createRatingsBlock(Inventory inventory, Player p, int Slot, Plot plot)
    {
        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        displayMaterial = Material.REDSTONE_TORCH;

        ItemStack ratingsItem = new ItemStack(displayMaterial);
        ItemMeta ratingsItemMeta = ratingsItem.getItemMeta();

        ratingsItemMeta.setDisplayName("§6Plot-Bewertungen");



        // Lore erstellen
        ArrayList ratingsItemLore = new ArrayList<>();
        ratingsItemLore.add("");

        UUID plotOwner_uuid = plot.getOwner();
        if (plotOwner_uuid != null)
        {
            int level = PlotLevelManager.getLevel(plotOwner_uuid);
            ratingsItemLore.add("§7Level: §9"+ level);
        }
        else
        {
            ratingsItemLore.add("§7Level: §cUnbekannt");
        }

        // Rating
        double averageRating = plot.getAverageRating();
        HashMap<UUID, Rating> allRatings = plot.getRatings();

        // Wenn averageRating exisitiert
        if (!Double.isNaN(averageRating))
        {
            int new_averageRating = (int) averageRating;

            ratingsItemLore.add("§7Bewertung: §e" + new_averageRating + "/10");
            ratingsItemLore.add("§7Bewertungen: §e" + allRatings.size());
        }
        else
        {
            ratingsItemLore.add("§7Bewertung: §cNoch keine Bewertung erhalten!");
        }

        ratingsItemMeta.setLore(ratingsItemLore);

        ratingsItem.setItemMeta(ratingsItemMeta);
        inventory.setItem(Slot,ratingsItem);
    }


    public static void createInfoBlock(Inventory inventory, Player p, int Slot,Plot plot)
    {

        Material displayMaterial = null;

        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            displayMaterial = Material.BARRIER;
        }

        // Item erstellen
        displayMaterial = Material.BOOK;

        ItemStack infoItem = new ItemStack(displayMaterial);
        ItemMeta infoItemMeta = infoItem.getItemMeta();

        infoItemMeta.setDisplayName("§6Plot-Infomationen");

        Set<Plot> mergedPlots = plot.getConnectedPlots();
        mergedPlots.size();

        // Lore erstellen
        ArrayList infoItemLore = new ArrayList<>();
        infoItemLore.add("");

        infoItemLore.add("§7Besitzer: §e" + getPlotOwner(p,plot));
        infoItemLore.add("§7Erstellt am: §e" + getPlotDate(plot));
        infoItemLore.add("§7Größe: §e" + mergedPlots.size());
        infoItemMeta.setLore(infoItemLore);

        infoItem.setItemMeta(infoItemMeta);
        inventory.setItem(Slot,infoItem);
    }


    public static void createPlotsListGUI(Player p, Inventory inventory, int startSlot, int endSlot)
    {
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        if (plotPlayer == null) return;

        List<Plot> plots = new ArrayList<>(plotPlayer.getPlots());

        // Größte Merges zuerst
        plots.sort(Comparator.comparingInt((Plot plot) -> plot.getConnectedPlots().size()).reversed());

        // Plots, die übersprungen werden sollen
        List<Plot> blacklist = new ArrayList<>();

        int slot = endSlot;
        int anzahl = startSlot;

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
}
