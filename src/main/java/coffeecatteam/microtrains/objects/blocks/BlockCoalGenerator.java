package coffeecatteam.microtrains.objects.blocks;

import coffeecatteam.microtrains.MicroTrains;
import coffeecatteam.microtrains.objects.blocks.base.BlockBaseFacing;
import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCoalGenerator extends BlockBaseFacing {

    public BlockCoalGenerator(String name) {
        super(name, 1.0F, 1.5F, Material.IRON, MicroTrains.MICROTAB);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCoalGenerator();
    }
}
