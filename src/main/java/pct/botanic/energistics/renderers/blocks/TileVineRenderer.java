package pct.botanic.energistics.renderers.blocks;

import java.util.List;

import org.lwjgl.opengl.GL11;

import pct.botanic.energistics.BotanicEnergistics;
import pct.botanic.energistics.api.block.vine.I_VineDirection;
import pct.botanic.energistics.references.CoreRefs;
import pct.botanic.energistics.blocks.tile.TileDataVine;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * 
 * @author Minothor, Flenix
 *Renders the Vines.
 *Adapted from Flenix's Tutorial at:
 *http://www.minecraftforge.net/wiki/Rendering_a_Techne_Model_as_a_Block
 */

//TODO: Cancel rendering if not visible
//Current Idea: Player.yaw/pitch trigonometry && sky light
public class TileVineRenderer extends TileEntitySpecialRenderer {
	private final ResourceLocation texture;
	private IModelCustom vineModel;
	
	public static int blockRenderId;
	public static final float pixel = 1.0F/16; 
	public Tessellator tessellator;
	
	public TileVineRenderer() {
		tessellator = Tessellator.instance;
		blockRenderId = RenderingRegistry.getNextAvailableRenderId();
		texture = new ResourceLocation(CoreRefs.MODID,"/textures/blocks/DataVine.png");
		vineModel = AdvancedModelLoader.loadModel(new ResourceLocation(CoreRefs.MODID,"/models/DataVine.obj"));
	}



	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float scale) {
		
		
		//binding the textures
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GL11.glPushMatrix();
		//initial location (what's up with the 0.5 and 1.5 difference I wonder)
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		Boolean hubDrawn = false,endDrawn=false;
		List<I_VineDirection> connections = ((TileDataVine)entity).getConnections();
        switch(connections.size()){
        case 1: endDrawn = true;
        case 2: hubDrawn = checkOpposing(connections); break;
        default: hubDrawn = true;
        }
        if(hubDrawn){
        	drawHub();
        }
         
         //let LWGL know we're doing more matixy manipulation stuff
         //rotate to avoid model rendering upside down
         for (I_VineDirection connection : connections) {
        	 int[] target = connection.getTarget();
        	 float[] angles = {target[0]*90,target[1]*90,target[2]*90};
        	 
        	 
        	 drawConnector(angles);
		}
        
         //pop both sections off the render stack
         
         GL11.glPopMatrix();
         //System.out.println("FOV : "+Minecraft.getMinecraft().gameSettings.fovSetting);
         GL11.glEnable(GL11.GL_CULL_FACE);
	}
	 private Boolean checkOpposing(List<I_VineDirection> connections) {
		int[] result = {0,0,0};
		for (I_VineDirection connection: connections) {
			int[] target = connection.getTarget();
			result[0]+=target[0];
			result[1]+=target[1];
			result[2]+=target[2];
		}
		return (result[0]==0&&result[1]==0&&result[2]==0);
	}



	private void drawHub() {
		
		vineModel.renderPart("VineCore");
		
	}

	 private void drawCore(float size)
	 {
		
	 }
	 
	 
	private void drawConnector(float[] angle) {

		GL11.glRotatef(angle[0], 1,0,0);
		//GL11.glRotatef(angle[1], 0,1,0);//
		GL11.glRotatef(-angle[2], 1,0,0);
		vineModel.renderPart("VineDiagHor");
		GL11.glRotatef(angle[2], 1,0,0);
		//GL11.glRotatef(-angle[1], 0,1,0);//
		GL11.glRotatef(-angle[0], 1,0,0);
		
		/*
		//Horizontal-Z
		GL11.glRotatef(angle[0], 0,1,0);
		GL11.glRotatef(angle[1], 0,1,0);
		GL11.glRotatef(angle[2], 0,1,0);
		vineModel.renderPart("VineBasic");
		GL11.glRotatef(-angle[2], 0,1,0);
		GL11.glRotatef(-angle[1], 0,1,0);
		GL11.glRotatef(-angle[0], 0,1,0);
		 */
		
		/*
		//Horizontal-X
		GL11.glRotatef(angle[0], 0, 1, 0);
		GL11.glRotatef(angle[1], 0,1, 0);
		vineModel.renderPart("VineBasic");
		GL11.glRotatef(-angle[1], 0, 1, 0);
		GL11.glRotatef(-angle[0], 0, 1, 0);
		 */
		
		/*
		//Vertical
		GL11.glRotatef(-angle[0], 1, 0, 0);
		GL11.glRotatef(-angle[1], 0,1, 0);
		vineModel.renderPart("VineBasic");
		GL11.glRotatef(angle[1], 0, 1, 0);
		GL11.glRotatef(angle[0], 1, 0, 0);
		 */
}

	private void adjustLightFixture(World world, int x, int y, int z, Block block) {
         
         float brightness = block.getLightValue(world, x, y, z);
         int skyLight = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
         int modulousModifier = skyLight % 65536;
         int divModifier = skyLight / 65536;
         tessellator.setColorOpaque_F(brightness, brightness, brightness);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  modulousModifier,  divModifier);
 }

}