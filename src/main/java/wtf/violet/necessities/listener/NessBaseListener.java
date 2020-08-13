package wtf.violet.necessities.listener;

import org.bukkit.event.Listener;
import wtf.violet.necessities.Necessities;

public abstract class NessBaseListener implements Listener
{

    public void register(final Necessities ness)
    {
        ness.getServer().getPluginManager().registerEvents(this, ness);
    }

}
