package subaraki.paintings.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PaintingButton extends Button {

    private static final int BORDER = 3;
    private static final int YELLOW = -256;

    ResourceLocation resLoc;

    public PaintingButton(int x, int y, int w, int h, ITextComponent text, IPressable onPress, PaintingType pt) {

        super(x, y, w, h, text, onPress);
        String combo = pt.getRegistryName().getNamespace() + ":textures/painting/" + pt.getRegistryName().getPath() + ".png";
        this.resLoc = new ResourceLocation(combo);
    }

    @Override
    public void renderButton(MatrixStack mat, int mouseX, int mouseY, float partial_ticks)
    {

        Minecraft.getInstance().getTextureManager().bindTexture(resLoc);

        blit(mat, this.x, this.y, 0, 0, width, height, width, height);

        if (isHovered)
        {
            fill(mat, x - BORDER, y - BORDER, x + width + BORDER, y, YELLOW); // upper left to upper right
            fill(mat, x - BORDER, y + height, x + width + BORDER, y + height + BORDER, YELLOW); // lower left to lower right
            fill(mat, x - BORDER, y, x, y + height, YELLOW); // middle rectangle to the left
            fill(mat, x + width, y, x + width + BORDER, y + height, YELLOW); // middle rectangle to the right

            GL11.glColor4f(1, 1, 1, 1);
        }

    }

    public void shiftY(int dy)
    {

        this.y += dy;
    }

    public void shiftX(int dx)
    {

        this.x += dx;
    }
}
