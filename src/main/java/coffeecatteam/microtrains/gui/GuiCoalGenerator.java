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

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiCoalGenerator extends GuiContainer {

    private IInventory inventory;
    private TileCoalGenerator generator;

    private ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/container/coal_generator.png");

    public GuiCoalGenerator(IInventory inventory, TileCoalGenerator generator) {
        super(new ContainerCoalGenerator(inventory, generator));
        this.inventory = inventory;
        this.generator = generator;

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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String teName = this.generator.getDisplayName().getUnformattedText();
        fontRenderer.drawString(teName, 8, 6, 0x040404);
        fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, 72, 0x040404);

        if(mouseX >= this.guiLeft + 84 && mouseX <= this.guiLeft + 119 && mouseY >= this.guiTop + 26 && mouseY <= this.guiTop + 63) {
            List<String> text = new ArrayList<>();
            text.add(this.generator.getField(1) + "RF / " + this.generator.getField(2) + "RF");
            this.drawHoveringText(text, mouseX - this.guiLeft, mouseY - this.guiTop);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if (TileCoalGenerator.isBurning(this.generator)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(this.guiLeft + 52, this.guiTop + 40 - k, 176, 12 - k, 14, k + 1);
        }

        //this.drawTexturedModalRect(this.guiLeft + 84, this.guiTop + 26, 180, 176, 14, 38);
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.generator.getField(0);
        if (i == 0)
            i = 100;
        return 100 * pixels / i;
    }

    private int getProgressLevel(int progressIndicatorPixelHeight) {
        int rf = this.generator.getField(0);
        int maxRF = this.generator.getField(1);
        return maxRF != 0 && rf != 0 ? (rf * progressIndicatorPixelHeight) / maxRF : 0;
    }
}
