package no.runsafe.toybox.command;

import no.runsafe.framework.api.command.argument.EntityTypeArgument;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class SpawnMob extends PlayerCommand
{
	public SpawnMob()
	{
		super(
			"spawnmob", "Spawns a mob", "runsafe.toybox.spawnmob",
			new EntityTypeArgument("name", true), new RequiredArgument("count")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		int n = Integer.parseInt(parameters.get("count"));
		String name = parameters.get("name");

		if (name.equalsIgnoreCase("horse"))
			return "&cPlease use /spawnhorse for that.";
		for (int i = 0; i < n; ++i)
			executor.getWorld().spawnCreature(executor.getLocation(), name);
		return null;
	}
}
