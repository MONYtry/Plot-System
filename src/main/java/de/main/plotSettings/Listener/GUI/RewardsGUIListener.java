package de.main.plotSettings.Listener.GUI;

import de.main.plotSettings.PlotSettings;
import de.main.plotSettings.Rewards.rewardGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class RewardsGUIListener implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§aBelohnungen")) return;

        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (item == null || !item.hasItemMeta()) return;
        ItemMeta itemMeta = item.getItemMeta();

        NamespacedKey key = new NamespacedKey(PlotSettings.getInstance(), "action");
        String action = itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (action == null) return;

        Player p = (Player) e.getWhoClicked();

        if (action.startsWith("claim_reward_"))
        {
            String[] data = action.split("_");
            // claim_reward_1
            if (data.length < 3) return;

            claimReward(p, data);
        }
    }

    private void claimReward(Player p, String[] data)
    {
        // Leere Vorlage
        int rewardLevel;
        try
        {
            // Versucht einen Int zu holen
            rewardLevel = Integer.parseInt(data[2]);
        }
        catch (NumberFormatException ex)
        {
            p.sendMessage("§cUngültiges Reward-Level!");
            return;
        }
        // Alle Datein laden
        FileConfiguration cfgRewards = PlotSettings.getInstance().rewards;
        FileConfiguration cfgPlayerData = PlotSettings.getInstance().playerData;

        // Section die durchsucht wird
        ConfigurationSection rewards = cfgRewards.getConfigurationSection("rewardGUI");

        // Nullpointer Vermeiden
        if (rewards == null)
        {
            p.sendMessage("§cEs wurden keine Rewards gefunden!");
            return;
        }

        String rewardPath = null;

        for (String reward : rewards.getKeys(false))
        {
            // Erstellt einen Path
            String path = "rewardGUI." + reward;

            // Holt sich das aktuelle Level
            int level = cfgRewards.getInt(path + ".level");

            if (level == rewardLevel) {
                rewardPath = path;
                break;
            }
        }

        if (rewardPath == null)
        {
            p.sendMessage("§cEs wurde kein Reward für Level " + rewardLevel + " gefunden!");
            return;
        }

        // Erstellt Spieler-Path
        String playerPath = "players." + p.getUniqueId();

        // Holt sich das Level vom Spieler
        int playerLevel = cfgPlayerData.getInt(playerPath + ".plotlevel.level", 1);

        // Wenn das Level zu niedrig ist
        if (playerLevel < rewardLevel)
        {
            p.sendMessage("§cDu benötigst mindestens Level " + rewardLevel + "!");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }


        String rewardDataPath = playerPath + ".rewards." + rewardLevel;
        boolean alreadyClaimed = cfgPlayerData.getBoolean(rewardDataPath, false);

        // Vermeidet ungewolltest einsammeln
        if (alreadyClaimed)
        {
            p.sendMessage("§cDu hast diese Belohnung bereits eingesammelt!");
            return;
        }

        // Holt sich den Materialnamen
        String materialName = cfgRewards.getString(rewardPath + ".material");
        if (materialName == null)
        {
            p.sendMessage("§cDer Reward besitzt kein Material!");
            return;
        }

        // Leeres Material
        Material material;

        // Versucht das Material anhand eines Strings zu erstellen
        try
        {
            material = Material.valueOf(materialName.toUpperCase());
        }
        catch (IllegalArgumentException ex)
        {
            p.sendMessage("§cUngültiges Material: " + materialName);
            return;
        }

        // Anzahl der Items
        int amount = cfgRewards.getInt(rewardPath + ".amount", 1);

        // Erstellung des Itemstacks
        ItemStack rewardItem = new ItemStack(material, amount);

        // Speichern
        cfgPlayerData.set(rewardDataPath, true);
        PlotSettings.getInstance().savePlayerData();

        executeCommand(p,"%player%",cfgRewards,rewardPath);

        giveReward(rewardItem,amount,cfgRewards,rewardPath,p);
    }

    private void giveReward(ItemStack rewardItem, int amount,FileConfiguration cfgRewards, String rewardPath,Player p)
    {
        // Dynamische Item vergabe via. Boolean
        boolean giveItem = cfgRewards.getBoolean(rewardPath + ".giveItem");
        if (giveItem)
        {
            // Legt Item in das Inventar
            p.getInventory().addItem(rewardItem);
        }

        p.sendMessage("§aDu hast deine Belohnung erfolgreich eingesammelt!");
        p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1f, 1f);

        // Aktualisiert GUI
        rewardGUI.createRewardGUI(p);
    }

    private void executeCommand(Player p, String raw,FileConfiguration cfgRewards,String rewardPath)
    {
        // Holt sich den Command von Rewards.yml
        String command = cfgRewards.getString(rewardPath + ".command");

        if (command != null && !command.isEmpty())
        {
            // Aktuell nur ein Command
            // Daher Switch-Case nicht wirklich sinnvoll, jedoch sehr gut skalierbar!
            switch (raw)
            {
                case ("%player%"):
                    // Erstetzt den Placeholder mit dem Spieler-Namen
                    command = command.replace(raw, p.getName());
                    break;
            }

            // Führt den Command als Console aus
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
        }
    }
}