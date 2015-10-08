package pct.botanic.energistics.utilities.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import pct.botanic.energistics.blocks.render.TESRRunicAssembler;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;

/**
 * Created by beepbeat on 08.10.2015.
 */
public class ClientProxy extends CommonProxy {

    public static void registerTESR(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileAERuneAssembler.class, new TESRRunicAssembler());
    }
}
