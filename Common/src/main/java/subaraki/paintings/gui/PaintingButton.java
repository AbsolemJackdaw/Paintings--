package subaraki.paintings.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import subaraki.paintings.utils.PaintingPackReader;

public class PaintingButton extends Button {

    private static final int BORDER = 3;
    private static final int YELLOW = -256;

    ResourceLocation resLoc;
    private int animationY;

    public PaintingButton(int x, int y, int w, int h, Component text, OnPress onPress, PaintingVariant pt) {
        super(x, y, w, h, text, onPress, Button.DEFAULT_NARRATION);
        ResourceLocation rl = BuiltInRegistries.PAINTING_VARIANT.getKey(pt);
        String combo = rl.getNamespace() + ":textures/painting/" + rl.getPath() + ".png";
        this.resLoc = new ResourceLocation(combo);

        animationY = height;
        PaintingPackReader.PAINTINGS.stream().filter(paintingEntry -> paintingEntry.getResLoc().equals(rl) && !resLoc.getNamespace().equals("minecraft")).findFirst().ifPresent(paintingEntry -> animationY = paintingEntry.getAnimY());
    }

    @Override
    public void renderWidget(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, resLoc);

        //blit(stack, this.x, this.y, 0, 0, width, 16, width, 16);
        blit(stack, this.getX(), this.getY(), width, height, 0, 0, width, height, width, animationY);

        if (isHovered) {
            fill(stack, getX() - BORDER, getY() - BORDER, getX() + width + BORDER, getY(), YELLOW); // upper left to upper right
            fill(stack, getX() - BORDER, getY() + height, getX() + width + BORDER, getY() + height + BORDER, YELLOW); // lower left to lower right
            fill(stack, getX() - BORDER, getY(), getX(), getY() + height, YELLOW); // middle rectangle to the left
            fill(stack, getX() + width, getY(), getX() + width + BORDER, getY() + height, YELLOW); // middle rectangle to the right
        }
    }

    public void shiftY(int dy) {
        this.setY(this.getY() + dy);
    }

    public void shiftX(int dx) {
        this.setX(this.getX() + dx);
    }
}
