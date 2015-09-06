package pct.botanic.energistics.items;

import appeng.api.AEApi;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.storage.data.IAEItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.utilities.RecipeChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class RuneAssemblerCraftingPattern extends Item implements ICraftingPatternDetails, ICraftingPatternItem {

    ItemStack[] input;
    ItemStack output;

    public RuneAssemblerCraftingPattern() {
        setCreativeTab(BotanicEnergistics.botanicEnergisticsTab);
        setMaxStackSize(64);
        setUnlocalizedName("botanicpattern");
        output = new ItemStack(Items.apple);
        input = new ItemStack[9];
        input[0] = new ItemStack(Items.record_11);
    }

    public RuneAssemblerCraftingPattern(ItemStack[] inputStacks, ItemStack output) {
        this.input = inputStacks;
        this.output = output;
        setCreativeTab(CreativeTabs.tabMisc);
        setMaxStackSize(64);
    }

    @Override
    public ItemStack getPattern() {
        return new ItemStack(this);
    }

    @Override
    public boolean isValidItemForSlot(int i, ItemStack itemStack, World world) {
        return false;
    }

    @Override
    public boolean isCraftable() {
        return RecipeChecker.isAltarRecipe(input, output);
    }

    @Override
    public IAEItemStack[] getInputs() {
        //return new IAEItemStack[9];
        IAEItemStack[] AEStack = new IAEItemStack[9];
        if (input == null) return AEStack;
        for (int i = 0, inputLength = input.length; i < inputLength; i++) {
            ItemStack stack = input[i];
            AEStack[i] =  AEApi.instance().storage().createItemStack(stack);
        }
        return AEStack;
    }

    @Override
    public IAEItemStack[] getCondensedInputs() {
        return new IAEItemStack[0];
    }

    @Override
    public IAEItemStack[] getCondensedOutputs() {
        return new IAEItemStack[0];
    }

    @Override
    public IAEItemStack[] getOutputs() {
        if (!isCraftable()){
            return null;
        }
        IAEItemStack[] stack = new IAEItemStack[1];
        stack[0] = AEApi.instance().storage().createItemStack(output);
        return stack;
    }

    @Override
    public boolean canSubstitute() {
        return false;
    }

    @Override
    public ItemStack getOutput(InventoryCrafting inventory, World world) {
        if (isCraftable()){
            return output;
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }

    @Override
    public ICraftingPatternDetails getPatternForItem(ItemStack itemStack, World world) {
        return new RuneAssemblerCraftingPattern(null,itemStack);
    }
}
