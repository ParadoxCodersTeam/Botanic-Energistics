package pct.botanic.energistics.blocks.tile;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.security.MachineSource;
import appeng.me.GridAccessException;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkTile;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import pct.botanic.energistics.blocks.AEPureDaisy;
import pct.botanic.energistics.items.RuneAssemblerCraftingPattern;
import pct.botanic.energistics.utilities.Config;
import pct.botanic.energistics.utilities.RecipeChecker;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.recipe.RecipePureDaisy;
import vazkii.botania.common.block.decor.IFloatingFlower;
import vazkii.botania.common.block.mana.BlockRuneAltar;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.List;

public class TileAEDaisy extends TileGeneric implements IFloatingFlower {

    private int currMana = 0, maxMana = 30000, manaCost = 0, multiplier = 1;
    private ItemStack input, output;
    private boolean isCrafting = false;
    private enum NBTKeys{currMana, Root}

    public TileAEDaisy() {

    }


    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        for (RecipePureDaisy rec : BotaniaAPI.pureDaisyRecipes) {
            helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(new Object[]{rec.getInput()}, new ItemStack(rec.getOutput()), 10000));
        }
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails pattern, InventoryCrafting inventoryCrafting) {
        if (!isCrafting && pattern instanceof RuneAssemblerCraftingPattern) {
            output = pattern.getOutputs()[0].getItemStack();
            input = pattern.getInputs()[0].getItemStack();
            manaCost = ((RuneAssemblerCraftingPattern) pattern).getManaUsage() * multiplier;
            //return true;
            return currMana >= manaCost;
        }
        return false;
    }

    @Override
    public boolean isBusy() {
        return isCrafting;
    }

    @Override
    public boolean isFull() {
        return currMana >= maxMana;
    }

    @Override
    public void recieveMana(int i) {
        if (isFull()) return;
        currMana = (currMana + i) <= maxMana ? currMana + i : maxMana;
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return !isFull();
    }

    @Override
    public int getCurrentMana() {
        return currMana;
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
                    if (((TileSpreader) te).isULTRA_SPREADER() || ((TileSpreader) te).isDreamwood()) {
                        manaBurst.setDead();
                    }
                }
            }
        }
        //endregion
        //region PassiveMode
        if (Config.isPassiveMode()){
            try {
                boolean returnv = true;
                Item daisy = vazkii.botania.common.item.block.ItemBlockSpecialFlower.ofType("puredaisy").getItem();
                if (worldObj.getBlock(xCoord + 1, yCoord, zCoord) != Blocks.air && (Item.getItemFromBlock(worldObj.getBlock(xCoord + 1, yCoord, zCoord)).equals(daisy)))
                    returnv = false;
                if (worldObj.getBlock(xCoord - 1, yCoord, zCoord) != Blocks.air && (Item.getItemFromBlock(worldObj.getBlock(xCoord - 1, yCoord, zCoord)).equals(daisy)))
                    returnv = false;

                if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) != Blocks.air && (Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord + 1, zCoord)).equals(daisy)))
                    returnv = false;
                if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) != Blocks.air && (Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord - 1, zCoord)).equals(daisy)))
                    returnv = false;

                if (worldObj.getBlock(xCoord, yCoord, zCoord + 1) != Blocks.air && (Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord, zCoord + 1)).equals(daisy)))
                    returnv = false;
                if (worldObj.getBlock(xCoord, yCoord, zCoord - 1)
                        != Blocks.air &&
                        (Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord, zCoord - 1)) == null ? Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord, zCoord - 1)) : (worldObj.getBlock(xCoord, yCoord, zCoord - 1).getItemDropped(0, null, 0))).equals(daisy))
                    returnv = false;
                if (returnv) return;

            }catch (Exception ignored){return;}

    }
        //endregion
        boolean validRecipe = false;
        if (input != null && output != null) {
            validRecipe = RecipeChecker.isDaisyRecipe(input, output);
        }

        if (validRecipe && currMana >= manaCost) {
            isCrafting = true;
            validRecipe = false;
            //inventory[9] = inventory[10].copy();
            try {
                //if (this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(inventory[10].copy()), Actionable.SIMULATE, new MachineSource(this)) == null) return;
                this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(output.copy()), Actionable.MODULATE, new MachineSource(this));
             //   if (!getProxy().getCrafting().isRequesting(AEApi.instance().storage().createItemStack(output))) {
                    input = null;
                    output = null;
//                }

                currMana -= manaCost;

            } catch (GridAccessException e) {
                //
            }
            finally {
                isCrafting = false;
            }

        }


    }

    @Override
    public ItemStack getDisplayStack() {
        return new ItemStack(new AEPureDaisy());
    }

    @Override
    public IslandType getIslandType() {
        return IslandType.GRASS;
    }

    @Override
    public void setIslandType(IslandType islandType) {

    }

    @Override
    public void setMana(int i) {
        this.currMana = i;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeNBT(NBTTagCompound cmp){
        NBTTagCompound root = new NBTTagCompound();
        root.setInteger(NBTKeys.currMana.toString(), currMana);
        cmp.setTag(NBTKeys.Root.toString(), root);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readNBT(NBTTagCompound cmp){
        if (cmp.hasKey(NBTKeys.Root.toString())){
            currMana = ((NBTTagCompound) cmp.getTag(NBTKeys.Root.toString())).getInteger(NBTKeys.currMana.toString());
        }
    }
}
