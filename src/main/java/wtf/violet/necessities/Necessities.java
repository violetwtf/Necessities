package wtf.violet.necessities;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.violet.necessities.command.GameModeCommand;
import wtf.violet.necessities.command.NecessitiesCommand;
import wtf.violet.necessities.command.VanishCommand;
import wtf.violet.necessities.manager.VanishManager;

@Getter
public final class Necessities extends JavaPlugin
{

    private VanishManager vanishManager;

    @Override
    public void onEnable()
    {
        vanishManager = new VanishManager(this);

        new GameModeCommand(this).sneakyRegister();
        new NecessitiesCommand(this).sneakyRegister();
        new VanishCommand(this).sneakyRegister();
    }

}
