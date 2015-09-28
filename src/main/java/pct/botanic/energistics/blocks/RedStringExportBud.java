package pct.botanic.energistics.blocks;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import vazkii.botania.common.block.tile.string.TileRedString;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.parts.BusSupport;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartCollisionHelper;
import appeng.api.parts.IPartHost;
import appeng.api.parts.IPartRenderHelper;
import appeng.api.parts.PartItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;

public class RedStringExportBud extends TileRedString implements IPart, IGridProxyable, IActionHost {

	@Override
	public void getBoxes(IPartCollisionHelper arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGridNode getGridNode(ForgeDirection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void securityBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGridNode getActionableNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DimensionalCoord getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AENetworkProxy getProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gridChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int cableConnectionRenderTo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canBePlacedOn(BusSupport arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canConnectRedstone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IIcon getBreakingTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDrops(List<ItemStack> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGridNode getExternalFacingNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGridNode getGridNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemStack(PartItemStack arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLightLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isLadder(EntityLivingBase arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isProvidingStrongPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isProvidingWeakPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActivate(EntityPlayer arg0, Vec3 arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onEntityCollision(Entity arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNeighborChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlacement(EntityPlayer arg0, ItemStack arg1,
			ForgeDirection arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onShiftActivate(EntityPlayer arg0, Vec3 arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void randomDisplayTick(World arg0, int arg1, int arg2, int arg3,
			Random arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean readFromStream(ByteBuf arg0) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeFromWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderDynamic(double arg0, double arg1, double arg2,
			IPartRenderHelper arg3, RenderBlocks arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderInventory(IPartRenderHelper arg0, RenderBlocks arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderStatic(int arg0, int arg1, int arg2,
			IPartRenderHelper arg3, RenderBlocks arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requireDynamicRender() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPartHostInfo(ForgeDirection arg0, IPartHost arg1,
			TileEntity arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToNBT(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToStream(ByteBuf arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean acceptBlock(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
