package de.main.plotSettings.GUI.Plot;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.Rating;
import de.main.plotSettings.Level.PlotLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.*;

import static de.main.plotSettings.Manager.GUIHelper.*;
import static de.main.plotSettings.Manager.GUIHelper.createUpdateItem;
import static de.main.plotSettings.Manager.PlotDataHelper.*;
import static de.main.plotSettings.Manager.PlotGUIHelper.*;
import static de.main.plotSettings.Manager.PlotGUIHelper.createCapBlock;
import static de.main.plotSettings.Manager.PlotGUIHelper.createDenyBlock;
import static de.main.plotSettings.Manager.PlotGUIHelper.createPlotMemberBlock;
import static de.main.plotSettings.Manager.PlotGUIHelper.createTrustedBlock;

public class PlotInfoGUI {

    public static void createInfoGUI(Player p)
    {
        Inventory infoGUI = Bukkit.createInventory(null,36,"§eInfo");

        Plot plot = getPlot(p);

        createRatingsBlock(infoGUI,p,32,plot);
        createInfoBlock(infoGUI,p,30,plot);
        createPlotMemberBlock(infoGUI,p,0,plot);
        createTrustedBlock(infoGUI,p,9,plot);
        createDenyBlock(infoGUI,p,18,plot);
        createCapBlock(infoGUI,p,15,plot);


        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Weitere §eFeatures §7sind in §ePlanung!");
        lore.add("§7Falls du eine §eIdee §7hast, lass es uns §eWissen!");
        lore.add("§7Vielen Dank für euer §eVerständnis §c<3");
        createUpdateItem(infoGUI,13,Material.COMMAND_BLOCK_MINECART,lore);

        p.openInventory(infoGUI);
    }



}
