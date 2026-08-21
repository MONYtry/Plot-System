package de.main.plotSettings.GUI;

import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MainGUI {

    public static void createMainGUI(Player p)
    {
        Inventory plotRandGUI = Bukkit.createInventory(null,36,"&7» §ePloteinstellungen");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(plotRandGUI,"mainGUI");

        p.openInventory(plotRandGUI);
    }
}
