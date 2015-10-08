package pct.botanic.energistics.blocks.tile;


import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.GridAccessException;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkTile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import pct.botanic.energistics.items.RuneAssemblerCraftingPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import pct.botanic.energistics.utilities.RecipeChecker;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.crafting.recipe.HeadRecipe;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.List;



public class TileAERuneAssembler extends AENetworkTile implements ICraftingProvider, ISidedInventory, IManaReceiver {


    private boolean validRecipe;
    private int manacost = 0;
    private ItemStack output;
    private IAEItemStack[] inputs;


    public void setMana(int mana) {
        this.currMana = mana;
    }

    private enum NBTKeys {storedMana, inventory, amount, item, metadata, nbt}
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
        if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            this.gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
            gridProxy.setIdlePowerUsage(0.5D);
        }

    }

    public TileAERuneAssembler(InventoryPlayer inventoryPlayer, TileAERuneAssembler tileEntity) {
        this();
    }

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        List<RecipeRuneAltar> recipes = BotaniaAPI.runeAltarRecipes;
        for (RecipeRuneAltar rec : recipes){
            if (rec instanceof HeadRecipe) continue;
            //if (rec.getOutput().isItemEqual(new ItemStack(ModItems.rune, 1, 3)) && !rec.getInputs().contains(new ItemStack(Blocks.carpet, 1, 0))) continue;
            if (rec.getOutput().isItemEqual(new ItemStack(ModItems.rune, 1, 3))){
                List<Object> tmp = new ArrayList<Object>();
                for (Object obj: rec.getInputs()){
                    if (obj instanceof ItemStack && ((ItemStack) obj).getItem() == Item.getItemFromBlock(Blocks.carpet)){
                        tmp.add(new ItemStack(Blocks.carpet,1 ,0));
                    }
                    else{
                        tmp.add(obj);
                    }
                }
                helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(tmp.toArray(), rec.getOutput(), rec.getManaUsage()));
                continue;
            }

            if (rec.getOutput().isItemEqual(new ItemStack(ModItems.rune, 1, 7))){
                List<Object> tmp = new ArrayList<Object>();
                for (Object obj: rec.getInputs()){
                    if (obj instanceof ItemStack && ((ItemStack) obj).getItem() == Item.getItemFromBlock(Blocks.wool)){
                        tmp.add(new ItemStack(Blocks.wool,1 ,0));
                    }
                    else{
                        tmp.add(obj);
                    }
                }
                helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(tmp.toArray(), rec.getOutput(), rec.getManaUsage()));
                continue;
            }

            helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(rec.getInputs().toArray(), rec.getOutput(), rec.getManaUsage()));
        }
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        if (iCraftingPatternDetails instanceof RuneAssemblerCraftingPattern){
            output = iCraftingPatternDetails.getOutputs()[0].getItemStack();
            inputs = iCraftingPatternDetails.getInputs();
            manacost = ((RuneAssemblerCraftingPattern) iCraftingPatternDetails).getManaUsage();
            //return true;
            return currMana >= (manacost * 2);
        }
        return false;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @TileEvent(TileEventType.TICK)
    public void onTick() {
        //region Nerfing
        List<EntityManaBurst> entities = this.getWorldObj().getEntitiesWithinAABB(EntityManaBurst.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord - 0.5, zCoord - 0.5, xCoord + 1.5, yCoord + 1.5, zCoord + 1.5));
        for (EntityManaBurst manaBurst : entities) {
            ChunkCoordinates coord = manaBurst.getBurstSourceChunkCoordinates();
            if (coord.posX == 0 && coord.posY == -1 && coord.posZ == 0 || manaBurst.getMana() == 120) {
                manaBurst.setFake(true);
                manaBurst.setMana(0);
                manaBurst.setDead();
            }
            TileEntity te = this.worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
            if (te instanceof TileSpreader) {
                ChunkCoordinates boundpos = ((TileSpreader) te).getBinding();
                if (boundpos == null) return;
                TileEntity boundtile = this.worldObj.getTileEntity(boundpos.posX, boundpos.posY, boundpos.posZ);
                if (boundtile != null && boundtile.xCoord == this.xCoord && boundtile.yCoord == this.yCoord && boundtile.zCoord == this.zCoord) {
                    if (!((TileSpreader) te).isULTRA_SPREADER()) {
                        manaBurst.setDead();
                    }
                }
            }
        }
        //endregion
        if (inputs == null) return;
        ItemStack initalStackSlot10 = null;
        if (inventory[10] != null) {
            //initalStackSlot10 = inventory[10];
        }
