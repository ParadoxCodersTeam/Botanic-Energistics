package pct.botanic.energistics.blocks.tile;

import appeng.api.networking.crafting.ICraftingProvider;
import appeng.tile.grid.AENetworkTile;
import vazkii.botania.api.mana.IManaReceiver;

/**
 * Created by beepbeat on 08.12.2015.
 */
public abstract class TileGeneric extends AENetworkTile implements ICraftingProvider, IManaReceiver {
    public abstract void setMana(int i);
}
