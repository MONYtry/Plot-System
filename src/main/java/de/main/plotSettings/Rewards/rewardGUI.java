package de.main.plotSettings.Rewards;


import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class rewardGUI {

    public static void createRewardGUI(Player p) {

        FileConfiguration cfg = PlotSettings.getInstance().rewards;
        FileConfiguration cfgPlayerData = PlotSettings.getInstance().playerData;

        Inventory inventory = Bukkit.createInventory(null, 36, "§aBelohnungen");

        ConfigurationSection rewards = cfg.getConfigurationSection("rewardGUI");

        // Falls keine Rewards gefunden wurden!
        if (rewards == null) {
            Bukkit.getLogger().warning("Keine Rewards für gefunden!");
            return;
        }

        // Aktuelles Level des Spielers
        String playerLevelPath = "players." + p.getUniqueId() + ".plotlevel.level";

        int playerLevel = cfgPlayerData.getInt(playerLevelPath, 1);

        ItemCreator itemCreator = new ItemCreator();


        for (String key : rewards.getKeys(false)) {

            String path = "rewardGUI." + key;

            int rewardLevel = cfg.getInt(path + ".level");

            String rewardDataPath = "players." + p.getUniqueId()  + ".rewards." + rewardLevel;
            boolean alreadyClaimed = cfgPlayerData.getBoolean(rewardDataPath, false);

            int slot = cfg.getInt(path + ".slot");

            String materialName = cfg.getString(path + ".material", "STONE");

            String name = cfg.getString(path + ".name", "§fReward");

            List<String> lore = new ArrayList<>(cfg.getStringList(path + ".lore"));

            String action = cfg.getString(path + ".action");

            if (alreadyClaimed) {
                lore.add("");
                lore.add("§c✔ Bereits eingesammelt!");

                action = "null";
            }
            // Wenn Spieler Level erreicht oder übertrifft und es noch nicht eingesammelt hat
            if (playerLevel >= rewardLevel && !alreadyClaimed) {

                lore.add("");
                lore.add("§eBereit zum einsammeln");

            }
            if (playerLevel < rewardLevel && !alreadyClaimed)
            {

                lore.add("");
                lore.add("§c🔒 Benötigt Level §e" + rewardLevel);

                lore.add("§7Dein Level: §e" + playerLevel);

                // Action entfernen
                action = "null";
            }

            Material material;

            try {
                material = Material.valueOf(materialName.toUpperCase());

            } catch (Exception e) {

                material = Material.STONE;

                Bukkit.getLogger().warning("Ungültiges Material: " + materialName);
            }

            itemCreator.createItemWith(
                    material,
                    name,
                    lore,
                    slot,
                    p,
                    inventory,
                    false,
                    action
            );
        }

        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);

        p.openInventory(inventory);
    }
}
