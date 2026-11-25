package de.main.plotSettings.GUI;

import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.main.plotSettings.Helper.GUIHelper.setQuitButton;
import static de.main.plotSettings.Helper.permissionHelper.hasPermission;

public class WeatherGUI {

    public static final Map<String,Double> weather = new HashMap<>();
    static {
        weather.put("rain",500.0);
        weather.put("sun",500.0);
    };

    public static void createWeatherGUI(Player p)
    {
        FileConfiguration file = PlotSettings.getInstance().getConfig();
        String title = file.getString("guiName.weather","§9Wetter");

        Inventory weatherGUI = Bukkit.createInventory(null,45,title);

        // Quit Button!
        setQuitButton(weatherGUI, Material.BARRIER,35,"open.main");
        createWeatherItems(weatherGUI,p);
        p.openInventory(weatherGUI);
    }

    private static void createWeatherItems(Inventory weatherGUI, Player p)
    {
        for (int i = 0; i < 2; i++)
        {
            String varWeather = "Null";
            if (i == 0) varWeather = "sun";
            else varWeather = "rain";

            double price = weather.get(varWeather);
            String permission = "plotsettings.weather." + varWeather;

            boolean isSun = varWeather.contains("sun");

            // Item wird erstellt
            ItemStack weatherItem = new ItemStack((isSun) ? Material.DEAD_BUSH : Material.ICE);

            // Item-Meta wird erstellt
            ItemMeta weatherItemMeta = weatherItem.getItemMeta();

            // Item-Meta Anzeigename
            weatherItemMeta.setDisplayName((varWeather.contains("sun")) ? "§eSonne" : "§9Regen");

            // Lore des Items
            List<String> weatherItemLore = new ArrayList<>();

            weatherItemLore.add("");
            String ownedDisplay = hasPermission(p, permission) ? "§aIn besitz" : "§cNicht in besitz!";

            weatherItemLore.add(ownedDisplay);
            weatherItemLore.add("§7Preis: §e" + price + "$");

            weatherItemMeta.setLore(weatherItemLore);

            weatherItemMeta.getPersistentDataContainer().set(new NamespacedKey(PlotSettings.getInstance(), "action"), PersistentDataType.STRING, "set_weather_" + varWeather);

            weatherItem.setItemMeta(weatherItemMeta);
            weatherGUI.setItem(i,weatherItem);
        }
    }
}
