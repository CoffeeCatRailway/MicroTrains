package coffeecatteam.microtrains.objects.tileentity;

import coffeecatteam.microtrains.init.InitBlock;
import cofh.core.energy.FurnaceFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileCoalGenerator extends TileEntity implements IInventory, ITickable {

    private CGEnergyStorage energyStorage;
    private int maxCooldown = 100;
    private int cooldown = maxCooldown;

    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    public TileCoalGenerator() {
        energyStorage = new CGEnergyStorage(10000, 0, 10, 0);
    }

    @Override
    public int getField(int id) {
        switch (id) {
        case 0:
            return this.cooldown;
        case 1:
            return this.energyStorage.getEnergyStored();
        case 2:
            return this.energyStorage.getMaxEnergyStored();
        default:
            return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
        case 0:
            this.cooldown = value;
            break;
        case 1:
            this.energyStorage.setEnergy(value);;
            break;
        case 2:
            this.energyStorage.setCapacity(value);
            break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public String getName() {
        return InitBlock.COAL_GENERATOR.getUnlocalizedName() + ".name";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName());
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory)
            if (!stack.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= getSizeInventory())
            return ItemStack.EMPTY;
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (getStackInSlot(index) != ItemStack.EMPTY) {
            ItemStack stack;
            if (getStackInSlot(index).getCount() <= count) {
                stack = getStackInSlot(index);
                setInventorySlotContents(index, ItemStack.EMPTY);
                markDirty();
                return stack;
            } else {
                stack = getStackInSlot(index).splitStack(count);
                if (getStackInSlot(index).getCount() <= 0)
                    setInventorySlotContents(index, ItemStack.EMPTY);
                else
                    setInventorySlotContents(index, getStackInSlot(index));
                markDirty();
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= getSizeInventory())
            return;
        if (stack != ItemStack.EMPTY && stack.getCount() > getInventoryStackLimit())
            stack.setCount(getInventoryStackLimit());
        if (stack != ItemStack.EMPTY && stack.getCount() == 0)
            stack = ItemStack.EMPTY;
        inventory.set(index, stack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return world.getTileEntity(getPos()) == this && player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return FurnaceFuelHandler.INSTANCE.getBurnTime(stack) > 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); i++)
            setInventorySlotContents(i, ItemStack.EMPTY);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != ItemStack.EMPTY) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        compound.setTag("Items", list);
        compound.setInteger("cooldown", this.cooldown);
        compound.setInteger("energyStored", this.energyStorage.getEnergyStored());

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            setInventorySlotContents(slot, new ItemStack(stackTag));
        }
        this.cooldown = compound.getInteger("cooldown");
        this.energyStorage.setEnergy(compound.getInteger("energyStored"));

        super.readFromNBT(compound);
    }

    @Override
    public void update() {
        if(this.world != null) {
            ItemStack stack = this.inventory.get(0);
            if (!stack.isEmpty()) {
                cooldown--;
                if (this.isBurning()) {
                    stack.shrink(1);
                    this.energyStorage.addEnergy(getEnergyFromCoal(stack));
                    cooldown = maxCooldown;
                }
            }

            // Check if stored energy is greater then max capacity
            if (this.energyStorage.getEnergyStored() >= this.energyStorage.getCapacity())
                this.energyStorage.setEnergy(this.energyStorage.getCapacity());

            // Output energy if stored energy is greater then 0
            if (this.energyStorage.getEnergyStored() > 0)
                outputEnergy();

            this.markDirty();
        }
    }

    /*
     * DOESN'T WORK YET!
     */
    private int getEnergyFromCoal(ItemStack stack) {
        Item item = stack.getItem();

        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
            Block block = Block.getBlockFromItem(item);

            if (block == Blocks.COAL_BLOCK)
                return 2000;
        }
        if (item == Items.COAL)
            return 200;

        return 100;
    }

    private void outputEnergy() {
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos pos = getPos().offset(facing);
            if (world.isBlockLoaded(pos)) {
                TileEntity tile = world.getTileEntity(pos);

                if (tile != null) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                        IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());

                        if (storage != null) {
                            int power = this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), true);
                            int drained = storage.receiveEnergy(power, false);
                            this.energyStorage.extractEnergy(drained, false);
                        }
                    }
                }
            }
        }
    }

    public CGEnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    public boolean isBurning() {
        return cooldown <= 0;
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
