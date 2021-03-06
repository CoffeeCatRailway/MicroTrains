package coffeecatteam.microtrains.util;

import coffeecatteam.microtrains.gui.GuiController;
import coffeecatteam.microtrains.gui.container.ContainerController;
import coffeecatteam.microtrains.objects.tileentity.TileController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int CONTROLLER_GUI_ID = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case CONTROLLER_GUI_ID:
                return new ContainerController(player.inventory, (TileController) te);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case CONTROLLER_GUI_ID:
                return new GuiController(player.inventory, (TileController) te);
            default:
                return null;
        }
    }
}
