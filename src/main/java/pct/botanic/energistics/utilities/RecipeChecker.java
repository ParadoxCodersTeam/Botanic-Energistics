package pct.botanic.energistics.utilities;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.material.ItemManaResource;

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

    public static boolean isAltarRecipe(Object[] ObjInput, ItemStack output) {
        ItemStack stack;
        List Recipes = BotaniaAPI.runeAltarRecipes;
        List<Object> input = new ArrayList<Object>();

        for (Object obj0 : ObjInput) {
            if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.manaResource, 1, 0))) input.add("ingotManasteel");
            else if (obj0 instanceof ItemStack && ((ItemStack) obj0).isItemEqual(new ItemStack(ModItems.manaResource, 1, 1)))  input.add("manaPearl");
            else if (obj0 instanceof ItemStack) input.add(obj0);
        }
        for (Object obj : Recipes) {
            RecipeRuneAltar rec = (RecipeRuneAltar) obj;
            if (rec == null || rec.getInputs() == null || rec.getOutput() == null || output == null)
                return false;
            output.stackSize = rec.getOutput().stackSize;
            if (rec.getInputs().containsAll(input) && input.size() == rec.getInputs().size() /*&& rec.getOutput() == output*/)
                return true;
        }
        return false;
    }

    public static boolean isAltarRecipe(IInventory inventory){
        List Recipes = BotaniaAPI.runeAltarRecipes;
        for (RecipeRuneAltar rec : (List<RecipeRuneAltar>) Recipes){
            if (rec.matches(inventory)) return true;
        }
        return false;
    }
}
