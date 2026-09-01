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

public class GUIHelper {

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

    public static void createPlaceholderItems(Inventory inventory, Material material, int startSlot, int endSlot,Player p)
    {
        // Geht durch jeden Slot
        for (int i = startSlot; i < endSlot; i++)
        {
            // Falls der Slot frei ist
            if (inventory.getItem(i) == null)
            {
                ItemStack displayItem = new ItemStack(material);
                inventory.setItem(i,displayItem);
            }
            else
            {
                String occupiedMessagePrefix = "§7Slot §e[" + startSlot + "]";
                p.sendMessage(occupiedMessagePrefix + "ist bereits besetzt!");
                p.sendMessage(occupiedMessagePrefix + "§7besetzt von §c" + inventory.getItem(startSlot));
            }
        }
    }

    public static void createDenyBlock(Inventory inventory, Player p,int Slot)
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

    public static void createTrustedBlock(Inventory inventory, Player p,int Slot)
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

}
