package pct.botanic.energistics.blocks.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.references.CoreRefs;

/**
 * Created by beepbeat on 08.10.2015.
 */
public class TESRRunicAssembler extends TileEntitySpecialRenderer{

    public TESRRunicAssembler() {
      //  this.bindTexture(new ResourceLocation(CoreRefs.MODID, "textures/blocks/runeassembler.png"));
    }

    @Override
    public void renderTileEntityAt(TileEntity par1, double x, double y, double z, float f) {
        TileAERuneAssembler te = (TileAERuneAssembler) par1;
        Tessellator tess = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 2, z);
        tess.addTranslation(0, 2, 0);
        tess.startDrawingQuads();



        tess.addVertexWithUV(1, 1, 1, 1, 1);
        tess.addVertexWithUV(0, 1, 1, 1, 1);
        tess.addVertexWithUV(1, 0, 1, 1, 1);
        tess.addVertexWithUV(1, 1, 0, 1, 1);
        tess.addVertexWithUV(1, 1, 1, 0, 1);
        tess.addVertexWithUV(1, 1, 1, 1, 0);
        tess.addVertexWithUV(0, 0, 0, 0, 0);

        GL11.glColor3d(255, 255, 0);
        //GL11.glDrawBuffer(GL11.GL_DRAW_BUFFER);

        tess.setBrightness(5);

        tess.draw();
        tess.addTranslation(0, -2, 0);
        GL11.glPopMatrix();
    }
}
