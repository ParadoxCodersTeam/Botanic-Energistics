package pct.botanic.energistics.utilities;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.material.ItemManaResource;
import vazkii.botania.common.lib.LibOreDict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class RecipeChecker {

    public static boolean isAltarRecipe(ItemStack[] input, ItemStack output){
        List Recipes = BotaniaAPI.runeAltarRecipes;
        for (Object obj : Recipes){
            RecipeRuneAltar rec = (RecipeRuneAltar) obj;
            if (rec == null || rec.getInputs() == null || input == null || rec.getOutput() == null || output == null) return false;
            if (rec.getInputs().containsAll(Arrays.asList(input)) && input.length == rec.getInputs().size() && rec.getOutput() == output) return true;
        }
        return false;
    }

    public static boolean isAltarRecipe(Object[] ObjInput, ItemStack output, TileEntity te) {
        ItemStack stack;
        List Recipes = BotaniaAPI.runeAltarRecipes;
        List<Object> input = new ArrayList<Object>();

        for (Object obj0 : ObjInput) {
            if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.manaResource, 1, 0))) input.add("ingotManasteel");
            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.manaResource, 1, 1)))  input.add("manaPearl");
            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.manaResource, 1, 2)))  input.add("manaDiamond");
            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(Blocks.stone)))  input.add("stone");
            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(Blocks.leaves)))  input.add("treeLeaves");

            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).getItem() ==  ModItems.rune) {
                String[] rune = LibOreDict.RUNE;
                for (int i = 0; i < rune.length; i++) {
                    if (((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.rune, 1, i)))
                        input.add(LibOreDict.RUNE[i]);
                }
            }
            else if (obj0 instanceof ItemStack) input.add(obj0);
        }
        for (Object obj : Recipes) {
            RecipeRuneAltar rec = (RecipeRuneAltar) obj;
            if (rec == null || rec.getInputs() == null || rec.getOutput() == null || output == null)
                return false;
            output.stackSize = rec.getOutput().stackSize;
            boolean containsAll = true;
            for (Object object : rec.getInputs()){
              containsAll = containsAll && input.contains(object);
            }
            if (/*rec.getInputs().containsAll(input)*/ containsAll && input.size() == rec.getInputs().size() /*&& rec.getOutput() == output*/) {
                if (te instanceof TileAERuneAssembler) ((TileAERuneAssembler) te).setManacost(rec.getManaUsage() * 2);
                return true;
            }
        }
        return false;
    }

}
