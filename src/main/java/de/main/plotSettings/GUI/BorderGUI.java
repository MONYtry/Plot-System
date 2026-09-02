package de.main.plotSettings.GUI;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.util.PatternUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static de.main.plotSettings.Manager.GUIHelper.setQuitButton;

public class BorderGUI {

    // Verfügbare Blöcke
    private static final Material[] BORDER_BLOCKS = new Material[]
            {
                Material.DIAMOND_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.EMERALD_BLOCK,
                Material.NETHERITE_BLOCK, Material.OBSIDIAN, Material.PRISMARINE_BRICKS, Material.QUARTZ_BLOCK,
                Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK, Material.LIGHT_BLUE_CONCRETE, Material.BEACON,
                Material.OAK_LOG, Material.EMERALD_ORE, Material.PRISMARINE, Material.AMETHYST_BLOCK
            };



    private static String formatName(Material mat)
    {
        // Alles kleinschreiben + _ entfernen!
        String name = mat.name().toLowerCase().replace("_","");

        // Holt sich alle Parts und entfernt Leertasten
        String[] parts = name.split(" ");

        // Neuen Stringbuilder erstellen
        StringBuilder formatted = new StringBuilder();

        // String verarbeiten
        for (String part : parts)
            formatted.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");

        return formatted.toString().trim();
    }


    public static void createBorderGUI(Player p)
    {
        Inventory borderGUI = Bukkit.createInventory(null,45,"Ränder");
        int currentSlot = 0;

        // Quit Button!
        setQuitButton(borderGUI,Material.BARRIER,35,"open.main");

        // Nimmt sich alle Blocke
        for (Material mat : BORDER_BLOCKS)
        {
            // Falls der Spieler rechte hat
            String perm = "plotsettings.border." + mat.name().toLowerCase();
            if (p.hasPermission(perm))
            {
                // Item erstellen
                ItemStack borderItem = new ItemStack(mat);
                ItemMeta borderItemMeta = borderItem.getItemMeta();
                borderItemMeta.setDisplayName("§e" + formatName(mat));

                // Lore erstellen
                ArrayList borderItemLore = new ArrayList<>();
                borderItemLore.add("");
                borderItemLore.add("§7blocktyp: ".toUpperCase() + "§b" + formatName(mat));
                borderItemLore.add("");
                borderItemLore.add("§9§l-INFO-");
                borderItemLore.add("");
                borderItemLore.add("§7Klicke, um diesen §eBlock §7als Rand zu §esetzen!");
                borderItemLore.add("");
                borderItemLore.add("§9§l-INFO-");

                // Lore setzen
                borderItemMeta.setLore(borderItemLore);

                borderItem.setItemMeta(borderItemMeta);

                // Item in GUI legen
                borderGUI.setItem(currentSlot,borderItem);

                // Slot hochziehen
                currentSlot++;
            }
        }
        p.openInventory(borderGUI);
    }
}
