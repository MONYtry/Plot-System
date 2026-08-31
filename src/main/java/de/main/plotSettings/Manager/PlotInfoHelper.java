package de.main.plotSettings.Manager;

import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class PlotInfoHelper {

    public static String getPlotOwner(Player p, Plot plot)
    {
        // Spieler ist nicht auf einem Grundstück!
        if (plot == null)
        {
            p.sendMessage("§7Kein Grundstück gefunden!");
            return "§cUnbekannt!";
        }
        UUID plot_owner_uuid = plot.getOwner();
        Player player = Bukkit.getPlayer(plot_owner_uuid);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(plot_owner_uuid);

        if (player != null)
        {
            return player.getName();
        }
        else
        {
            return  offlinePlayer.getName();
        }

    }

    public static String getPlotDate(Plot plot)
    {
        long time = plot.getTimestamp();
        if (time <= 0)
        {
            return "Unbekannt!";
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(new Date(time));
    }

    public static ArrayList<String> getPlotMembers(Plot plot, Player p)
    {
        HashSet<UUID> raw_members = plot.getMembers();
        ArrayList<String> memberLore = new ArrayList<>();

        int count = 0;
        for (UUID member : raw_members)
        {
            Player player = Bukkit.getPlayer(member);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(member);
            String playerName = "Unbekannt!";

            if (player == null)
            {
                playerName = offlinePlayer.getName();
            }
            else
            {
                playerName = player.getName();
            }

            count++;
            memberLore.add("§7[#" + count + "] §e" + playerName);
        }

        int members = raw_members.size();
        memberLore.add("§7Insgesamt: §c" + members);

        return memberLore;
    }

    public static ArrayList<String> getDeniedPlayer(Plot plot)
    {
        HashSet<UUID> raw_denied = plot.getDenied();

        ArrayList<String> deniedLore = new ArrayList<>();

        int count = 0;
        for (UUID deniedPlayer : raw_denied)
        {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(deniedPlayer);
            Player player = Bukkit.getPlayer(deniedPlayer);
            String playerName = "Unbekannt";

            if (player == null)
            {
                playerName = offlinePlayer.getName();
            }
            else
            {
                playerName = player.getName();
            }

            count++;
            deniedLore.add("§7[#" + count + "] §e" + playerName);
        }


        int trustedPlayers = raw_denied.size();

        deniedLore.add("§7Insgesamt: §c" + trustedPlayers);

        return deniedLore;
    }


    public static ArrayList<String> getPlotTrusted(Plot plot)
    {
        HashSet<UUID> raw_trusted = plot.getTrusted();

        ArrayList<String> trustedLore = new ArrayList<>();


        int count = 0;
        for (UUID trusted : raw_trusted)
        {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(trusted);
            Player player = Bukkit.getPlayer(trusted);
            String playerName = "Unbekannt";

            if (player == null)
            {
                playerName = offlinePlayer.getName();
            }
            else
            {
                playerName = player.getName();
            }

            count++;
            trustedLore.add("§7[#" + count + "] §e" + playerName);
        }


        int trustedPlayers = raw_trusted.size();
        trustedLore.add("§7Insgesamt: §c" + trustedPlayers);

        return trustedLore;
    }
}
