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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/container/coal_generator.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String teName = generator.getDisplayName().getUnformattedText();
        fontRenderer.drawString(teName, 8, 6, 0x040404);
        fontRenderer.drawString(inventory.getDisplayName().getUnformattedText(), 8, 72, 0x040404);

//        this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/container/coal_generator.png"));
//        this.drawTexturedModalRect(84, 26, 180, 176, 14, 38 - getProgressLevel(38));
//
//        if(mouseX >= 84 && mouseX <= 119 && mouseY >= 26 && mouseY <= 63) {
//            List<String> text = new ArrayList<>();
//            text.add(generator.getField(0) + "RF / " + generator.getField(1) + "RF");
//            this.drawHoveringText(text, mouseX, mouseY);
//        }
    }

//    private int getProgressLevel(int progressIndicatorPixelHeight) {
//        int rf = generator.getField(0);
//        int maxRF = generator.getField(1);
//        return maxRF != 0 && rf != 0 ? (rf * progressIndicatorPixelHeight) / maxRF : 0;
//    }
}
