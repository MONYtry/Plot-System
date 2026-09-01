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
}
