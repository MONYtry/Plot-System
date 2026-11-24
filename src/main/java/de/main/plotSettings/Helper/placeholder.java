package de.main.plotSettings.Helper;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

import static de.main.plotSettings.Helper.PlotFlagHelper.getMusic;
import static de.main.plotSettings.Helper.messageHelper.getPrefix;

public class placeholder {
    private static ArrayList<String> placeholders = new ArrayList<>();
    static {
        placeholders.add("%get_music_state%");
        placeholders.add("%get_prefix%");
        placeholders.add("%get_settings_prefix%");
        placeholders.add("%get_settings_scoreboard%");
        placeholders.add("%get_settings_tablist%");
    }
    public static String replacePlaceholderChat(String message, Player p)
    {
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        Plot plot = plotPlayer.getCurrentPlot();

        for (String placeholder : placeholders)
        {
            if (message.contains(placeholder))
            {
                message = message.replace(placeholder, getPlaceholder(placeholder,p,plot));
            }
        }
        return message;
    }
    public static void replacePlaceholder(ArrayList<String> list, Player p)
    {
        List<String> stringsToCheck = placeholders;
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());
        Plot plot = plotPlayer.getCurrentPlot();
        for (int i = 0; i < list.size(); i++)
        {
            String line = list.get(i);
            for (String placeholder : stringsToCheck)
            {
                if (line.contains(placeholder))
                {
                    line = line.replace(placeholder, getPlaceholder(placeholder,p, plot));
                }

            }
            list.set(i,line);
        }
    }

    private static String getPlaceholder(String toUse, Player p, Plot plot)
    {
        boolean isMusic = false;

        switch (toUse)
        {
            case "%get_music_state%":
                if (plot != null)
                    isMusic = getMusic(plot);
                return isMusic ? "§eAktiviert" : "§cDeaktviert!";

            case "%get_prefix%":
                return getPrefix();

            default:
                return "Error! Check Placeholder";

        }
    }
}
