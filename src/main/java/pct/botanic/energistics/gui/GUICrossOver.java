package pct.botanic.energistics.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import pct.botanic.energistics.references.CoreRefs;

/**
 * Created by beepbeat on 22.08.2015.
 */

public class GUICrossOver extends GuiContainer {
    private final ResourceLocation guiTexture;
    public GUICrossOver(Container container, String guiTextureName, IInventory inventory) {
        super(container);
        guiTexture = new ResourceLocation(CoreRefs.MODID + ":gui/" + guiTextureName + ".png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
