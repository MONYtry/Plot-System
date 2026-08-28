package de.main.plotSettings.GUI;

import com.sk89q.worldedit.world.biome.BiomeType;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeGUI {

    public static final Map<String,Integer> timeListe = new HashMap<>();
        static
        {
            timeListe.put("Mittags",200);
            timeListe.put("Nachts",200);
            timeListe.put("Frühs",200);
            timeListe.put("remove",200);
        }

    public static void createTimeGUI(Player p) {
        Inventory timeInventory = Bukkit.createInventory(null, 36, "§eUhrzeit");
        int currentSlot = 0;

        for (Map.Entry<String,Integer> entry : timeListe.entrySet())
        {
            String uhrzeit = entry.getKey();
            String uhrzeit_Name =  entry.getKey().toString();
            double preis = entry.getValue();

            String permission = "plotsettings.biome." + uhrzeit_Name;
            String status = p.hasPermission(permission) ? "§aIn Besitz" : "§cNicht in Besitz";

            // Item erstellen
            ItemStack biomeItem = new ItemStack(Material.DIRT);
            ItemMeta biomeItemMeta = biomeItem.getItemMeta();

            // Erstellt die Action
            NamespacedKey key = new NamespacedKey(PlotSettings.getInstance(),"action");
            biomeItemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"set_time_" + uhrzeit_Name);

            biomeItemMeta.setDisplayName("§e" + uhrzeit_Name);

            // Lore erstellen
            ArrayList biomeItemLore = new ArrayList<>();
            biomeItemLore.add("");
            biomeItemLore.add("§7Biome: ".toUpperCase() + "§b" + uhrzeit_Name);
            biomeItemLore.add("");
            biomeItemLore.add("§9§l-INFO-");
            biomeItemLore.add("");
            biomeItemLore.add("§7Klicke, um dieses §eBiome §7zu §esetzen!");
            biomeItemLore.add("§7Status: " + status);
            biomeItemLore.add("§7Preis: §e" + preis + "$");
            biomeItemLore.add("");
            biomeItemLore.add("§9§l-INFO-");

            // Lore setzen
            biomeItemMeta.setLore(biomeItemLore);

            biomeItem.setItemMeta(biomeItemMeta);

            // Item in GUI legen
            timeInventory.setItem(currentSlot, biomeItem);

            // Slot hochziehen
            currentSlot++;
        }
        p.openInventory(timeInventory);
    }


}
