package pct.botanic.energistics.blocks;

import appeng.api.AEApi;
import appeng.api.networking.IGridNode;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.blocks.tile.TileAEElvenPortal;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.gui.GUIHandler;
import pct.botanic.energistics.references.CoreRefs;
import pct.botanic.energistics.utilities.LexionEntryHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageElvenRecipe;
import vazkii.botania.common.lexicon.page.PageText;

/**
 * Created by beepbeat on 06.12.2015.
 */

public class AEElvenPortal extends BlockContainer implements ILexiconable {

    IIcon[] icons;

    public AEElvenPortal() {
        super(Material.iron);
        setBlockName("elvenportal");
        setCreativeTab(BotanicEnergistics.botanicEnergisticsTab);
        setBlockTextureName(CoreRefs.MODID + ":elvenportal");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if (world.isRemote) {
            return;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileAERuneAssembler) {
            TileAERuneAssembler tileC = (TileAERuneAssembler) tile;
            tileC.getProxy().onReady();
            IGridNode node = tileC.getGridNode(ForgeDirection.UNKNOWN);
            if (entity != null && entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                node.setPlayerID(AEApi.instance().registries().players().getID(player));
            }
            node.updateState();
        }
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        if (world.isRemote)
            return;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileAERuneAssembler) {
                IGridNode node = ((TileAERuneAssembler) tile).getGridNode(ForgeDirection.UNKNOWN);
                if (node != null) {
                    node.destroy();
                }
            }
        }
    }


    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileAEElvenPortal();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        if (!world.isRemote) {
            player.openGui(BotanicEnergistics.instance, GUIHandler.GUIIDs.RuneAssembler.ordinal(), world, x, y, z);
        }
        return true;
    }

    @Override
    public LexiconEntry getEntry(World world, int i, int i1, int i2, EntityPlayer entityPlayer, ItemStack itemStack) {
        return getEntry();
    }

    public LexiconEntry getEntry() {
        LexiconEntry entry = new LexionEntryHelper.elvenKnowledgeEntry("entry.elvenportal", BotaniaAPI.categoryDevices, new ItemStack(this));
        //entry.addPage(LexionEntryHelper.LexiconPageCreator.createTextPage("page.elvenportal", 10, 10, 10, 12, "THIS IS A TEST TEXT!!!"));
        //entry.addPage(new PageText("This is a test text page"));
        entry.addPage(new PageText("page1.elvenportal"));
        entry.addPage(new PageElvenRecipe("pageRecipe.elvenportal", BotanicEnergisticsBlocks.RecRuneAssembler));
        return entry;
    }
}