/*
        De-Implemented for actual AE-based crafting loop. may be reimplented later
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
        inventory[9] = inventory[10].copy();
        currMana -= manacost;
        if (initialStacksizeSlot10 != -1)
        inventory[10].stackSize = initialStacksizeSlot10;
        }
        */

        if (inputs.length > 0 && output != null) {
            Object[] inputs2 = new Object[inputs.length];
            for (int i = 0; i < inputs.length;i++){
                inputs2[i] = (Object) inputs[i];
            }
            validRecipe = RecipeChecker.isAltarRecipe(inputs, output, this);
        }

        if (validRecipe && currMana >= manacost) {
            validRecipe = false;
            //inventory[9] = inventory[10].copy();
            try {
                //if (this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(inventory[10].copy()), Actionable.SIMULATE, new MachineSource(this)) == null) return;
                this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(output.copy()), Actionable.MODULATE, new MachineSource(this));
                if (!getProxy().getCrafting().isRequesting(AEApi.instance().storage().createItemStack(output))){
                    for (int i = 0; i < inputs.length; i++) {
                         inputs[i] = null;
                         output = null;
                    }
                }

                currMana -= manacost;

            } catch (GridAccessException e) {
                //
            }

            if (initalStackSlot10 != null)
                inventory[10] = initalStackSlot10;
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
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return true;
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
         return currMana;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeNBT(NBTTagCompound cmp){
        cmp.setInteger(NBTKeys.storedMana.toString(), currMana);
        NBTTagCompound inv = new NBTTagCompound();
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] == null) continue;
            NBTTagCompound slot = new NBTTagCompound();
            slot.setInteger(NBTKeys.amount.toString(), inventory[i].stackSize);
            slot.setString(NBTKeys.item.toString(), GameData.getItemRegistry().getNameForObject(inventory[i].getItem()));
            slot.setInteger(NBTKeys.metadata.toString(), inventory[i].getItemDamage());
            if (inventory[i].hasTagCompound())
                slot.setString(NBTKeys.nbt.toString(), inventory[i].getTagCompound().toString());
            else
                slot.setString(NBTKeys.nbt.toString(), "");
            inv.setTag("#" + i, slot);
        }
        cmp.setTag(NBTKeys.inventory.toString(), inv);


    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readNBT(NBTTagCompound cmp){
        currMana = cmp.getInteger(NBTKeys.storedMana.toString());
        NBTTagCompound inv = cmp.getCompoundTag(NBTKeys.inventory.toString());
        for (int i = 0; i < inventory.length; i++){
            if (!inv.hasKey("#" + i)) continue;
            if (inv.getCompoundTag("#" + i) == null) continue;
            NBTTagCompound slot = inv.getCompoundTag("#" + i);
            inventory[i] = GameRegistry.makeItemStack(slot.getString(NBTKeys.item.toString()), slot.getInteger(NBTKeys.metadata.toString()), slot.getInteger(NBTKeys.amount.toString()), slot.getString(NBTKeys.nbt.toString()));
            //inventory[i].stackSize = 1; //slot.getInteger(NBTKeys.amount.toString());
          //  slot.setString(NBTKeys.item.toString(), inventory[i].getUnlocalizedName());
            //inventory[i] = new ItemStack(Block.getBlockFromName(slot.getString(NBTKeys.item.toString())));
           // inv.setTag("#" + i, slot);
        }
    }


}
