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

        int currentSlot = 0;

        // Geht durch jedes Achivment
        for (Achivment achivment : AchivmentManager.getAchivments().values())
        {
            // Erstellt die Paths
            String playerPath = "players." + p.getUniqueId();
            String basePath = playerPath + ".achievements." + achivment.getId();

            // Holt sich das Material vom Achivment
            Material material = achivment.getMaterial();

            // Holt sich Daten
            int currentLevel = playerData.getInt(basePath + ".level",0);
            int brokenBlocks = playerData.getInt(playerPath + ".blocks.destoryed." + material.name());
            int nextLevel = currentLevel + 1;

            // Erstellt das Item mit abgekürzten If-Statment
            ItemStack achivmentItem = new ItemStack(currentLevel > 0 ? material : Material.GRAY_DYE);
            ItemMeta achivementItemMeta = achivmentItem.getItemMeta();

            achivementItemMeta.setDisplayName("§e" + achivment.getTitle());

            List<String> achivmentLore = new ArrayList<>();
            achivmentLore.add("");


            if (nextLevel <= achivment.getLevels().size())
            {
                // Holt sich die gebrauchten Blöcke
                int requiredBlocks = achivment.getLevels().get(nextLevel);

                // Erstellt Progressbar
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
            // Falls Spieler das maximale Level erreicht hat
            else
            {
                String currentName = achivment.getLevelNames().get(currentLevel);
                achivmentLore.add("§7Level: §e" + currentLevel + " §8(" + currentName + "§8)");
                achivmentLore.add("§a§l✔ MAXIMALES LEVEL");
            }

            // Erstellte Elemente festlegen
            achivementItemMeta.setLore(achivmentLore);
            achivmentItem.setItemMeta(achivementItemMeta);
            inventory.setItem(currentSlot,achivmentItem);

            currentSlot++;
        }

        p.openInventory(inventory);
    }


    private static String createProgressBar(int current, int required) {

        // Anzahl der Bars
        int bars = 10;

        // Aktuelles Level geteilt durch benötigt, wird zu einem double
        double progress = (double) current / required;

        // Maximal 100%
        progress = Math.min(progress, 1.0);

        // progress * bars wird abgerundet
        int filled = (int) Math.round(progress * bars);

        // Eine Art String-Array
        // StringBuilder wird in dem Fall genutzt um den String zu erweitern
        StringBuilder bar = new StringBuilder();
        bar.append("§7[");

        // Geht durch jede Bar
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
