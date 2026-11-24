package de.main.plotSettings.Helper;

import de.main.plotSettings.PlotSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

public class messageHelper {

    public static FileConfiguration getMessages()
    {
        return PlotSettings.getInstance().messages;
    }

    public static String getPrefix()
    {
        return PlotSettings.getInstance().messages.getString("messages.prefix","Blockhive");
    }
}
