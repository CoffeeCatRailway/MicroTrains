package coffeecatteam.microtrains.objects.blocks;

import coffeecatteam.microtrains.objects.blocks.base.BlockBaseFacingContainer;
import coffeecatteam.microtrains.objects.tileentity.TileController;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockController extends BlockBaseFacingContainer {

    public BlockController(String name) {
        super(name, Material.IRON, 1.0F, 1.5F);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileController();
    }

    @Override
    public boolean onBlockActivatedAb(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }

    @Override
    public void breakBlockAb(World world, BlockPos pos, IBlockState state) {
    }
}
