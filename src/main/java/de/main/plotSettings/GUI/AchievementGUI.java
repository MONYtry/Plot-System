package de.main.plotSettings.GUI;

import de.main.plotSettings.Achviment.Achivment;
import de.main.plotSettings.Achviment.AchivmentManager;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AchievementGUI {

    public static void createAchievementGUI(Player p)
    {
        Inventory inventory = Bukkit.createInventory(null,36,"§eAchievements");
        FileConfiguration playerData = PlotSettings.getInstance().playerData;
        String path = "players." + p.getUniqueId() + ".achievements";

        int currentSlot = 0;

        for (Achivment achivment : AchivmentManager.getAchivments().values())
        {

            String playerPath = "players." + p.getUniqueId();
            String basePath = playerPath + ".achievements." + achivment.getId();
            Material material = achivment.getMaterial();

            int currentLevel = playerData.getInt(basePath + ".level",0);
            int brokenBlocks = playerData.getInt(playerPath + ".blocks.destoryed." + material.name());
            int nextLevel = currentLevel + 1;

            ItemStack achivmentItem = new ItemStack(currentLevel > 0 ? material : Material.GRAY_DYE);
            ItemMeta achivementItemMeta = achivmentItem.getItemMeta();

            achivementItemMeta.setDisplayName("§e" + achivment.getTitle());


            List<String> achivmentLore = new ArrayList<>();
            achivmentLore.add("");


            if (nextLevel <= achivment.getLevels().size())
            {
                int requiredBlocks = achivment.getLevels().get(nextLevel);
                String progressbar = createProgressBar(brokenBlocks,requiredBlocks);
                achivmentLore.add("§7Fortschritt: ");
                achivmentLore.add(progressbar + " §e" + brokenBlocks + "/" + requiredBlocks);
                achivmentLore.add("");

                if (currentLevel > 0)
                {
                    String currentName = achivment.getLevelNames().get(currentLevel);
                    String nextName = achivment.getLevelNames().get(nextLevel);

                    achivmentLore.add("§7Level: §e" + currentLevel + " §8(" + currentName + "§8)");
                    achivmentLore.add("§7Nächstes Level: §e" + nextName);
                }
                else
                {
                    achivmentLore.add("§7Level: §cNoch nicht erreicht!");
                }
            }
            else
            {
                String currentName = achivment.getLevelNames().get(currentLevel);
                achivmentLore.add("§7Level: §e" + currentLevel + " §8(" + currentName + "§8)");
                achivmentLore.add("§a§l✔ MAXIMALES LEVEL");
            }

            achivementItemMeta.setLore(achivmentLore);
            achivmentItem.setItemMeta(achivementItemMeta);
            inventory.setItem(currentSlot,achivmentItem);

            currentSlot++;
        }

        p.openInventory(inventory);
    }


    private static String createProgressBar(int current, int required) {

        int bars = 10;

        double progress = (double) current / required;

        // Maximal 100%
        progress = Math.min(progress, 1.0);

        int filled = (int) Math.round(progress * bars);

        StringBuilder bar = new StringBuilder();
        bar.append("§7[");

        for (int i = 0; i < bars; i++) {

            if (i < filled) {
                bar.append("§a❘");
            } else {
                bar.append("§7❘");
            }
        }

        bar.append("§7]");

        return bar.toString();
    }
}
