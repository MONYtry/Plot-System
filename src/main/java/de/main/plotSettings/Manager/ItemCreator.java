package de.main.jobsystem.manager;


import com.plotsquared.core.plot.Plot;
import de.main.jobsystem.Jobsystem;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCreator {

    public void autoGenItemsWithConfigGUI(Inventory inventoryToUse,String categoryName)
    {
        // selectionGUI
        FileConfiguration cfg = PlotSettings.getInstance().gui;

        // Schau Config macht Sinn :)
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

            if(lore == null) lore = new ArrayList<>();

            // Ignorieren
            Material material;
            try {
                // Versucht das material zu switchen
                material = Material.valueOf(materialName);
            } catch (Exception e) {
                // Falls Error
                material = Material.STONE;
                System.out.print("error Critical!");
            }


            // Basic Stuff
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(lore);

            String action = cfg.getString(path + ".action");
            if (action != null)
            {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(PlotSettings.getInstance(), "action"),
                        PersistentDataType.STRING,
                        action
                );
            }
            // Für Server Detection
            String server = cfg.getString(path + ".server");

            if (server != null) {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(PlotSettings.getInstance(), "server"),
                        PersistentDataType.STRING,
                        server
                );
            }

            if (meta == null) {
                System.out.print("error: ItemMeta is null!");
                continue;
            }

            item.setItemMeta(meta);

            inventoryToUse.setItem(slot, item);
        }
    }
}
