package no.runsafe.toybox.events;

import no.runsafe.framework.event.inventory.IInventoryClosed;
import no.runsafe.framework.server.inventory.RunsafeInventory;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.toybox.handlers.CarePackageHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Stanley
 * Date: 30/04/13
 * Time: 04:52
 * To change this template use File | Settings | File Templates.
 */
public class InventoryClose implements IInventoryClosed
{
	public InventoryClose(CarePackageHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void OnInventoryClosed(RunsafePlayer player, RunsafeInventory inventory)
	{
		if (this.handler.PlayerHasOpenCarePackage(player))
			this.handler.DropPackage(player);
	}

	private CarePackageHandler handler;
}