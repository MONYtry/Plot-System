package de.main.plotSettings.Listener.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.WeatherFlag;
import de.main.plotSettings.GUI.WeatherGUI;
import de.main.plotSettings.PlotSettings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import static de.main.plotSettings.Helper.GUIHelper.checkQuitButton;
import static de.main.plotSettings.Helper.GUIHelper.getPlayerFromClick;
import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;
import static de.main.plotSettings.Helper.placeholder.replacePlaceholderChat;

public class WeatherGUIListener implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.weather","§9Wetter");
        title = replacePlaceholderChat(title,(Player) e.getWhoClicked());

        Player p = getPlayerFromClick(e,title);
        if (p == null)
            return;

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        if (plotPlayer == null) return;

        Plot plot = plotPlayer.getCurrentPlot();
        if (plot == null) return;

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Verhindert Fremdzugriff
        if (!plot.isOwner(plotPlayer.getUUID()))
        {
            sendPlayerMessage(p,"§cNur der Besitzter des Grundstücks darf dies tun!");
            return;
        }

        // Nullpointer verhindern
        if (item == null || !item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING);

        checkQuitButton(item,p);

        if (action.startsWith("set_weather_"))
        {
            String weather = action.substring("set_weather_".length());

            Double price = WeatherGUI.weather.get(weather);

            // Keine Musik/kein Preis gefunden
            if (price == null)
            {
                p.sendMessage("§cFür dieses Wetter wurde kein Preis festgelegt!");
                return;
            }

            setWeather(plot, weather, p,price);
        }
    }

    private void setWeather(Plot plot,String weather, Player p,double price)
    {
        Economy economy = PlotSettings.getEconomy();
        if (economy == null)
        {
            sendPlayerMessage(p,"Leider ist das Geld-System aktuell deaktiviert!");
            return;
        }

        double currentCash = PlotSettings.getEconomy().getBalance(p);
        String permission = "plotsettings.weather." + weather;


        if (!p.hasPermission(permission))
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

        String weatherValue = weather.equalsIgnoreCase("sun") ? "clear" : "rain";
        plot.setFlag(WeatherFlag.class, weatherValue);

        sendPlayerMessage(p,"§aWetter wurde erfolgreich geändert!");
        p.closeInventory();
    }
}
