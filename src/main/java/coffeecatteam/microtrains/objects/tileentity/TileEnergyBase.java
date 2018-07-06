package coffeecatteam.microtrains.objects.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public abstract class TileEnergyBase extends TileEntity implements IInventory {

    protected CGEnergyStorage energyStorage;
    private int extraFieldCount;

    public TileEnergyBase(int capacity, int maxReceive, int maxExtract, int energy) {
        this(capacity, maxReceive, maxExtract, energy, 0);
    }

    public TileEnergyBase(int capacity, int maxReceive, int maxExtract, int energy, int extraFieldCount) {
        this.energyStorage = new CGEnergyStorage(capacity, maxReceive, maxExtract, energy);
        this.extraFieldCount = extraFieldCount;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.energyStorage.getEnergyStored();
            case 1:
                return this.energyStorage.getMaxEnergyStored();
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.energyStorage.setEnergy(value);
                break;
            case 1:
                this.energyStorage.setCapacity(value);
                break;
        }
        this.markDirty();
    }

    @Override
    public int getFieldCount() {
        return 2 + this.extraFieldCount;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energyStored", this.energyStorage.getEnergyStored());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.energyStorage.setEnergy(compound.getInteger("energyStored"));
        super.readFromNBT(compound);
    }

    protected void outputEnergy() {
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos pos = getPos().offset(facing);
            if (world.isBlockLoaded(pos)) {
                TileEntity tile = world.getTileEntity(pos);

                if (tile != null) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                        IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

                        if (storage != null && storage.getEnergyStored() != storage.getMaxEnergyStored()) {
                            int power = this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), true);
                            int drained = storage.receiveEnergy(power, false);
                            this.energyStorage.extractEnergy(drained, false);
                        }
                    }
                }
            }
        }
    }

    protected void receiveEnergy() {
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos pos = getPos().offset(facing);
            if (world.isBlockLoaded(pos)) {
                TileEntity tile = world.getTileEntity(pos);

                if (tile != null) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                        IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

                        if (storage != null && storage.getEnergyStored() != storage.getMaxEnergyStored()) {
                            int power = this.energyStorage.receiveEnergy(this.energyStorage.getMaxReceive(), true);
                            int drained = storage.extractEnergy(power, false);
                            this.energyStorage.receiveEnergy(drained, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return (T) this.energyStorage;
        return super.getCapability(capability, facing);
    }
}