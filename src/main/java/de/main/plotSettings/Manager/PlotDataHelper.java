package de.main.plotSettings.Manager;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import de.main.plotSettings.PlotSettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class PlotDataHelper {

    // * WICHTIG *
    // Dies ist eine Helfer-Klasse
    // Hier baue ich NUR get-funktionen
    // Sehr sinnvoll, wird noch erweitert!
    // Datum: 01.09.2026 14:32
    // * WICHTIG *

    public static String getPlayerPowerstate(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        String playerName = "Unbekannt!";

        if (player == null) {
            playerName = offlinePlayer.getName();
        } else {
            playerName = player.getName();
        }

        if (playerName == null || playerName.equalsIgnoreCase("Unbekannt!")) {
            return "Unbekannter Fehler!";
        }

        return playerName;
    }

    public static Plot getPlot(Player p) {
        // Holt sich die UUID des Spieler der auf dem Plot steht
        PlotPlayer<?> plotPlayer = PlotSquared.platform().playerManager().getPlayer(p.getUniqueId());

        // Wenn es keine UUID gibt abbruch
        if (plotPlayer == null) return null;

        // Aktuelles Plot holen
        Plot plot = plotPlayer.getCurrentPlot();
        return plot;
    }

    public static String getPlotOwner(Player p, Plot plot) {
        // Spieler ist nicht auf einem Grundstück!
        if (plot == null) {
            p.sendMessage("§7Kein Grundstück gefunden!");
            return "§cUnbekannt!";
        }
        // Variablen erstellen
        UUID plot_owner_uuid = plot.getOwner();
        return getPlayerPowerstate(plot_owner_uuid);
    }

    public static String getPlotDate(Plot plot) {
        // Erstellt einen long
        long time = plot.getTimestamp();

        // Vermeidet fehlerhafte Zeit
        if (time <= 0) {
            return "Unbekannt!";
        }

        // Erstellt Datum + Format
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(new Date(time));
    }

    public static ArrayList<String> getPlotMembers(Plot plot, Player p) {
        // Erstellt ein HashSet
        // Inhalt Grundstück-Mitglieder
        HashSet<UUID> raw_members = plot.getMembers();
        ArrayList<String> memberLore = new ArrayList<>();

        int count = 0;
        for (UUID member : raw_members) {
            String playerName = getPlayerPowerstate(member);

            count++;
            memberLore.add("§7[#" + count + "] §e" + playerName);
        }

        int members = raw_members.size();
        memberLore.add("§7Insgesamt: §c" + members);

        return memberLore;
    }

    public static ArrayList<String> getDeniedPlayer(Plot plot) {
        HashSet<UUID> raw_denied = plot.getDenied();
        ArrayList<String> deniedLore = new ArrayList<>();

        int count = 0;
        for (UUID deniedPlayer : raw_denied) {
            String playerName = getPlayerPowerstate(deniedPlayer);

            count++;
            deniedLore.add("§7[#" + count + "] §e" + playerName);
        }


        int trustedPlayers = raw_denied.size();

        deniedLore.add("§7Insgesamt: §c" + trustedPlayers);

        return deniedLore;
    }


    public static ArrayList<String> getPlotTrusted(Plot plot) {
        HashSet<UUID> raw_trusted = plot.getTrusted();
        ArrayList<String> trustedLore = new ArrayList<>();

        int count = 0;
        for (UUID trusted : raw_trusted) {
            String playerName = getPlayerPowerstate(trusted);

            count++;
            trustedLore.add("§7[#" + count + "] §e" + playerName);
        }


        int trustedPlayers = raw_trusted.size();
        trustedLore.add("§7Insgesamt: §c" + trustedPlayers);

        return trustedLore;
    }

    public static String getHopperCap(Plot plot)
    {
        FileConfiguration plot_hopper_config = PlotSettings.getInstance().plot;

        String path = "plots." + plot.getId();

        int hopper_limit = plot_hopper_config.getInt(path + ".settings.hopper-limit", 20);
        int hoppers_placed = plot_hopper_config.getInt(path + ".settings.hopper-placed");

        String hopperText = hoppers_placed + "/" + hopper_limit;

        return hopperText;
    }
}
