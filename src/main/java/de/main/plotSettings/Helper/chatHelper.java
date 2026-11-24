package de.main.plotSettings.Helper;

import org.bukkit.entity.Player;

import static de.main.plotSettings.Helper.messageHelper.getPrefix;

public class chatHelper {

    public static void sendPlayerMessage(Player p, String message)
    {
        p.sendMessage(getPrefix() + message);
    }
}
