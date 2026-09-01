package de.main.plotSettings.Manager;

import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GUIHelper {


    public static void createQuitButton(Inventory inventory, Material material, int slot, Player p)
    {
        if (inventory.getItem(slot) != null)
        {
            String occupiedMessagePrefix = "§7Slot §e[" + slot + "]";
            p.sendMessage(occupiedMessagePrefix + "ist bereits besetzt!");
            p.sendMessage(occupiedMessagePrefix + "§7besetzt von §c" + inventory.getItem(slot));
        }
        else
        {
            ItemStack quitButton = new ItemStack(material);
            ItemMeta quitButtonMeta = quitButton.getItemMeta();
            String action = "onClick_quit";

            if(action != null)
            {
                quitButtonMeta.getPersistentDataContainer().set(
                        new NamespacedKey(PlotSettings.getInstance(), "action"),
                        PersistentDataType.STRING,
                        action
                );
            }
            inventory.setItem(slot,quitButton);
        }
    }

    public static void createPlaceholderItems(Inventory inventory, Material material, int startSlot, int endSlot,Player p)
    {
        for (int i = startSlot; i < endSlot; i++)
        {
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

}
