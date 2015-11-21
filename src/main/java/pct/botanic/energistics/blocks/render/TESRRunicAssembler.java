package pct.botanic.energistics.blocks.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.references.CoreRefs;
import vazkii.botania.client.model.ModelSpinningCubes;

public class TESRRunicAssembler extends TileEntitySpecialRenderer{
    ModelSpinningCubes cubes = new ModelSpinningCubes();
    RenderBlocks renderBlocks = new RenderBlocks();
    public TESRRunicAssembler() {
      //  this.bindTexture(new ResourceLocation(CoreRefs.MODID, "textures/blocks/runeassembler.png"));
    }

    @Override
    public void renderTileEntityAt(TileEntity par1, double x, double y, double z, float f) {
        TileAERuneAssembler te = (TileAERuneAssembler) par1;
        Tessellator tess = Tessellator.instance;
/*
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 2, z);
        tess.addTranslation(0, 2, 0);
        tess.startDrawingQuads();
        tess.setColorRGBA(1, 1, 1, 1);

        tess.addVertexWithUV(0, 0, 0, 1, 1); //Origin
        tess.addVertexWithUV(0, 1, 0, 1, 1); //1 unit up
        tess.addVertex(1, 1, 0); //1 unit up and to the right
        tess.addVertex(1, 0, 0); //1 unit to the right

        GL11.glColor3d(255, 255, 0);
        tess.setBrightness(5);

        tess.draw();
        tess.addTranslation(0, -2, 0);
        GL11.glPopMatrix();*/

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glDisable(3008);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 1.8F + 0.2F, 0.5F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
        byte var28 = 15;
        this.cubes.renderSpinningCubes(2, var28, var28);
        this.renderBlocks.overrideBlockBounds(0, 0, 0, 1, 0.5, 1);
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glPopMatrix();
    }
}
