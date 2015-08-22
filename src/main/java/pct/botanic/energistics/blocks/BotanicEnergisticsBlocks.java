package pct.botanic.energistics.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class BotanicEnergisticsBlocks {

    public static void Register(){
        GameRegistry.registerTileEntity(TileAERuneAssembler.class, "tile.runeassembler");
        GameRegistry.registerBlock(new AERuneAssembler(), "AERuneAssembler");
    }

}
