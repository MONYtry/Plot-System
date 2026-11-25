package de.main.plotSettings.GUI;

import de.main.plotSettings.Helper.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.main.plotSettings.Helper.placeholder.replacePlaceholderChat;

public class SettingsGUI {

    public static void createMainGUI(Player p)
    {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.settings","§7» §ePloteinstellungen");
        title = replacePlaceholderChat(title,p);

        Inventory plotRandGUI = Bukkit.createInventory(null,45,title);

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(plotRandGUI,"settingsGUI");

        p.openInventory(plotRandGUI);
    }
}
