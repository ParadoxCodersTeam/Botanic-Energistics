package pct.botanic.energistics.gui;

import pct.botanic.energistics.blocks.tile.TileAERuneAssembler;
import pct.botanic.energistics.blocks.tile.TileGeneric;
import pct.botanic.energistics.gui.container.ContainerRuneAssembler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by beepbeat on 22.08.2015.
 */
public class GUIHandler implements IGuiHandler {
    public enum GUIIDs{RuneAssembler, ElvenPortal}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GUIIDs.values()[ID]){
            case RuneAssembler:
                return new ContainerRuneAssembler(player.inventory, (TileGeneric) world.getTileEntity(x, y, z));
            case ElvenPortal:
                return new ContainerRuneAssembler(player.inventory, (TileGeneric) world.getTileEntity(x, y, z));
        }

        throw new IllegalArgumentException("No Container with ID " + ID);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GUIIDs.values()[ID]){
            case RuneAssembler:
                return new GUIRuneAssembler(player.inventory, (TileGeneric) world.getTileEntity(x, y, z));
            case ElvenPortal:
                return new GUIRuneAssembler(player.inventory, (TileGeneric) world.getTileEntity(x, y, z));
        }
        throw new IllegalArgumentException("No GUI with ID " + ID);
    }
}
