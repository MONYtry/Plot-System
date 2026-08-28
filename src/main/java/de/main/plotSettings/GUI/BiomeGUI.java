package de.main.plotSettings.GUI;

import com.sk89q.worldedit.world.biome.BiomeType;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BiomeGUI {

    // LinkedHashMap garantiert reinfolge
    public static final Map<BiomeType,Double> biomeListe = new LinkedHashMap<>();
            static
            {
                biomeListe.put(BiomeTypes.BADLANDS,500.0);
                biomeListe.put(BiomeTypes.BASALT_DELTAS,500.0);
                biomeListe.put(BiomeTypes.PLAINS,500.0);
                biomeListe.put(BiomeTypes.JUNGLE,500.0);
                biomeListe.put(BiomeTypes.SAVANNA,500.0);
                biomeListe.put(BiomeTypes.SNOWY_TAIGA,500.0);
            };

    // Verfügbare Blöcke
    private static final Material[] PREVIEW_BLOCKS = new Material[]
            {
                    Material.BLACKSTONE,
                    Material.BASALT,
                    Material.GRASS_BLOCK,
                    Material.JUNGLE_LOG,
                    Material.ACACIA_LOG,
                    Material.SPRUCE_LOG
            };

    public static void createBiomeGUI(Player p) {
        Inventory biomeInventory = Bukkit.createInventory(null, 36, "§eBiome");
        int currentSlot = 0;

        for (Map.Entry<BiomeType,Double> entry : biomeListe.entrySet())
        {
            BiomeType biome = entry.getKey();
            String biomeName =  entry.getKey().toString().toLowerCase();
            double preis = entry.getValue();

            String permission = "plotsettings.biome." + biomeName;
            String status = p.hasPermission(permission) ? "§aIn Besitz" : "§cNicht in Besitz";

            // Item erstellen
            ItemStack biomeItem = new ItemStack(PREVIEW_BLOCKS[currentSlot]);
            ItemMeta biomeItemMeta = biomeItem.getItemMeta();

            // Erstellt die Action
            NamespacedKey key = new NamespacedKey(PlotSettings.getInstance(),"action");
            biomeItemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"set_biome_" + biomeName);

            biomeItemMeta.setDisplayName("§e" + biomeName);

            // Lore erstellen
            ArrayList biomeItemLore = new ArrayList<>();
            biomeItemLore.add("");
            biomeItemLore.add("§7Biome: §b" + biomeName);
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
            biomeInventory.setItem(currentSlot, biomeItem);

            // Slot hochziehen
            currentSlot++;
        }
        p.openInventory(biomeInventory);
    }
}
