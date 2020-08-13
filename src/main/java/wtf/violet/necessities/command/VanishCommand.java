package wtf.violet.necessities.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.violet.necessities.Necessities;
import wtf.violet.necessities.manager.VanishManager;
import wtf.violet.necessities.util.ParseUtil;

import java.util.Collections;
import java.util.List;

public class VanishCommand extends NessBaseCommand
{

    public VanishCommand(final Necessities ness)
    {
        super(ness, "Hide players", "[targets]", "ness.vanish", "vanish", "v");
    }

    @Override
    public void run(Necessities plugin, String alias, CommandSender sender, String[] args)
    {
        List<Player> targets;

        if (args.length == 0)
        {
            final Player target = requirePlayer(sender);
            targets = Collections.singletonList(target);
            if (target == null) return;
        } else
        {
            targets = ParseUtil.getTargetsFromArgs(sender, args);
            if (targets == null) return;
        }

        final VanishManager manager = plugin.getVanishManager();

        for (final Player target : targets)
        {
            final boolean vanished = manager.toggleVanish(target);

            final String text = getVanishedText(vanished);

            if (target == sender)
            {
                success(sender, "Set your vanish: " + ChatColor.WHITE + text);
            } else
            {
                success(target, sender.getName() + " set your vanish: " + ChatColor.WHITE + text);
            }
        }

        final Player zero = targets.get(0);

        if (targets.size() == 1)
        {
            if (zero != sender)
            {
                success(
                    sender,
                    "Set " +
                        ChatColor.WHITE + zero.getName() +
                        ChatColor.GREEN + "'s vanish: " +
                        ChatColor.WHITE + getVanishedText(manager.isVanished(targets.get(0)))
                );
            }
        } else
        {
            success(sender, "Toggled the vanish of " +
                ChatColor.WHITE + targets.size() +
                ChatColor.GREEN + " players.");
        }
    }

    private static String getVanishedText(final boolean vanished)
    {
        return vanished ? "On" : "Off";
    }

    @Override
    public List<String> tab(Necessities plugin, CommandSender sender, String alias, String[] args)
    {
        return ParseUtil.getTargetTabComplete(args);
    }

}
