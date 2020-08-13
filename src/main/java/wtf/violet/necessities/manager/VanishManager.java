package wtf.violet.necessities.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wtf.violet.necessities.Necessities;
import wtf.violet.necessities.listener.NessBaseListener;

import java.util.ArrayList;
import java.util.List;

public class VanishManager extends NessBaseListener
{

    private final List<Player> vanishedPlayers = new ArrayList<>();
    private final Necessities ness;

    public VanishManager(final Necessities ness)
    {
        this.ness = ness;
        register(ness);
    }

    /**
     * Find out whether a player is vanished
     * @param player Player to query
     * @return Whether they are vanished
     */
    public boolean isVanished(final Player player)
    {
        return vanishedPlayers.contains(player);
    }

    /**
     * Vanish a player
     *
     * @param player Player
     * @return Whether or not the player was vanished
     */
    public boolean toggleVanish(final Player player)
    {
        final boolean shouldVanish = !isVanished(player);

        for (final Player target : ness.getServer().getOnlinePlayers())
        {
            if (shouldVanish)
            {
                target.hidePlayer(ness, player);
            } else
            {
                target.showPlayer(ness, player);
            }
        }

        if (shouldVanish)
        {
            vanishedPlayers.add(player);
        } else
        {
            vanishedPlayers.remove(player);
        }

        return shouldVanish;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        for (final Player player : vanishedPlayers)
        {
            event.getPlayer().hidePlayer(ness, player);
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event)
    {
        vanishedPlayers.remove(event.getPlayer());
    }

}
