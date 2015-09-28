package pct.botanic.energistics.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import pct.botanic.energistics.blocks.tile.TileDataVine;

import pct.botanic.energistics.renderers.blocks.TileVineRenderer;
import sun.java2d.pipe.RenderingEngine;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.client.MinecraftForgeClient;


public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileDataVine.class, new TileVineRenderer());

}
}
