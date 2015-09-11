package pct.botanic.energistics.blocks.tile;

import appeng.api.networking.crafting.ICraftingMedium;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.storage.data.IAEItemStack;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkTile;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import org.apache.logging.log4j.LogManager;
import pct.botanic.energistics.items.RuneAssemblerCraftingPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import pct.botanic.energistics.utilities.RecipeChecker;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.recipe.RecipeRuneAltar;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class TileAERuneAssembler extends AENetworkTile implements ICraftingProvider, ISidedInventory, IManaReceiver {

    private boolean validRecipe;
    private int manacost = 0;

    public void setMana(int mana) {
        this.currMana = mana;
    }

    private enum NBTKeys {storedMana}
    List RuneAltarRecipes = BotaniaAPI.runeAltarRecipes;
    private ItemStack[] inventory = new ItemStack[11];
    List availRecipes = new ArrayList();
    int currMana = 0, maxmana = 30000 /* 3*/;

    public void setManacost(int manacost){
        this.manacost = manacost;
    }



    public TileAERuneAssembler() {
        setName("runeassembler");
        availRecipes.clear();
        for (ItemStack stack : inventory) {
            if (stack != null && stack.getItem() instanceof RuneAssemblerCraftingPattern) {
                RuneAssemblerCraftingPattern pattern = (RuneAssemblerCraftingPattern) stack.getItem();
                if (pattern.getOutputs() != null)
                    availRecipes.add(pattern.getOutputs()[0].getItemStack());
            }
        }
    }

    public TileAERuneAssembler(InventoryPlayer inventoryPlayer, TileAERuneAssembler tileEntity) {
        availRecipes.clear();
        for (ItemStack stack : inventory) {
            if (stack != null && stack.getItem() instanceof RuneAssemblerCraftingPattern) {
                RuneAssemblerCraftingPattern pattern = (RuneAssemblerCraftingPattern) stack.getItem();
                if (pattern.getOutputs() != null)
                    availRecipes.add(pattern.getOutputs()[0].getItemStack());
            }
        }
    }

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
/*        for (RecipeRuneAltar rec : BotaniaAPI.runeAltarRecipes){
            helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(rec.getInputs().toArray(), rec.getOutput()));
        }*/
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        return false;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @TileEvent(TileEventType.TICK)
    public void onTick() {
        List<ItemStack> input = new ArrayList<ItemStack>();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory[i];
            if (stack == null) continue;
            input.add(stack);
        }
        if (input.size() > 0 && inventory[10] != null)
        validRecipe = RecipeChecker.isAltarRecipe(input.toArray(), inventory[10], this);

        if (validRecipe && currMana >= manacost){
            validRecipe = false;
            for (int i = 0; i < 9; i++){
                inventory[i] = null;
            }
            inventory[9] = inventory[10];
            currMana -= manacost;
        }
    }

    //region ISidedInventory

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    /**
     * Returns the stack in slot i
     *
     * @param slot
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    public ItemStack decrStackSize(int slot, int decrAmount) {

        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= decrAmount) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(decrAmount);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName() {
        return "inv.RuneAssembler.name";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param player
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param slot
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return slot != 10;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[]{0,1,2,3,4,5,6,7,8,9};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return slot != 10;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot != 10;
    }

    //endregion
    @Override
    public boolean isFull() {
        return currMana == maxmana;
    }

    @Override
    public void recieveMana(int i) {
        if (isFull()) return;
        currMana = currMana + i < maxmana ? currMana + i : maxmana;
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return !isFull();
    }

    @Override
    public int getCurrentMana() {
        if (isFull())
        return maxmana;
        else return currMana;
        //return 10;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeNBT(NBTTagCompound cmp){
        cmp.setInteger(NBTKeys.storedMana.toString(), currMana);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readNBT(NBTTagCompound cmp){
        currMana = cmp.getInteger(NBTKeys.storedMana.toString());
    }
}
