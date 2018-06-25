package coffeecatteam.microtrains.objects.tileentity;

import net.minecraftforge.energy.EnergyStorage;

import java.util.Random;

public class CGEnergyStorage extends EnergyStorage {

    public CGEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public int getCapacity() {
        return this.getMaxEnergyStored();
    }

    public void fillEnergy() {
        this.energy = 1000000;
    }

    public void loseEnergy(Random random) {
        this.energy -= random.nextInt(500) + 500;
        if(this.energy < 0)
            this.energy = 0;
    }

    public void setEnergy(int newEnergy) {
        this.energy = newEnergy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }
}
