package de.main.plotSettings.Achviment;

import de.main.plotSettings.Level.PlotLevelManager;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AchivmentManager {

    private static final Map<String, Achivment> achivments = new HashMap<>();
    public static Map<String,Achivment> getAchivments()
    {
        return achivments;
    }

    public static void registerAchivments() {
        achivments.put("stone_breaker",
                new Achivment(
                        "stone_breaker",
                        "Steinzerstörer",
                Material.STONE,

                Map.of(
                        1,10,
                        2, 50,
                        3, 60,
                        4, 100
                ),
                Map.of(
                        1, "Anfänger",
                        2, "Fortgeschrittener",
                        3, "Profi",
                        4, "Meister"
                )
            )
        );


        achivments.put("grass_breaker",
                new Achivment(
                        "grass_breaker",
                        "Rasenmäher",
                        Material.GRASS_BLOCK,

                        Map.of(
                                1,10,
                                2, 50,
                                3, 60,
                                4, 100
                        ),
                        Map.of(
                                1, "Anfänger",
                                2, "Fortgeschrittener",
                                3, "Profi",
                                4, "Meister"
                        )
                )
        );

        achivments.put("grass_breaker",
                new Achivment(
                        "grass_breaker",
                        "Rasenmäher",
                        Material.GRASS_BLOCK,

                        Map.of(
                                1,10,
                                2, 50,
                                3, 60,
                                4, 100
                        ),
                        Map.of(
                                1, "Anfänger",
                                2, "Fortgeschrittener",
                                3, "Profi",
                                4, "Meister"
                        )
                )
        );

        achivments.put("beacon_placer",
                new Achivment(
                        "beacon_placer",
                        "§f§k?? §r§c??? §f§k??",
                        Material.BEACON,

                        Map.of(
                                1,1,
                                2, 5,
                                3, 100,
                                4, 999
                        ),
                        Map.of(
                                1, "Anfänger",
                                2, "Fortgeschrittener",
                                3, "Profi",
                                4, "Meister"
                        )
                )
        );
    }
    public static void checkBuildingAchivment(Material material, Player p) {

        FileConfiguration playerData = PlotSettings.getInstance().playerData;

        for (Achivment achivment : achivments.values()) {

            if (achivment.getMaterial() != material) {
                continue;
            }
            // Holt sich die Path`s
            String playerPath = "players." + p.getUniqueId();
            String basePath = playerPath + ".achievements." + achivment.getId();
            // Holt sich die Daten basierend auf dem Path
            int brokenBlocks = playerData.getInt(playerPath + ".blocks.destoryed." + material.name());
            int currentLevel = playerData.getInt(basePath + ".level");
            int newLevel = currentLevel;

            for (Map.Entry<Integer, Integer> entry : achivment.getLevels().entrySet()) {

                int level = entry.getKey();
                int requiredBlocks = entry.getValue();

                // Wenn die RequierdBlocks erfüllt sind && das alte Level kleiner ist als das neue
                if (brokenBlocks >= requiredBlocks && level > newLevel) {
                    newLevel = level;
                }
            }

            // Wenn das neue Level größer ist als das aktuelle
            if (newLevel > currentLevel) {

                playerData.set(basePath + ".level", newLevel);
                unlockAchievement(p,achivment,newLevel);

                PlotSettings.getInstance().savePlayerData();
            }
        }
    }

    private static void unlockAchievement(Player p,Achivment achivment,int newLevel)
    {
        String levelName = achivment.getLevelNames().get(newLevel);

        p.sendMessage("§6§lERFOLG FREIGESCHALTET!");
        p.sendMessage("§e" + achivment.getTitle() + " §7- §f" + levelName);
        p.sendMessage("§7Stufe: §e" + newLevel);
        p.sendTitle("§6§lERFOLG FREIGESCHALTET!","§f" + achivment.getTitle() +" "+ newLevel);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1,1);
        PlotLevelManager.addLevel(p);
    }
}