package pct.botanic.energistics.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.api.block.vine.VineHelper;
import pct.botanic.energistics.blocks.tile.TileDataVine;

public class DataVine extends BlockContainer{

	public DataVine() {
		super(Material.gourd);
		setBlockName("Data Vine");
        setCreativeTab(BotanicEnergistics.botanicEnergisticsTab);;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metaD,  float hitX, float hitY, float hitZ)
	{

		
		System.out.println("This pipe has "+((TileDataVine)world.getTileEntity(x, y, z)).getConnections().size()+" connections.");
		//System.out.println(((TileEntityFluxCable)world.getTileEntity(x, y, z)).toJSON());
		return false;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		int[] origin = {x, y ,z,};
		VineHelper.checkConnections(world, origin);
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		TileEntity cable = new TileDataVine();
		return cable;
	}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metaD) {
		int[] origin = {x, y ,z,};
		VineHelper.breakAllConnections(world, origin);
		super.breakBlock(world, x, y, z, block, metaD);
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int metaD) {
		int[] origin = {x, y ,z,};
		VineHelper.breakAllConnections(world, origin);
		super.onBlockPreDestroy(world, x, y, z,	metaD);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	};

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	};

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return false;
	};

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}



}
