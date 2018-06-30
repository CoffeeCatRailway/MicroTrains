package coffeecatteam.microtrains.gui;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.gui.container.ContainerController;
import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import coffeecatteam.microtrains.objects.tileentity.TileController;
import coffeecatteam.microtrains.util.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiController extends GuiContainer {

    private IInventory inventory;
    private TileController controller;

    private ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/container/controller.png");

    public GuiController(IInventory inventory, TileController controller) {
        super(new ContainerController(inventory, controller));
        this.inventory = inventory;
        this.controller = controller;

        this.xSize = 176;
        this.ySize = 180;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String teName = this.controller.getDisplayName().getUnformattedText();
        fontRenderer.drawString(teName, 8, 6, 0x040404);
        fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, 86, 0x040404);

        if(mouseX >= this.guiLeft + 133 && mouseX <= this.guiLeft + 168 && mouseY >= this.guiTop + 16 && mouseY <= this.guiTop + 83) {
            List<String> text = new ArrayList<>();
            text.add(this.controller.getField(0) + "RF / " + this.controller.getField(1) + "RF");
            this.drawHoveringText(text, mouseX - this.guiLeft, mouseY - this.guiTop);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int l = Utils.getEnergyReading(67, this.controller.getField(0), this.controller.getField(1));
        this.drawTexturedModalRect(this.guiLeft + 133, this.guiTop + 83 - l, 176, 67 - l, 36, l + 1);
    }
}
