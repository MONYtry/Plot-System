package de.main.plotSettings.Manager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardHelper {



    public static ItemMeta addEnchantments(FileConfiguration cfgRewards, String rewardPath, ItemMeta itemMeta)
    {
        // Fügt Enchantments hinzu
        List<String> enchantmentData = cfgRewards.getStringList(rewardPath + ".enchantments");
        Map<Enchantment,Integer> enchantments = new HashMap<>();

        // Geht durch jede Verzauberung
        for (String data : enchantmentData)
        {
            // Teilt die gebenen Daten
            String[] parts = data.split(" ");

            // Falls es unpassend angeben wurde -> abbruch + Fehler werfen
            if (parts.length != 2)
            {
                Bukkit.getLogger().warning("Ungültiges Enchantment: " + data);
                continue;
            }
            // Erste Teil -> Verzauberungs-Name
            String enchantmentName = parts[0];

            try
            {
                // Versucht aus dem zweiten Teil ein Integer zu erstellen
                int level = Integer.parseInt(parts[1]);

                // Erstellt die Verzauberung
                Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());

                // Nullpointer Vermeiden
                if (enchantment == null)
                {
                    Bukkit.getLogger().warning("Ungültiges Enchantment: " + enchantmentName.toUpperCase());
                    continue;
                }
                // Wenn alles passt -> In die Map packen
                enchantments.put(enchantment,level);
            }
            catch (NumberFormatException e)
            {
                Bukkit.getLogger().warning(e.toString());
            }
        }
        // Setzt jede Verzauberung auf die ItemMeta
        for (Map.Entry<Enchantment,Integer> entry : enchantments.entrySet())
        {
            // GetKey() -> Name der Verzauberung
            // GetValue() -> Integer als Stärke der Verzauberung
            itemMeta.addEnchant(entry.getKey(),entry.getValue(),true);
        }
        return itemMeta;
    }
}
