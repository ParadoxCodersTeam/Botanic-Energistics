package pct.botanic.energistics.utilities.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import pct.botanic.energistics.blocks.render.TESRRunicAssembler;
import pct.botanic.energistics.blocks.tile.TileAEDaisy;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import vazkii.botania.client.render.tile.RenderTileFloatingFlower;

/**
 * Created by beepbeat on 08.10.2015.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAERuneAssembler.class, new TESRRunicAssembler());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileAEDaisy.class, new RenderTileFloatingFlower());
    }

}
