package no.runsafe.toybox.command;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class SpawnMob extends PlayerCommand
{
	public SpawnMob()
	{
		super("spawnmob", "Spawns a mob", "runsafe.toybox.spawnmob", "name", "count");
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
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
