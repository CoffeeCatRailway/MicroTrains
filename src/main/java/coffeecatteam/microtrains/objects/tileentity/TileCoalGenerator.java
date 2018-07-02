package coffeecatteam.microtrains.objects.tileentity;

import coffeecatteam.microtrains.init.InitBlock;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileCoalGenerator extends TileEntity implements IInventory, ITickable {

    private CGEnergyStorage energyStorage;

    private int maxCooldown = 100;
    private int cooldown = maxCooldown;

    private boolean burn;

    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    public TileCoalGenerator() {
        energyStorage = new CGEnergyStorage(100000, 0, 50, 0);
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
        return GameRegistry.getFuelValue(stack) > 0;
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
        compound.setBoolean("burn", this.burn);

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
        this.burn = compound.getBoolean("burn");

        super.readFromNBT(compound);
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.cooldown;
            case 1:
                return this.maxCooldown;
            case 2:
                return this.energyStorage.getEnergyStored();
            case 3:
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
                this.maxCooldown = value;
                break;
            case 2:
                this.energyStorage.setEnergy(value);;
                break;
            case 3:
                this.energyStorage.setCapacity(value);
                break;
        }
        this.markDirty();
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void update() {
        if(this.world != null) {
            // Check if stored energy is greater then max capacity
            if (this.energyStorage.isFull()) {
                this.energyStorage.setEnergy(this.energyStorage.getCapacity());
            } else {
                ItemStack stack = this.inventory.get(0);

                // Check if stack is empty
                if (!stack.isEmpty())
                    this.burn = true;

                // Cooldown
                if (burn) {
                    this.cooldown--;

                    // Check if cooldown is done
                    if (this.cooldown <= 0) {
                        this.cooldown = maxCooldown;
                        stack.shrink(1);
                        this.burn = false;
                    }
                }

                // Add energy to storage if burning
                if (isBurning() && this.burn)
                    this.energyStorage.addEnergy(getEnergyFromCoal(stack) / 10);
            }

            // Output energy if stored energy is greater then 0
            if (this.energyStorage.getEnergyStored() > 0)
                outputEnergy();

            this.markDirty();
        }
    }

    public boolean isBurning() {
        return this.cooldown > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
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
