package pct.botanic.energistics.utilities;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by beebeat on 22.08.2015.
 */
public class RecipeChecker {

    public static boolean isAltarRecipe(ItemStack[] input, ItemStack output){
        List Recipes = BotaniaAPI.runeAltarRecipes;
        for (Object obj : Recipes){
            RecipeRuneAltar rec = (RecipeRuneAltar) obj;
            if (rec.getInputs().containsAll(Arrays.asList(input)) && input.length == rec.getInputs().size() && rec.getOutput() == output) return true;
        }
        return false;
    }
}
