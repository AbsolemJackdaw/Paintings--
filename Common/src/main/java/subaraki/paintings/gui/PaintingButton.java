package subaraki.paintings.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.utils.PaintingPackReader;

public class PaintingButton extends Button {

    private static final int BORDER = 3;
    private static final int YELLOW = -256;

    ResourceLocation resLoc;
    private int animationY = 0;

    public PaintingButton(int x, int y, int w, int h, Component text, OnPress onPress, PaintingVariant pt) {
        super(x, y, w, h, text, onPress);
        ResourceLocation rl = Registry.PAINTING_VARIANT.getKey(pt);
        String combo = rl.getNamespace() + ":textures/painting/" + rl.getPath() + ".png";
        this.resLoc = new ResourceLocation(combo);

        animationY = height;
        PaintingPackReader.PAINTINGS.stream().filter(paintingEntry -> paintingEntry.getResLoc().equals(rl.getPath()) && !resLoc.getNamespace().equals("minecraft")).findFirst().ifPresent(paintingEntry -> animationY = paintingEntry.getAnimY());
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, resLoc);

        //blit(stack, this.x, this.y, 0, 0, width, 16, width, 16);
        blit(stack, this.x, this.y, width, height, 0, 0, width, height, width, animationY);

        if (isHovered) {
            fill(stack, x - BORDER, y - BORDER, x + width + BORDER, y, YELLOW); // upper left to upper right
            fill(stack, x - BORDER, y + height, x + width + BORDER, y + height + BORDER, YELLOW); // lower left to lower right
            fill(stack, x - BORDER, y, x, y + height, YELLOW); // middle rectangle to the left
            fill(stack, x + width, y, x + width + BORDER, y + height, YELLOW); // middle rectangle to the right
        }

    }

    public void shiftY(int dy) {
        this.y += dy;
    }

    public void shiftX(int dx) {
        this.x += dx;
    }
}
