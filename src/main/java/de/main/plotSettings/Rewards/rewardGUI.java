package de.main.plotSettings.Rewards;


import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.main.plotSettings.Manager.GUIHelper.createPlaceholderItems;
import static de.main.plotSettings.Manager.RewardHelper.addEnchantments;

public class rewardGUI {

    public static void createRewardGUI(Player p)
    {
        // Inventory
        Inventory inventory = Bukkit.createInventory(null, 36, "§aBelohnungen");

        // Läd Datein
        FileConfiguration cfg = PlotSettings.getInstance().rewards;
        FileConfiguration cfgPlayerData = PlotSettings.getInstance().playerData;

        // Baut Placeholder Items
        // Selbst gebauter Helper
        createPlaceholderItems(inventory,Material.RED_STAINED_GLASS_PANE,27,36,p);
        createPlaceholderItems(inventory,Material.GRAY_STAINED_GLASS_PANE,0,27,p);

        // Section welche durchsucht wird
        ConfigurationSection rewards = cfg.getConfigurationSection("rewardGUI");

        // Falls keine Rewards gefunden wurden!
        if (rewards == null)
        {
            Bukkit.getLogger().warning("Keine Rewards für gefunden!");
            return;
        }

        // Path für das aktuelle Level des Spielers
        String playerLevelPath = "players." + p.getUniqueId() + ".plotlevel.level";

        // Aktuelles Level des Spielers
        int playerLevel = cfgPlayerData.getInt(playerLevelPath, 1);

        ItemCreator itemCreator = new ItemCreator();

        // Items erstellen
        for (String key : rewards.getKeys(false)) {
            // Haupt-kategorie
            String path = "rewardGUI." + key;

            // Benötigtes Level
            int rewardLevel = cfg.getInt(path + ".level");


            // Holt sich das Level vom Spieler
            String rewardDataPath = "players." + p.getUniqueId()  + ".rewards." + rewardLevel;

            // Verhindert ungewolltes einsammeln
            boolean alreadyClaimed = cfgPlayerData.getBoolean(rewardDataPath, false);

            // Holt sich den gewünschten Slot
            int slot = cfg.getInt(path + ".slot");

            // Holt sich den Material-Namen
            String materialName = cfg.getString(path + ".material", "STONE");

            // Item-Name
            String name = cfg.getString(path + ".name", "§fReward");

            // Nimmt sich die Lore
            List<String> lore = new ArrayList<>(cfg.getStringList(path + ".lore_display"));

            // Holt sich die Lore
            String action = cfg.getString(path + ".action");

            // Falls es bereits eingesammelt ist
            if (alreadyClaimed) {
                lore.add("");
                lore.add("§c✔ Bereits eingesammelt!");
                // Action deaktivieren!
                action = "null";
            }

            // Wenn Spieler Level erreicht oder übertrifft und es noch nicht eingesammelt hat
            if (playerLevel >= rewardLevel && !alreadyClaimed)
            {
                lore.add("");
                lore.add("§eBereit zum einsammeln");
            }

            // Wenn Spieler Level nicht erreicht hat und es noch nicht eingesammlt hat
            if (playerLevel < rewardLevel && !alreadyClaimed)
            {
                lore.add("");
                lore.add("§c🔒 Benötigt Level §e" + rewardLevel);

                lore.add("§7Dein Level: §e" + playerLevel);

                // Action entfernen
                action = "null";
            }

            // Leeres Material
            Material material;

            // Versucht dem Material ein Wert zu geben
            // durch den oben geholten material-namen
            try
            {
                material = Material.valueOf(materialName.toUpperCase());
            }
            catch (Exception e)
            {
                // Placeholder Material
                // Sinnvoll um verbuggte Items oder Fehler zu erstellen
                material = Material.STONE;
                Bukkit.getLogger().warning("Ungültiges Material: " + materialName);
            }

            // Item erstellen
            createItem(
                    material,
                    name,
                    lore,
                    slot,
                    inventory,
                    action,
                    cfg,
                    path
            );
        }
        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);
        p.openInventory(inventory);
    }


    private static void createItem(Material material, String name, List<String> lore, int slot, Inventory inventory, String action,FileConfiguration cfgRewards,String rewardDataPath)
    {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        itemMeta = addEnchantments(cfgRewards,rewardDataPath,itemMeta);


        if (action != null)
        {
            itemMeta.getPersistentDataContainer().set(
                    new NamespacedKey(PlotSettings.getInstance(), "action"),
                    PersistentDataType.STRING,
                    action
            );
        }
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot,itemStack);
    }
}
