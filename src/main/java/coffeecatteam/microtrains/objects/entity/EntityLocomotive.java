package coffeecatteam.microtrains.objects.entity;

import coffeecatteam.microtrains.init.InitItem;
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

    public Item getLoco() {
        return this.loco;
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
                EntityItem entityItem = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(loco));
                this.world.spawnEntity(entityItem);
            }
        }
    }
}
