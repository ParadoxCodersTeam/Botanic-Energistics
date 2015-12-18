package pct.botanic.energistics.blocks.tile;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.GridAccessException;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import pct.botanic.energistics.items.RuneAssemblerCraftingPattern;
import pct.botanic.energistics.utilities.Config;
import pct.botanic.energistics.utilities.RecipeChecker;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.BlockAlfPortal;
import vazkii.botania.common.block.mana.BlockRuneAltar;
import vazkii.botania.common.block.tile.TileAlfPortal;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.crafting.recipe.HeadRecipe;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beepbeat on 06.12.2015.
 */
public class TileAEElvenPortal extends TileGeneric {
    private int currMana = 0, maxMana = 30000, manacost = 0, waitCounter = 0;
    private IAEItemStack[] inputs;
    private ItemStack output;
    private boolean validRecipe, isCrafting;
    private enum NBTKeys{currMana, Root, inputs, output}

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        int manac = 6000;
        List<RecipeElvenTrade> recipes = BotaniaAPI.elvenTradeRecipes;
        for (RecipeElvenTrade rec : recipes){
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
                helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(tmp.toArray(), rec.getOutput(), manac));
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
                helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(tmp.toArray(), rec.getOutput(), manac));
                continue;
            }

            helper.addCraftingOption(this, new RuneAssemblerCraftingPattern(rec.getInputs().toArray(), rec.getOutput(), manac));
        }
    }

    @TileEvent(TileEventType.TICK)
    public void onTick() {
        TileAlfPortal portal = null;

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
        //region PassiveMode
        if (Config.isPassiveMode()){
            boolean returnv = true;
            try {
                if ((worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof BlockAlfPortal) && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord).getBlockMetadata() == 0) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
                }
                if ((worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof BlockAlfPortal) && worldObj.getTileEntity(xCoord - 1, yCoord, zCoord).getBlockMetadata() == 0) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
                }
                if ((worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof BlockAlfPortal)) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
                }
                if ((worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof BlockAlfPortal) && worldObj.getTileEntity(xCoord, yCoord - 1, zCoord).getBlockMetadata() == 0) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
                }
                if ((worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof BlockAlfPortal) && worldObj.getTileEntity(xCoord, yCoord, zCoord + 1).getBlockMetadata() == 0) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
                }
                if ((worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof BlockAlfPortal) && worldObj.getTileEntity(xCoord, yCoord, zCoord - 1).getBlockMetadata() == 0) {
                    returnv = false;
                    portal = (TileAlfPortal) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
                }
                if (returnv) return;
            }
            catch (Exception e) {
                System.out.println("Spam!");
                return;
            }
        }
        //endregion   waitCounter++;
        //if (waitCounter % 3 != 0) return;

        if (portal == null) return;
        System.out.println(portal.ticksOpen);
        portal.onWanded();
        if (portal.ticksOpen < 51) return;
        if (inputs == null) return;


        if (inputs.length > 0 && output != null) {
            Object[] inputs2 = new Object[inputs.length];
            for (int i = 0; i < inputs.length;i++){
                inputs2[i] = (Object) inputs[i];
            }
            //validRecipe = RecipeChecker.isAltarRecipe(inputs, output, this);
            validRecipe = true;
        }

        if (validRecipe && currMana >= manacost) {
            validRecipe = false;
            isCrafting = true;
            //inventory[9] = inventory[10].copy();
            try {
                //if (this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(inventory[10].copy()), Actionable.SIMULATE, new MachineSource(this)) != null) return;
                this.getProxy().getStorage().getItemInventory().injectItems(AEApi.instance().storage().createItemStack(output.copy()), Actionable.MODULATE, new MachineSource(this));
                //currMana -= manacost;

                //if (!getProxy().getCrafting().isRequesting(AEApi.instance().storage().createItemStack(output))){
                    for (int i = 0; i < inputs.length; i++) {
                        inputs[i] = null;
                        output = null;
                    }
                    //return;
                //}



            } catch (GridAccessException e) {
                //
            }
            finally {
                isCrafting = false;
            }

        }

    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        if (iCraftingPatternDetails instanceof RuneAssemblerCraftingPattern  && !isCrafting/* && inputs[0] == null*/){
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

    @Override
    public void recieveMana(int i) {
        if (isFull()) return;
        currMana = currMana + i < maxMana ? currMana + i : maxMana;
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return !isFull();
    }

    @Override
    public int getCurrentMana() {
        return currMana;
    }

    @Override
    public boolean isFull() {
        return currMana == maxMana;
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
