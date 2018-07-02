package coffeecatteam.microtrains.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonCustom extends GuiButton {

    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffText;

    public GuiButtonCustom(int id, int x, int y, int width, int height, int xTex, ResourceLocation TEXTURES) {
        super(id, x, y, width, height, "");
        this.xTexStart = xTex;
        this.yTexStart = 68;
        this.yDiffText = height;
        this.resourceLocation = TEXTURES;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(this.resourceLocation);
            int i = this.xTexStart;
            int j = this.yTexStart;

            if (this.enabled) {
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.disableDepth();
                if (this.hovered) {
                    j += this.yDiffText;
                }

                this.drawTexturedModalRect(this.x, this.y, i, j, this.width, this.height);
                GlStateManager.enableDepth();
            } else {
                GlStateManager.disableDepth();
                j += this.yDiffText * 2;

                this.drawTexturedModalRect(this.x, this.y, i, j, this.width, this.height);
                GlStateManager.enableDepth();
            }
        }
    }
}
