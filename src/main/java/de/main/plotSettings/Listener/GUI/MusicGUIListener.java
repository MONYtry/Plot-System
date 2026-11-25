package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.MusicFlag;
import de.main.plotSettings.GUI.MusicGUI;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.Map;
import static de.main.plotSettings.Helper.GUIHelper.checkQuitButton;
import static de.main.plotSettings.Helper.GUIHelper.getPlayerFromClick;
import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;
import static de.main.plotSettings.Helper.permissionHelper.hasPermission;
import static de.main.plotSettings.Helper.placeholder.replacePlaceholderChat;

public class MusicGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player p = getPlayerFromClick(e,"§cMusic");
        if (p == null)
            return;

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null)
            return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null)
            return;

        // Verhindert Fremdzugriff
        if (!plot.isOwner(plotPlayer.getUUID()))
        {
            p.sendMessage("§cNur der Besitzter des Grundstücks darf dies tun!");
            return;
        }

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();
        if (item == null ||!item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);
        if (!action.startsWith("set_music_"))
            return;

        if (checkQuitButton(item,p))
            return;

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
            p.sendMessage("§cFür diese Musik wurde kein Preis festgelegt!");
            return;
        }

        setMusic(plot, music, p,price);
    }

    private void setMusic(Plot plot,String music, Player p,double price)
    {

        String permission = "plotsettings.music." + music;
        double currentCash = PlotSettings.getEconomy().getBalance(p);

        if (!hasPermission(p,permission))
        {
            if (currentCash < price)
            {
                sendPlayerMessage(p,"§cDu hast nicht genug Guthaben! §7(Preis: " + price +"$)");
                return;
            }

            PlotSettings.getEconomy().withdrawPlayer(p,price);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user " + p.getName() + " permission set " + permission + " true");
            sendPlayerMessage(p,"§7Du hast §c-" + price + "$ §7bezahlt!");
        }

        if (music.equalsIgnoreCase("BARRIER"))
        {
            plot.removeFlag(MusicFlag.class);
            sendPlayerMessage(p,replacePlaceholderChat("§aDie Music wurde %get_music_state%",p));
            return;
        }

        plot.setFlag(MusicFlag.class,music);
        sendPlayerMessage(p,replacePlaceholderChat("§aDie Music wurde %get_music_state%",p));

        p.closeInventory();
    }
}
