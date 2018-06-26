package coffeecatteam.microtrains.objects.tileentity;

import net.minecraftforge.energy.EnergyStorage;

public class CGEnergyStorage extends EnergyStorage {

    public CGEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public int getMaxExtract() {
        return this.maxExtract;
    }

    public int getCapacity() {
        return this.getMaxEnergyStored();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setEnergy(int newEnergy) {
        this.energy = newEnergy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }
}
