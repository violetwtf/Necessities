package wtf.violet.necessities.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import wtf.violet.necessities.Necessities;

public class NecessitiesCommand extends NessBaseCommand
{

    public NecessitiesCommand(final Necessities ness)
    {
        super(
            ness,
            "View information about Necessities",
            "",
            null,
            "necessities",
            "ness"
        );
    }

    @Override
    public void run(Necessities plugin, String alias, CommandSender sender, String[] args)
    {
        success(
            sender,
            "Necessities " +
                ChatColor.WHITE + "v" + plugin.getDescription().getVersion() +
                ChatColor.GREEN + " by " +
                ChatColor.WHITE + "violetwtf - https://violet.wtf" +
                ChatColor.GREEN + ".\n" +
                "https://github.com/violetwtf/Necessities"
        );
    }

}
