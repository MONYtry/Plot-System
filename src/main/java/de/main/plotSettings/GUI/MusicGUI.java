package de.main.plotSettings.GUI;

import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicGUI {

    public static final Map<ItemType,Double> musicDiscs = new HashMap<>();
            static{
                    musicDiscs.put(ItemType.MUSIC_DISC_LAVA_CHICKEN,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_PIGSTEP,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_CAT,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_TEARS,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_CREATOR,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_11,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_5,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_STRAD,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_FAR,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_BLOCKS,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_WAIT,500.0);
                    musicDiscs.put(ItemType.MUSIC_DISC_PRECIPICE,500.0);
                    musicDiscs.put(ItemType.BARRIER,0.0);
            };

    public static void createMusicGUI(Player p)
    {
        Inventory inventory = Bukkit.createInventory(null,36,"§cMusic");
        int currentSlot = 0;

        for (Map.Entry<ItemType, Double> entry : musicDiscs.entrySet())
        {
            ItemType music = entry.getKey();
            double price = entry.getValue();

            String musicName = music.getKey().getKey();

            ItemStack borderItem = new ItemStack(music.createItemStack());
            ItemMeta borderItemMeta = borderItem.getItemMeta();

            borderItemMeta.setDisplayName("§eMusik");

            NamespacedKey key = new NamespacedKey(PlotSettings.getInstance(), "action");
            borderItemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "set_music_" + musicName);

            String permission = "plotsettings.music." + musicName;
            String status = p.hasPermission(permission) ? "§aIn Besitz" : "§cNicht in Besitz";

            ArrayList<String> lore = new ArrayList<>();

            lore.add("");
            lore.add("§9§l-INFO-");
            lore.add("");
            lore.add("§7Status: " + status);
            lore.add("§7Preis: §e" + price + "$");
            lore.add("");
            lore.add("§9§l-INFO-");

            borderItemMeta.setLore(lore);
            borderItem.setItemMeta(borderItemMeta);

            inventory.setItem(currentSlot, borderItem);
            currentSlot++;
        }
        p.openInventory(inventory);
    }
}
