package pct.botanic.energistics.blocks.tile;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;






import java.util.Random;

import appeng.api.implementations.parts.IPartCable;
import appeng.api.networking.IGridNode;
import appeng.api.parts.BusSupport;
import appeng.api.parts.IPartCollisionHelper;
import appeng.api.parts.IPartHost;
import appeng.api.parts.IPartRenderHelper;
import appeng.api.parts.PartItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import pct.botanic.energistics.api.block.vine.I_VineDirection;
import pct.botanic.energistics.api.block.vine.VineHelper;
import pct.botanic.energistics.blocks.vine.VineDirection;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileDataVine extends TileEntity implements IPartCable {


	private LinkedList<I_VineDirection> connections;

	public TileDataVine()
	{

		connections=new LinkedList<I_VineDirection>();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		byte[] connectionsArr = new byte[connections.size()];
		int i =0;
		for (I_VineDirection connection: connections) {
			connectionsArr[i] = connection.toByte();
			i++;
		}
		nbtTag.setByteArray("connections", connectionsArr);



		super.writeToNBT(nbtTag);
	};

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		byte[] connectionsArr = nbtTag.getByteArray("connections");
		for (byte b : connectionsArr) {
			I_VineDirection temp = new VineDirection(b);
			connections.add(temp);
		}






		super.readFromNBT(nbtTag);
	}

	//Fixed by Whov
	@Override
	public Packet getDescriptionPacket() {
		Packet packet = super.getDescriptionPacket();
    	NBTTagCompound nbtTag = packet != null ? ((S35PacketUpdateTileEntity)packet).func_148857_g() : new NBTTagCompound();
        writeToNBT(nbtTag);
		//TODO: Get this damn working!
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}
	//Fixed by Whov
	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(networkManager, packet);
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	/*
	//Left in in case Magnus's evil idea gets done
	private I_VineDirection randDir(byte initDirection) {
		I_VineDirection origin = new VineDirection(initDirection);
		origin.invert();
		I_PipeDirection newDirection = origin;
		int attempt = 0;
		while (!connections.isEmpty()&&attempt<26&&newDirection.equals(origin))
		{
			attempt++;
			int randNum=(int)(Math.random()*connections.size());
			newDirection  = connections.get(randNum);
		}
		return newDirection;
	}
	*/
	
	

	@Override
	public boolean canUpdate()
	{
		return true;
	}


	public List<I_VineDirection> getConnections() {
		//System.out.println("Getting "+(worldObj.isRemote?"Client":"Server")+" connections");
		return connections;
	}

	public String toJSON() {
		String result = "\n{";
		result+=("\n\t\"tileX\":\""+xCoord+"\",");
		result+=("\n\t\"tileY\":\""+yCoord+"\",");
		result+=("\n\t\"tileZ\":\""+zCoord+"\",");
		result+=("\n\t\"connections\":[\n");
		for (I_VineDirection pipe : connections) {
			result+=(pipe.toJSON()+",\n");
		}
		result+="]";
		result+="\n}\n";
		
		return result;
	}

		public boolean checkConnections() {
		int[] origin = {xCoord,yCoord,zCoord};
		return VineHelper.checkConnections(worldObj, origin);
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
	public void writeToStream(ByteBuf arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

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
	public boolean changeColor(AEColor arg0, EntityPlayer arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AEColor getCableColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AECableType getCableConnectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected(ForgeDirection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValidSides(EnumSet<ForgeDirection> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BusSupport supportsBuses() {
		// TODO Auto-generated method stub
		return null;
	}
}