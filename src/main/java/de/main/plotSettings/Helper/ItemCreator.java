package de.main.plotSettings.Helper;


import de.main.plotSettings.PlotSettings;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.List;


public class ItemCreator {

    public void autoGenItemsWithConfigGUI(Inventory inventoryToUse,String categoryName)
    {
        // selectionGUI
        FileConfiguration cfg = PlotSettings.getInstance().gui;

        // Holt sich die Section
        ConfigurationSection items = cfg.getConfigurationSection(categoryName);

        // Geht durch jede Kategorie
        for (String key : items.getKeys(false)) {

            // Dynamischer Path
            String path = categoryName + "." + key;

            // Material Name
            String materialName = cfg.getString(path + ".material");

            // Slot index erstellen
            int slot = cfg.getInt(path + ".slot");

            // Item DisplayName
            String name = cfg.getString(path + ".name");

            // Item Lore
            List<String> lore = cfg.getStringList(path + ".lore");

            if (lore == null)
                lore = new ArrayList<>();

            // Ignorieren
            Material material;
            try {
                // Versucht das material zu switchen
                material = Material.valueOf(materialName);
            } catch (Exception e) {
                // Falls Error
                material = Material.STONE;
            }


            // Basic Stuff
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(lore);

            String action = cfg.getString(path + ".action");

            if (meta == null) {
                System.err.println("Es wurde keine Item-Meta gefunden!");
                continue;
            }

            if (action != null)
            {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(PlotSettings.getInstance(), "action"),
                        PersistentDataType.STRING,
                        action
                );
            }

            item.setItemMeta(meta);

            inventoryToUse.setItem(slot, item);
        }
    }
}
