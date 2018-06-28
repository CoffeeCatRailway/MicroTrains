package coffeecatteam.microtrains.objects.blocks;

import coffeecatteam.microtrains.MicroTrains;
import coffeecatteam.microtrains.objects.blocks.base.BlockBaseFacingContainer;
import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import coffeecatteam.microtrains.util.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCoalGenerator extends BlockBaseFacingContainer {

    public BlockCoalGenerator(String name) {
        super(name, Material.IRON);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCoalGenerator();
    }

    @Override
    public boolean onBlockActivatedAb(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            player.openGui(MicroTrains.instance, GuiHandler.COAL_GENERATOR_GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlockAb(World world, BlockPos pos, IBlockState state) {
        TileCoalGenerator generator = (TileCoalGenerator) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, generator);
    }
}
