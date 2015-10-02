package pct.botanic.energistics.blocks;

import appeng.api.AEApi;
import appeng.api.networking.IGridNode;
import net.minecraft.util.ChatComponentText;
import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.gui.GUIHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.utilities.LexionEntryHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.page.PageText;


public class AERuneAssembler extends BlockContainer implements ILexiconable{


    public AERuneAssembler() {
        super(Material.iron);
        setBlockName("AE Rune Assembler");
        setCreativeTab(BotanicEnergistics.botanicEnergisticsTab);

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
        return new TileAERuneAssembler();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        if (!world.isRemote){
            player.openGui(BotanicEnergistics.instance, GUIHandler.GUIIDs.RuneAssembler.ordinal(), world, x, y, z);
        }
        return true;
    }

    @Override
    public LexiconEntry getEntry(World world, int i, int i1, int i2, EntityPlayer entityPlayer, ItemStack itemStack) {
        LexiconEntry entry = new LexionEntryHelper.runicKnowledgeEntry("entry.runeAssembler", BotaniaAPI.categoryDevices);
        //entry.addPage(LexionEntryHelper.LexiconPageCreator.createTextPage("page.runeassembler", 10, 10, 10, 12, "THIS IS A TEST TEXT!!!"));
        entry.addPage(new PageText("page.runeassembler"));
        return entry;
    }

    public LexiconEntry getEntry() {
        LexiconEntry entry = new LexionEntryHelper.elvenKnowledgeEntry("entry.runeAssembler", BotaniaAPI.categoryDevices);
        entry.addPage(LexionEntryHelper.LexiconPageCreator.createTextPage("page.runeassembler", 10, 10, 10, 12, "THIS IS A TEST TEXT!!!"));
        return entry;
    }
}
