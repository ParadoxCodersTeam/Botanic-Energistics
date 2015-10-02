package pct.botanic.energistics.items;

import appeng.api.AEApi;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.storage.data.IAEItemStack;
import appeng.items.misc.ItemEncodedPattern;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.common.item.ItemCraftPattern;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class PatternATMPT2 implements ICraftingPatternDetails {
    IAEItemStack[] input = new IAEItemStack[9];
    IAEItemStack[] output = new IAEItemStack[1];

    protected PatternATMPT2() {

    }

    public PatternATMPT2(IAEItemStack[] input, IAEItemStack output) {
        this.input = input;
        this.output[0] = output;
    }

    public PatternATMPT2(ItemStack[] input, ItemStack output) {
        IAEItemStack[] tmpinput = new IAEItemStack[input.length];
        for (int i = 0; i < input.length; i++) {
            ItemStack stack = input[i];
            tmpinput[i] = AEApi.instance().storage().createItemStack(stack);
        }
        this.input = tmpinput;
        this.output[0] = AEApi.instance().storage().createItemStack(output);
    }

    @Override
    public ItemStack getPattern() {
        //return new ItemStack(new ItemEncodedPattern());
        return null;
    }

    @Override
    public boolean isValidItemForSlot(int i, ItemStack itemStack, World world) {
        return true;
    }

    @Override
    public boolean isCraftable() {
        return true;
    }

    @Override
    public IAEItemStack[] getInputs() {
        return input;
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
        return output;
    }

    @Override
    public boolean canSubstitute() {
        return false;
    }

    @Override
    public ItemStack getOutput(InventoryCrafting inventoryCrafting, World world) {
       return output[0].getItemStack();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }
}
