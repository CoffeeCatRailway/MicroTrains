package coffeecatteam.microtrains.objects.entity;

import coffeecatteam.microtrains.init.InitItem;
import coffeecatteam.microtrains.objects.items.ItemLocomotive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLocomotive extends EntityThrowable {

    public Item loco = InitItem.LOCO_DIESELS[0];

    public EntityLocomotive(World world) {
        super(world);
    }

    public EntityLocomotive(World world, EntityPlayer playerEntity) {
        super(world, playerEntity);
    }

    public EntityLocomotive(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityLocomotive setLoco(Item loco) {
        this.loco = loco;
        return this;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.05f;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.setDead();

            if (result.entityHit instanceof EntityItemFrame) {
                EntityItemFrame frame = (EntityItemFrame) result.entityHit;
                if (frame.getDisplayedItem() == ItemStack.EMPTY)
                    frame.setDisplayedItem(new ItemStack(this.loco));
                else
                    if (frame.getDisplayedItem().getItem() instanceof ItemLocomotive) {
                        Item frameItem = frame.getDisplayedItem().getItem();
                        frame.setDisplayedItem(new ItemStack(this.loco));
                        doSpawnItem(frameItem);
                    } else
                        doSpawnItem(this.loco);
            } else {
                doSpawnItem(this.loco);
            }
        }
    }

    private void doSpawnItem(Item item) {
        EntityItem entityItem = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(item));
        this.world.spawnEntity(entityItem);
    }
}
