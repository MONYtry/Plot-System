package de.main.plotSettings.GUI;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.Level.PlotLevelManager;
import de.main.plotSettings.Manager.ItemCreator;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static de.main.plotSettings.Manager.PlotGUIHelper.createPlotsListGUI;
import static de.main.plotSettings.Manager.PlotGUIHelper.createRatingsBlock;


public class MainGUI {

    public static void createMainGUI(Player p)
    {
        Inventory mainGUI = Bukkit.createInventory(null,36,"Hautpmenü");

        ItemCreator itemCreator = new ItemCreator();
        // Erstellt Items aus der Liste
        itemCreator.autoGenItemsWithConfigGUI(mainGUI,"mainGUI");

        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        Plot plot = plotPlayer.getCurrentPlot();

        // Erstellt Ratings-Item
        createRatingsBlock(mainGUI,p,31,plot);

        // Erstellt Plots-Liste
        createPlotsListGUI(p,mainGUI,0,11);
    }
}
