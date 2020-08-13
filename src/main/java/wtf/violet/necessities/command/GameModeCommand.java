package wtf.violet.necessities.command;

import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.violet.necessities.Necessities;

import java.util.*;
import java.util.stream.Collectors;

/*
Usages:

/gm                             - Toggle between Creative and Survival
/gm <players...>                - Toggle player between Creative and Survival
/gm <identifier>                - Set your game mode to identifier's
/gm <identifier> <players...>   - Set player's game mode to identifier's
/gm<identifier>                 - Set your game mode to identifier's
/gm<identifier> <players...>    - Set player's game mode to creative

player can be substituted with * to represent all players.
Space-separated lists are valid as players.
 */
public class GameModeCommand extends NessBaseCommand
{

    // Has to be static to make registration easier
    private static final Map<String, GameMode> IDENTIFIERS = new HashMap<>();
    private static final Map<String, GameMode> GAME_MODE_ALIASES = new HashMap<>();

    static
    {
        for (final GameMode mode : GameMode.values())
        {
            // Yes, I know this is deprecated, but people insist on having it.
            IDENTIFIERS.put(String.valueOf(mode.getValue()), mode);
        }

        IDENTIFIERS.put("s", GameMode.SURVIVAL);
        IDENTIFIERS.put("c", GameMode.CREATIVE);
        IDENTIFIERS.put("a", GameMode.ADVENTURE);
        IDENTIFIERS.put("sp", GameMode.SPECTATOR);
    }

    public GameModeCommand(final Necessities ness)
    {
        super(
            ness,
            "Set your game mode",
            "(usage depends on the command)",
            "ness.gamemode",
            getCustomAliases("gamemode", "gm")
        );
    }

    // TODO: Permission check
    @Override
    public void run(
        final Necessities plugin,
        final String alias,
        final CommandSender sender,
        final String[] args
    )
    {
        List<Player> targets = new ArrayList<>();
        // If null, it flips the game mode of targets
        GameMode mode = null;
        boolean senderTarget = false;

        if (GAME_MODE_ALIASES.containsKey(alias)) // e.g. /gmc
        {
            mode = GAME_MODE_ALIASES.get(alias);

            if (args.length == 0) // /gmc
            {
                senderTarget = true;
            } else
            {
                targets = getTargetsFromArgs(sender, args);
            }
        } else // e.g /gm
        {
            if (args.length == 0) // gm
            {
                senderTarget = true;
            } else // gm <player/identifier>
            {
                if (IDENTIFIERS.containsKey(args[0])) // e.g. /gm c
                {
                    mode = IDENTIFIERS.get(args[0]);

                    if (args.length == 1) // gm c
                    {
                        senderTarget = true;
                    } else // gm c ...
                    {
                        targets = getTargetsFromArgs(
                            sender,
                            Arrays.copyOfRange(args, 1, args.length)
                        );
                    }
                } else
                {
                    targets = getTargetsFromArgs(sender, args);
                }
            }
        }

        if (senderTarget)
        {
            final Player player = requirePlayer(sender);
            if (player == null) return;
            targets.add(player);
        }

        if (targets == null) return;

        final int players = targets.size();

        for (final Player player : targets)
        {
            final GameMode playerMode;

            if (mode == null)
            {
                playerMode = player.getGameMode() == GameMode.CREATIVE
                    ? GameMode.SURVIVAL
                    : GameMode.CREATIVE;
            } else
            {
                playerMode = mode;
            }

            player.setGameMode(playerMode);

            final String displayMode = getGameModeString(playerMode);

            if (player == sender)
            {
                if (players == 1)
                {
                    success(sender, "Set your own game mode to " + displayMode + ".");
                }
            } else
            {
                success(player, sender.getName() + " set your game mode to " + displayMode + ".");
            }
        }

        final Player zero = targets.get(0);

        if (players > 1)
        {
            if (mode == null)
            {
                success(sender, "Toggled the game mode of " + players + " players.");
            } else
            {
                success(
                    sender,
                    "Set the game mode of " +
                        players +
                        " players to " +
                        getGameModeString(mode) +
                        "."
                );
            }
        } else if (zero != sender)
        {
            if (mode == null)
            {
                success(sender, "Toggled the game mode of " + zero.getName() + ".");
            }
            else
            {
                success(
                    sender,
                    "Set the game mode of " + zero.getName() + " to " + getGameModeString(mode)
                );
            }
        }
    }

    private static String getGameModeString(final GameMode mode)
    {
        final String modeString = mode.toString();

        return modeString.charAt(0) +
            modeString.substring(1).toLowerCase();
    }

    private List<Player> getTargetsFromArgs(
        final CommandSender sender,
        final String[] args
    )
    {
        if (args.length == 0)
        {
            error(sender, "Invalid target! None provided.");
            return null;
        }

        final Server server = getPlugin().getServer();

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
                error(sender, "Invalid target: " + name);
                return null;
            }

            targets.add(player);
        }

        return targets;
    }

    // TODO: Permission check
    @Override
    public List<String> tab(
        final Necessities plugin,
        final CommandSender sender,
        final String alias,
        final String[] args
    )
    {
        List<String> result = Collections.emptyList();

        final List<String> playerSuggestions = plugin.getServer()
            .getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .collect(Collectors.toList());

        // /gm<identifier>
        if (GAME_MODE_ALIASES.containsKey(alias))
        {
            result = playerSuggestions;

            if (args.length == 1)
            {
                result.add("*");
            }
        } else
        {
            final List<String> identifiers = new ArrayList<>(IDENTIFIERS.keySet());

            System.out.println(args.length);

            // gm
            switch (args.length)
            {
                // recommend identifiers before anything
                case 1:
                    result = identifiers;

                    if (!args[0].equals(""))
                    {
                        result.addAll(playerSuggestions);
                        result.add("*");
                    }
                    break;
                case 2:
                    if (!args[0].equals("*"))
                    {
                        result = playerSuggestions;
                    }
                    break;
                default:
                    // this can now only be players, so 2 and above should recommend players, if no
                    // previous args are *
                    for (final String arg : args)
                    {
                        if (arg.equals("*"))
                        {
                            break;
                        }
                    }

                    result = playerSuggestions;
                    break;
            }
        }

        final String current = args[args.length - 1];

        if (!current.trim().equals(""))
        {
            result = result.stream()
                .filter(suggestion -> suggestion.startsWith(current))
                .collect(Collectors.toList());
        }

        result.sort(Comparator.naturalOrder());

        return result;
    }

    private static String[] getCustomAliases(final String... aliases)
    {
        final String[] next = new String[IDENTIFIERS.size() + aliases.length];

        int i = 0;

        for (final String alias : aliases)
        {
            next[i] = alias;
            i++;
        }

        for (final String identifier : IDENTIFIERS.keySet())
        {
            final String alias = "gm" + identifier;
            GAME_MODE_ALIASES.put(alias, IDENTIFIERS.get(identifier));
            next[i] = alias;
            i++;
        }

        // Now that we're done, add the rest of the identifiers. This is hacky, but it works.
        for (final GameMode mode : GameMode.values())
        {
            IDENTIFIERS.put(mode.toString().toLowerCase(), mode);
        }

        return next;
    }

}
