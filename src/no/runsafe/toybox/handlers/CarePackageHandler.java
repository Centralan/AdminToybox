package no.runsafe.toybox.handlers;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.block.RunsafeBlock;
import no.runsafe.framework.minecraft.block.RunsafeChest;
import no.runsafe.framework.minecraft.entity.RunsafeFallingBlock;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

public class CarePackageHandler
{
	public CarePackageHandler()
	{
		this.awaitingDrops = new HashMap<String, RunsafeInventory>();
		this.fallingDrops = new HashMap<Integer, RunsafeInventory>();
	}

	public void CreateCarePackage(RunsafePlayer player)
	{
		RunsafeInventory newInventory = RunsafeServer.Instance.createInventory(null, 27, "Care Package"); // Create
		player.openInventory(newInventory); // Show player the inventory.
		this.awaitingDrops.put(player.getName(), newInventory); // Store the inventory pointer.
	}

	public boolean PlayerHasOpenCarePackage(RunsafePlayer player)
	{
		return this.awaitingDrops.containsKey(player.getName()); // Does the player have an awaiting drop?
	}

	private RunsafeInventory GetAwaitingInventory(RunsafePlayer player)
	{
		return this.awaitingDrops.get(player.getName()); // Get an awaiting inventory linked to a player.
	}

	private void RemoveAwaitingInventory(RunsafePlayer player)
	{
		this.awaitingDrops.remove(player.getName()); // Remove awaiting drop linked to player.
	}

	private RunsafeInventory GetFallingInventory(Integer entityID)
	{
		return this.fallingDrops.get(entityID);
	}

	private void RemoveFallingInventory(Integer entityID)
	{
		this.fallingDrops.remove(entityID);
	}

	public void DropPackage(RunsafePlayer player)
	{
		RunsafeFallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation(), Material.CHEST, (byte) 0);
		block.setDropItem(false);
		this.fallingDrops.put(block.getEntityId(), this.GetAwaitingInventory(player));
		this.RemoveAwaitingInventory(player);
	}

	public boolean CheckDrop(Integer entityID)
	{
		return this.fallingDrops.containsKey(entityID);
	}

	public void ProcessDrop(Integer entityID, IBlock block)
	{
		List<RunsafeMeta> items = this.GetFallingInventory(entityID).getContents();
		this.RemoveFallingInventory(entityID);

		if (block.getBlockState() instanceof RunsafeChest)
		{
			RunsafeChest chest = (RunsafeChest) block.getBlockState();
			RunsafeInventory chestInventory = chest.getInventory();

			for (RunsafeMeta item : items)
				chestInventory.addItems(item);
		}
	}

	private final HashMap<String, RunsafeInventory> awaitingDrops;
	private final HashMap<Integer, RunsafeInventory> fallingDrops;
}
