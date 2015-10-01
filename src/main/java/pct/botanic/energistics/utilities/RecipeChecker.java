package pct.botanic.energistics.utilities;

import appeng.api.storage.data.IAEItemStack;
import appeng.util.item.AEStack;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.material.ItemManaResource;
import vazkii.botania.common.lib.LibOreDict;

import java.util.*;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class RecipeChecker {

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
/*            boolean containsAll = true;
            for (Object object : rec.getInputs()){
              for (Object object2 : input){

                  if (object instanceof String && object2 instanceof String && ((String) object).equalsIgnoreCase((String) object2)){
                      containsAll = true;
                      break;}

                  if (object instanceof ItemStack && object2 instanceof ItemStack){
                      ItemStack is = (ItemStack) object;
                      ItemStack is2 = (ItemStack) object2;
                      *//*containsAll = containsAll && (is.getItem() == is2.getItem());
                      break;*//*
                      containsAll = GameData.getItemRegistry().getNameForObject(is.getItem()).equalsIgnoreCase(GameData.getItemRegistry().getNameForObject(is2.getItem()));

                  }
                  //containsAll = containsAll && false;

    //break;
              }
                containsAll = false;
            }*/
            output.stackSize = rec.getOutput().stackSize;
            if (containsAllItems(input, rec.getInputs()) && input.size() == rec.getInputs().size() && rec.getOutput().isItemEqual(output)){
                if (te instanceof TileAERuneAssembler) ((TileAERuneAssembler) te).setManacost(rec.getManaUsage() * 2);
                return true;
            }
        }
        return false;
    }

    public static boolean isAltarRecipe(IAEItemStack[] AEStacks, ItemStack output, TileEntity te){
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (IAEItemStack stack : AEStacks){
            stacks.add(stack.getItemStack());
        }
        return isAltarRecipe(stacks.toArray(), output, te);
    }


    public static boolean ItemStackEqual(ItemStack is1, ItemStack is2){
        return GameData.getItemRegistry().getNameForObject(is1.getItem()).equalsIgnoreCase(GameData.getItemRegistry().getNameForObject(is2.getItem()));
    }


    public static boolean containsAllItems(List<Object> collection1, List<Object> collection2){
/*        boolean bool = false;
        for (Object a : collection1){
            for (Object b: collection2){
                if (a instanceof String && b instanceof String) bool = ((String) a).equalsIgnoreCase((String) b);

                if (a instanceof ItemStack && b instanceof ItemStack){
                    bool = (ItemStackEqual((ItemStack) a, (ItemStack) b));
                }

            }
        }
    return bool;*/

        for (int i = 0; i < collection1.size(); i++) {
            Object o = collection1.get(i);
            if (o instanceof String) continue;
            if (o instanceof ItemStack) {
                Item tmp = ((ItemStack) o).getItem();
                collection1.set(i, GameData.getItemRegistry().getNameForObject(tmp));
            }
        }

        for (int i = 0; i < collection2.size(); i++) {
            Object o = collection2.get(i);
            if (o instanceof String) continue;
            if (o instanceof ItemStack) {
                Item tmp = ((ItemStack) o).getItem();
                collection2.set(i, GameData.getItemRegistry().getNameForObject(tmp));
            }
        }
        return collection1.containsAll(collection2);
    }


}
