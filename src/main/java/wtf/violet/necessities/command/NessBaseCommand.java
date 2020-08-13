package wtf.violet.necessities.command;

import wtf.violet.common.command.BaseCommand;
import wtf.violet.necessities.Necessities;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NessBaseCommand extends BaseCommand<Necessities>
{

    public NessBaseCommand(
        final Necessities ness,
        final String description,
        final String usage,
        final String permission,
        final String... aliases
    )
    {
        super(
            ness,
            description,
            usage,
            permission == null ? null : "ness." + permission,
            getAliases(aliases)
        );
    }

    @Override
    public void register()
        throws
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException
    {
        super.register();

        getPlugin()
            .getLogger()
            .info("Commands > Command /" + getName() + " registered successfully!");
    }

    private static String[] getAliases(final String... aliases)
    {
        final List<String> filtered = Arrays.stream(aliases)
            .map(alias -> alias.replace("!", ""))
            .collect(Collectors.toList());

        long addCount = Arrays.stream(aliases).filter(alias -> !alias.startsWith("!")).count();

        final String[] next = new String[(int) (aliases.length + addCount)];

        int i = 0;

        for (int j = 0; j < aliases.length; j++)
        {
            final String alias = aliases[j];
            next[i] = filtered.get(j);

            if (!alias.startsWith("!"))
            {
                next[i + 1] = "n" + alias;
                i++;
            }

            i++;
        }

        return next;
    }

}
