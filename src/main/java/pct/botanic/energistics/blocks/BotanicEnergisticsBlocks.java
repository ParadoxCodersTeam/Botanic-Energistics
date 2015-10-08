package pct.botanic.energistics.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import pct.botanic.energistics.blocks.tile.TileAEDaisy;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class BotanicEnergisticsBlocks {
    public static RecipeElvenTrade AERuneAssembler;

    public static void Register(){
        //RuneAssembler
        GameRegistry.registerTileEntity(TileAERuneAssembler.class, "tile.runeassembler");
        GameRegistry.registerBlock(new AERuneAssembler(), "AERuneAssembler");
        AERuneAssembler = BotaniaAPI.registerElvenTradeRecipe(new ItemStack(new AERuneAssembler()), new ItemStack(ModBlocks.runeAltar), new ItemStack(ModItems.tinyPlanet), new ItemStack(ModItems.temperanceStone));

        //AE-Daisy
        GameRegistry.registerTileEntity(TileAEDaisy.class, "tile.aedaisy");
        GameRegistry.registerBlock(new AEPureDaisy(), "AEDaisy");
    }

}
