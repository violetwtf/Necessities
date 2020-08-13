package wtf.violet.necessities;

import org.bukkit.plugin.java.JavaPlugin;
import wtf.violet.necessities.command.GameModeCommand;
import wtf.violet.necessities.command.NecessitiesCommand;

public final class Necessities extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        new GameModeCommand(this).sneakyRegister();
        new NecessitiesCommand(this).sneakyRegister();
    }

}
