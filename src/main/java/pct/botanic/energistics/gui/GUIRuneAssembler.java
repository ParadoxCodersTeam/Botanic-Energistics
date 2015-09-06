package pct.botanic.energistics.gui;

import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.gui.container.ContainerRuneAssembler;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.logging.LogManager;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class GUIRuneAssembler extends GUICrossOver {
    TileAERuneAssembler te;

    public GUIRuneAssembler(InventoryPlayer playerInventory, TileAERuneAssembler te) {
        super(new ContainerRuneAssembler(playerInventory, te), "RuneAssembler", playerInventory);
        this.te = te;

    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString("Mana: " + String.valueOf(te.getCurrentMana()), 10, 5, 0xFF444444);
    }

}
