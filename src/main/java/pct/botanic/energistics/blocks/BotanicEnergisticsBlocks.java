package pct.botanic.energistics.blocks;

import appeng.api.AEApi;
import appeng.api.util.AEColor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import pct.botanic.energistics.blocks.tile.TileAEDaisy;
import pct.botanic.energistics.blocks.tile.TileAEElvenPortal;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.utilities.Config;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.subtile.SubTilePureDaisy;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

/**
 * Created by magnus97 on 22/08/2015.
 */
public class BotanicEnergisticsBlocks {
    public static RecipeElvenTrade RecRuneAssembler;
    public static RecipeElvenTrade RecAEDaisy;

    public static Block AEDaisy;
    public static Block RuneAssembler;
    public static Block ElvenPortal;

    public static void Register(){
        //RuneAssembler
        if (Config.isRuneAssembler()) {
            GameRegistry.registerTileEntity(TileAERuneAssembler.class, "tile.runeassembler");
            RuneAssembler = GameRegistry.registerBlock(new AERuneAssembler(), "AERuneAssembler");
            RecRuneAssembler = BotaniaAPI.registerElvenTradeRecipe(
                new ItemStack(RuneAssembler), new ItemStack(ModBlocks.runeAltar),
                new ItemStack(ModItems.tinyPlanet), new ItemStack(ModItems.temperanceStone),
                new ItemStack(AEApi.instance().definitions().blocks().craftingUnit().maybeBlock().get()),
                //new ItemStack(AEApi.instance().definitions().blocks().controller().maybeBlock().get()));
                new ItemStack((Block)Block.blockRegistry.getObject("appliedenergistics2:BlockController")));
        }

        //AE-Daisy
        if (Config.isAedaisy()) {
            GameRegistry.registerTileEntity(TileAEDaisy.class, "tile.aedaisy");
            AEDaisy = GameRegistry.registerBlock(new AEPureDaisy(), "AEDaisy");
            RecAEDaisy = BotaniaAPI.registerElvenTradeRecipe(
                new ItemStack(AEDaisy), ItemBlockSpecialFlower.ofType("puredaisy"),
                new ItemStack(ModItems.tinyPlanet), new ItemStack(ModItems.blackLotus),
                new ItemStack(ModItems.keepIvy), new ItemStack(ModItems.laputaShard, 1, 5),
                new ItemStack(AEApi.instance().definitions().blocks().craftingUnit().maybeBlock().get()),
//                new ItemStack(AEApi.instance().definitions().blocks().controller().maybeBlock().get()));
                new ItemStack((Block)Block.blockRegistry.getObject("appliedenergistics2:BlockController")));

        }
        //AEElvenPortal
        if (Config.isElvenPortal()){
            GameRegistry.registerTileEntity(TileAEElvenPortal.class, "tile.elvenportal");
            ElvenPortal = GameRegistry.registerBlock(new AEElvenPortal(), "ElvenPortal");
        }
    }

}
