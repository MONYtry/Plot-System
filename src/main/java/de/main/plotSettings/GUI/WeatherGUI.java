package de.main.plotSettings.GUI;

import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.main.plotSettings.Manager.GUIHelper.setQuitButton;

public class WeatherGUI {

    public static final Map<String,Double> weather = new HashMap<>();
    static{
        weather.put("rain",500.0);
        weather.put("clear",500.0);
    };

    public static void createWeatherGUI(Player p)
    {
        Inventory weatherGUI = Bukkit.createInventory(null,45,"§9Wetter");

        // Quit Button!
        setQuitButton(weatherGUI, Material.BARRIER,35,"open.main");

        createSunItem(weatherGUI,p);
        createRainItem(weatherGUI,p);

        p.openInventory(weatherGUI);
    }

    private static void createSunItem(Inventory weatherGUI,Player p)
    {
        double price = weather.get("clear");
        String permission = "plotsettings.weather.sun";
        String status = "§cNicht in Besitz";

        ItemStack sunItem = new ItemStack(Material.DEAD_BUSH);
        ItemMeta sunItemMeta = sunItem.getItemMeta();
        sunItemMeta.setDisplayName("§eSonne");

        List<String> sunItemLore = new ArrayList<>();
        sunItemLore.add("");

        if (p.hasPermission(permission))
        {
            status = "§aIn Besitz";
        }
        sunItemLore.add(status);
        sunItemLore.add("§7Preis: §e" + price + "$");

        sunItemMeta.setLore(sunItemLore);

        sunItemMeta.getPersistentDataContainer().set(
                new NamespacedKey(PlotSettings.getInstance(), "action"),
                PersistentDataType.STRING,
                "set_weather_clear"
        );

        sunItem.setItemMeta(sunItemMeta);
        weatherGUI.setItem(0,sunItem);


    }

    private static void createRainItem(Inventory weatherGUI,Player p)
    {
        double price = weather.get("rain");
        String permission = "plotsettings.weather.rain";
        String status = "§cNicht in Besitz";

        ItemStack sunItem = new ItemStack(Material.WATER_BUCKET);
        ItemMeta sunItemMeta = sunItem.getItemMeta();
        sunItemMeta.setDisplayName("§9Regen");

        List<String> sunItemLore = new ArrayList<>();
        sunItemLore.add("");

        if (p.hasPermission(permission))
        {
            status = "§aIn Besitz";
        }
        sunItemLore.add(status);
        sunItemLore.add("§7Preis: §e" + price + "$");

        sunItemMeta.setLore(sunItemLore);


        sunItemMeta.getPersistentDataContainer().set(
                new NamespacedKey(PlotSettings.getInstance(), "action"),
                PersistentDataType.STRING,
                "set_weather_rain"
        );

        sunItem.setItemMeta(sunItemMeta);
        weatherGUI.setItem(1,sunItem);
    }
}
