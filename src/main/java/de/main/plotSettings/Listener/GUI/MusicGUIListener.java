package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.MusicFlag;
import de.main.plotSettings.GUI.MainGUI;
import de.main.plotSettings.GUI.MusicGUI;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class MusicGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!e.getView().getTitle().equalsIgnoreCase("§cMusic")) return;

        if (e.getView().getTitle() == null) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(player.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Verhindert Fremdzugriff
        if (!plot.isOwner(plotPlayer.getUUID()))
        {
            player.sendMessage("§cNur der Besitzter des Grundstücks darf dies tun!");
            return;
        }

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();
        Player p = ((Player) e.getWhoClicked());

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);

        if (action.equals("open.main"))
        {
            MainGUI.createMainGUI(p);
            p.playSound(p.getLocation(), Sound.BLOCK_BARREL_CLOSE,1,1);
        }

        if (action.startsWith("set_music_"))
        {
            String music = action.substring("set_music_".length());

            Double price = null;

            // Preis für die Musik suchen
            for (Map.Entry<ItemType, Double> entry : MusicGUI.musicDiscs.entrySet())
            {
                if (entry.getKey().getKey().getKey().equalsIgnoreCase(music))
                {
                    price = entry.getValue();
                    break;
                }
            }

            // Keine Musik/kein Preis gefunden
            if (price == null)
            {
                player.sendMessage("§cFür diese Musik wurde kein Preis festgelegt!");
                return;
            }

            setMusic(plot, music, player,price);
        }
    }

    private void setMusic(Plot plot,String music, Player p,double price)
    {

        String permission = "plotsettings.music." + music;
        double currentCash = PlotSettings.getEconomy().getBalance(p);

        if (!p.hasPermission(permission))
        {
            if (currentCash < price)
            {
                p.sendMessage("§cDu hast nicht genug Guthaben! §7(Preis: " + price +"$)");
                return;
            }

            PlotSettings.getEconomy().withdrawPlayer(p,price);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + p.getName() + " permission set " + permission + " true");
            p.sendMessage("§7Du hast §c-" + price + "$ §7bezahlt!");

        }
        if (music.equalsIgnoreCase("BARRIER"))
        {
            plot.removeFlag(MusicFlag.MUSIC_FLAG_NONE);
            p.sendMessage("§eMusik wurde §cdeaktivert");
            return;
        }
        plot.setFlag(MusicFlag.class,music);
        p.sendMessage("§aMusik wurde erfolgreich geändert!");
        p.closeInventory();
    }
}
