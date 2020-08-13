package wtf.violet.necessities.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParseUtil
{

    private ParseUtil() {} // Do not construct

    /**
     * Get player targets from command arguments.
     * @param sender Command sender
     * @param args Arguments
     * @return The player targets
     */
    public static List<Player> getTargetsFromArgs(
        final CommandSender sender,
        final String[] args
    )
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Invalid target! None provided.");
            return null;
        }

        final Server server = sender.getServer();

        if (args[0].equals("*"))
        {
            return new ArrayList<>(server.getOnlinePlayers());
        }

        final List<Player> targets = new ArrayList<>();

        for (final String name : args)
        {
            final Player player = server.getPlayer(name);

            if (player == null)
            {
                sender.sendMessage(ChatColor.RED + "Invalid target: " + name);
                return null;
            }

            targets.add(player);
        }

        return targets;
    }

    /** Returns a list of targets for tab complete */
    public static List<String> getTargetTabComplete(final boolean addWildcard)
    {
        final List<String> players = Bukkit.getServer()
            .getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .collect(Collectors.toList());

        if (addWildcard)
        {
            players.add("*");
        }

        players.sort(Comparator.naturalOrder());
        return players;
    }

    /** Returns a list of targets for tab complete depending on the contents of args */
    public static List<String> getTargetTabComplete(final String[] args)
    {
        if (args[0].equals("*"))
        {
            return Collections.emptyList();
        }

        return getTargetTabComplete(args.length > 1);
    }

}
