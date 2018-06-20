package coffeecatteam.microtrains.objects.tileentity;

import coffeecatteam.microtrains.MicroTrains;
import coffeecatteam.microtrains.Reference;
import cofh.core.block.TileInventory;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileCoalGenerator extends TileInventory implements ITickable, IEnergyProvider {

    public EnergyStorage energyStorage;

    public TileCoalGenerator() {
        energyStorage = new EnergyStorage(1000, 100, 100);
    }

    @Override
    protected Object getMod() {
        return MicroTrains.instance;
    }

    @Override
    protected String getModVersion() {
        return Reference.VERSION;
    }

    @Override
    protected String getTileName() {
        return "tile.coal_generator.name";
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void update() {
    energyStorage.modifyEnergyStored(10);
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return 100;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
        return nbt;
    }
}
