package de.main.plotSettings.GUI;

import de.main.plotSettings.Manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SettingsGUI {

    public static void createMainGUI(Player p)
    {
        Inventory plotRandGUI = Bukkit.createInventory(null,45,"§7» §ePloteinstellungen");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(plotRandGUI,"settingsGUI");

        p.openInventory(plotRandGUI);
    }
}
