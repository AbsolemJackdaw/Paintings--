package subaraki.paintings.gui;

import static com.mojang.blaze3d.platform.GlStateManager.color4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;

public class PaintingButton extends Button {

    private static final int BORDER = 3;
    private static final int YELLOW = -256;

    ResourceLocation resLoc;

    public PaintingButton(int x, int y, int w, int h, String text, IPressable onPress, PaintingType pt) {

        super(x, y, w, h, text, onPress);
        String combo = pt.getRegistryName().getNamespace() + ":textures/painting/" + pt.getRegistryName().getPath() + ".png";
        this.resLoc = new ResourceLocation(combo);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float no_clue) {

        Minecraft.getInstance().getTextureManager().bindTexture(resLoc);

        blit(this.x, this.y, 0, 0, width, height, width, height);

        if (isHovered) {
            fill(x - BORDER, y - BORDER, x + width + BORDER, y, YELLOW); // upper left to upper right
            fill(x - BORDER, y + height, x + width + BORDER, y + height + BORDER, YELLOW); // lower left to lower right
            fill(x - BORDER, y, x, y + height, YELLOW); // middle rectangle to the left
            fill(x + width, y, x + width + BORDER, y + height, YELLOW); // middle rectangle to the right

            color4f(1, 1, 1, 1);
        }

    }

    public void shiftY(int dy) {
        this.y += dy;
    }

    public void shiftX(int dx) {
        this.x += dx;
    }
}
