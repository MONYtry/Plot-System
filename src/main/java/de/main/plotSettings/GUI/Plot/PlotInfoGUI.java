package de.main.plotSettings.GUI.Plot;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.Level.PlotLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlotInfoGUI {

    public static void createInfoGUI(Player p)
    {
        Inventory infoGUI = Bukkit.createInventory(null,36,"§eInfo");

        createRatings(infoGUI,p);
        createInfo(infoGUI,p);
        createPlotMemberBlock(infoGUI,p);
        createPlotTrustedBlock(infoGUI,p);

        p.openInventory(infoGUI);
    }

    private static String getPlotOwner(Player p,Plot plot)
    {
        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            return "§cUnbekannt!";
        }
        UUID plot_owner_uuid = plot.getOwner();
        Player plot_owner = Bukkit.getPlayer(plot_owner_uuid);
        return plot_owner.getName();
    }

    private static String getPlotDate(Player p, Plot plot)
    {
        long time = plot.getTimestamp();
        if (time <= 0)
        {
            return "Unbekannt!";
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(new Date(time));
    }
    private static ArrayList<String> getPlotMembers(Plot plot,Player p)
    {
        HashSet<UUID> raw_members = plot.getMembers();
        ArrayList<String> memberLore = new ArrayList<>();
        memberLore.add("Mitglieder: ");

        int count = 0;
        for (UUID test : raw_members)
        {
            OfflinePlayer player = Bukkit.getOfflinePlayer(test);
            memberLore.add("§7[#" + count + "] §e" + player.getName());
            count++;
        }

        int members = raw_members.size();
        memberLore.add("§7Insgesamt: §c" + members);

        return memberLore;
    }

    private static ArrayList<String> getPlotTrusted(Plot plot,Player p)
    {
        HashSet<UUID> raw_trusted = plot.getTrusted();

        ArrayList<String> trustedLore = new ArrayList<>();
        trustedLore.add("Vertraut: ");

        int count = 0;
        for (UUID trusted : raw_trusted)
        {
            OfflinePlayer player = Bukkit.getOfflinePlayer(trusted);
            trustedLore.add("§7[#" + count + "] §e" + player.getName());
            count++;
        }


        int members = trustedLore.size();
        trustedLore.add("§7Insgesamt: §c" + members);

        return trustedLore;
    }
    private static void createPlotMemberBlock(Inventory inventory, Player p)
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
        displayMaterial = Material.GREEN_DYE;

        ItemStack memberItem = new ItemStack(displayMaterial);
        ItemMeta memberItemMeta = memberItem.getItemMeta();

        memberItemMeta.setDisplayName("§6Plot-Infomationen");

        Set<Plot> mergedPlots = plot.getConnectedPlots();
        mergedPlots.size();

        // Lore erstellen
        ArrayList<String> memberLore = getPlotMembers(plot,p);
        memberItemMeta.setLore(memberLore);

        memberItem.setItemMeta(memberItemMeta);
        inventory.setItem(20,memberItem);
    }

    private static void createPlotTrustedBlock(Inventory inventory, Player p)
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
        displayMaterial = Material.RED_CONCRETE;

        ItemStack memberItem = new ItemStack(displayMaterial);
        ItemMeta memberItemMeta = memberItem.getItemMeta();

        memberItemMeta.setDisplayName("§6Plot-Infomationen");


        // Lore erstellen
        ArrayList<String> trustedLore = getPlotTrusted(plot,p);
        memberItemMeta.setLore(trustedLore);

        memberItem.setItemMeta(memberItemMeta);
        inventory.setItem(23,memberItem);
    }
    private static void createInfo(Inventory inventory, Player p)
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
        infoItemLore.add("§7Erstellt am: §e" + getPlotDate(p,plot));
        infoItemLore.add("§7Größe: §e" + mergedPlots.size());
        infoItemMeta.setLore(infoItemLore);

        infoItem.setItemMeta(infoItemMeta);
        inventory.setItem(29,infoItem);
    }

    private static void createRatings(Inventory inventory,Player p)
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
