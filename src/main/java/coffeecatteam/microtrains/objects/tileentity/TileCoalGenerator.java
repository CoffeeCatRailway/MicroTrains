package coffeecatteam.microtrains.objects.tileentity;

import coffeecatteam.microtrains.init.InitBlock;
import cofh.core.energy.FurnaceFuelHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileCoalGenerator extends TileEntity implements IInventory, IEnergyProvider, ITickable {

    private int increasePerTick = 20;

    private int capacity = 10000;
    private int maxExtract = 50;
    private int currentRF;
    private int cooldown = 100;

    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

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
    public int getField(int id) {
        switch(id) {
            case 0:
                return this.currentRF;
            case 1:
                return this.capacity;
            case 2:
                return this.cooldown;
            case 3:
                return this.increasePerTick;
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.currentRF = value;
            case 1:
                this.capacity = value;
            case 2:
                this.cooldown = value;
            case 3:
                this.increasePerTick = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
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
        compound.setInteger("currentRF", this.currentRF);
        compound.setInteger("cooldown", this.cooldown);
        compound.setInteger("ipt", this.increasePerTick);

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
        this.currentRF = compound.getInteger("currentRF");
        this.cooldown = compound.getInteger("cooldown");
        this.increasePerTick = compound.getInteger("ipt");

        super.readFromNBT(compound);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return this.currentRF;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return this.capacity;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true; // (from == EnumFacing.EAST || from == EnumFacing.SOUTH || from == EnumFacing.WEST)
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        currentRF -= maxExtract;
        return maxExtract;
    }

    @Override
    public void update() {
        if(this.world != null) {
            ItemStack stack = this.inventory.get(0);
            if(canUse(stack)) {
                if(this.cooldown <= 0) {
                    this.inventory.get(0).setCount(this.inventory.get(0).getCount() - 1);
                    if(this.inventory.get(0).getCount() == 0) {
                        this.inventory.set(0, ItemStack.EMPTY);
                    }
                }
            }
            if(this.cooldown > 0) {
                this.cooldown--;
                if(this.currentRF < this.capacity)
                    this.currentRF += this.increasePerTick;
            }
            this.markDirty();
        }
    }

    private boolean canUse(ItemStack stack) {
        if(this.inventory.get(0) == null) {
            return false;
        }
        else {
            String name = I18n.format(stack.getUnlocalizedName() + ".name").toLowerCase();
            if(name.contains("coal") && TileEntityFurnace.isItemFuel(stack)) {
                if(this.currentRF < this.capacity)
                    return true;
            }
        }
        return false;
    }
}
