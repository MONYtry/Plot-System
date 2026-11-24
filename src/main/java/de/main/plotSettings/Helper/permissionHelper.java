package de.main.plotSettings.Helper;

import de.main.plotSettings.PlotSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static de.main.plotSettings.Helper.messageHelper.sendPlayerMessage;


public class permissionHelper {

    public static boolean hasPermission(Player p, String permission)
    {
        if (!p.hasPermission(permission))
        {
            FileConfiguration file = PlotSettings.getYML(PlotSettings.getInstance().messages);
            sendPlayerMessage(p,file.getString("messages.noPermission"));
            return false;
        }
        return true;
    }
}
