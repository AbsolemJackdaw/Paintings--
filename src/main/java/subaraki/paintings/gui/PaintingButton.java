package subaraki.paintings.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.components.Button.OnPress;

public class PaintingButton extends Button {

    private static final int BORDER = 3;
    private static final int YELLOW = -256;

    ResourceLocation resLoc;

    public PaintingButton(int x, int y, int w, int h, Component text, OnPress onPress, Motive pt) {

        super(x, y, w, h, text, onPress);
        String combo = pt.getRegistryName().getNamespace() + ":textures/painting/" + pt.getRegistryName().getPath() + ".png";
        this.resLoc = new ResourceLocation(combo);
    }

    @Override
    public void renderButton(PoseStack mat, int mouseX, int mouseY, float partial_ticks)
    {

        Minecraft.getInstance().getTextureManager().bind(resLoc);

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
