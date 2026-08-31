package de.main.plotSettings.GUI.Plot;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.Rating;
import de.main.plotSettings.Level.PlotLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eclipse.aether.transfer.RepositoryOfflineException;


import java.util.*;

import static de.main.plotSettings.Manager.PlotInfoHelper.*;

public class PlotInfoGUI {

    public static void createInfoGUI(Player p)
    {
        Inventory infoGUI = Bukkit.createInventory(null,36,"§eInfo");

        createRatingsBlock(infoGUI,p,32);
        createInfoBlock(infoGUI,p,30);
        createPlotMemberBlock(infoGUI,p,0);
        createPlotTrustedBlock(infoGUI,p,9);
        createDenyBlock(infoGUI,p,18);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Weitere §eFeatures §7sind in §ePlanung!");
        lore.add("§7Falls du eine §eIdee §7hast, lass es uns §eWissen!");
        lore.add("§7Vielen Dank für euer §eVerständnis §c<3");
        createUpdateItem(infoGUI,13,Material.COMMAND_BLOCK_MINECART,lore);

        p.openInventory(infoGUI);
    }

    public static void createUpdateItem(Inventory inventory , int Slot, Material material, ArrayList<String> lore)
    {
        ItemStack updateItem = new ItemStack(material);
        ItemMeta updateItemMeta = updateItem.getItemMeta();

        // IDEE
        // Jedes mal Random Farbe Nehmen!
        // 31.08.2026 17:09 Uhr
        // IDEE

        updateItemMeta.setDisplayName("§d§ke§r§e§ke§r §cUpdate-Info §a§ke§r§9§ke§r");
        updateItemMeta.setLore(lore);

        updateItem.setItemMeta(updateItemMeta);
        inventory.setItem(Slot,updateItem);
    }
    private static void createPlotMemberBlock(Inventory inventory, Player p,int Slot)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
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

    private static void createDenyBlock(Inventory inventory, Player p,int Slot)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
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

    private static void createPlotTrustedBlock(Inventory inventory, Player p,int Slot)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
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


    private static void createInfoBlock(Inventory inventory, Player p,int Slot)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
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

    private static void createRatingsBlock(Inventory inventory,Player p,int Slot)
    {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
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

        // Rating
        double averageRating = plot.getAverageRating();
        HashMap<UUID, Rating> allRatings = plot.getRatings();



        // Lore erstellen
        ArrayList biomeItemLore = new ArrayList<>();
        biomeItemLore.add("");

        UUID plotOwner_uuid = plot.getOwner();

        if (plotOwner_uuid != null)
        {
            int level = PlotLevelManager.getLevel(plotOwner_uuid);

            biomeItemLore.add("§7Level: §9"+ level);

        }
        else
        {
            biomeItemLore.add("§7Level: §cUnbekannt");
        }


        if (!Double.isNaN(averageRating))
        {
            int new_averageRating = (int) averageRating;

            biomeItemLore.add("§7Bewertung: §e" + new_averageRating + "/10");
            biomeItemLore.add("§7Bewertungen: §e" + allRatings.size());
        }
        else
        {
            biomeItemLore.add("§7Bewertung: §cNoch keine Bewertung erhalten!");
        }
        ratingsItemMeta.setLore(biomeItemLore);

        ratingsItem.setItemMeta(ratingsItemMeta);
        inventory.setItem(Slot,ratingsItem);
    }
}
