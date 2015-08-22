package pct.botanic.energistics.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Created by beepbeat on 22.08.2015.
 */
public abstract class ContainerCrossoverMod extends Container {


    protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y){
        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y + 58));
        }
    }
}