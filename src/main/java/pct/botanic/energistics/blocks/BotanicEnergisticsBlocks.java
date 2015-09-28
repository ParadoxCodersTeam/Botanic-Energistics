package pct.botanic.energistics.blocks;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import pct.botanic.energistics.blocks.tile.*;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class BotanicEnergisticsBlocks {

    public static void Register(){
        //Runic Assembler
    	GameRegistry.registerTileEntity(TileAERuneAssembler.class, "tile.runeassembler");
        GameRegistry.registerBlock(new AERuneAssembler(), "AERuneAssembler");
        
        //Data Vine
        GameRegistry.registerTileEntity(TileDataVine.class, "tile.datavine");
        GameRegistry.registerBlock(new DataVine(), "AEdataVine");
    }

}
