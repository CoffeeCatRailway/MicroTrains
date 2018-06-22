package coffeecatteam.microtrains.gui;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.gui.container.ContainerCoalGenerator;
import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends GuiContainer {

    private IInventory inventory;
    private TileCoalGenerator tileentity;

    public GuiCoalGenerator(IInventory inventory, TileCoalGenerator tileentity) {
        super(new ContainerCoalGenerator(inventory, tileentity));
        this.inventory = inventory;
        this.tileentity = tileentity;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/container/coal_generator.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String teName = tileentity.getDisplayName().getUnformattedText();
        fontRenderer.drawString(teName, 8, 6, 0x040404);
        fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, 72, 0x040404);
    }
}
