package subaraki.paintings.mod.client;

// import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.PaintingsPattern;

@SideOnly(Side.CLIENT)
public class RenderPaintingLate extends Render implements IRenderFactory {

    private static ResourceLocation TEXTURE = new ResourceLocation("subaraki:art/" + ConfigurationHandler.instance.texture + ".png");

    public RenderPaintingLate(RenderManager renderManager) {
        super(renderManager);
    }

    private void doRender(EntityPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(entity);
        EntityPainting.EnumArt enumart = entity.art;
        float f = 0.0625F;
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.renderPainting(entity, enumart.sizeX, enumart.sizeY, enumart.offsetX, enumart.offsetY);

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void renderPainting(EntityPainting painting, int width, int height, int textureU, int textureV) {
        float centerX = -width / 2.0F;
        float centerY = -height / 2.0F;

        float x0 = 3F / 4;     // 0.75
        float x1 = 385F / 512; // 0.751953125
        float x2 = 10F / 16;   // 0.8125
        float y0 = 0F;
        float y1 = 1F / 512;   // 0.001953125
        float y2 = 1F / 16;    // 0.0625
        float z = 0.5F;

        // For each section in the painting:
        for (int y = 0; y < height / 16; ++y) {
            for (int x = 0; x < width / 16; ++x) {

                // Compute Lighting
                float ltX0 = centerX + x * 16F;
                float ltX1 = ltX0 + 16F;
                float ltY0 = centerY + y * 16F;
                float ltY1 = ltY0 + 16F;
                this.setLightmap(painting, (ltX1 + ltX0) / 2, (ltY1 + ltY0) / 2);

                // Compute Texture
                float txWidth = PaintingsPattern.instance.getSize().width * 16;
                float txHeight = PaintingsPattern.instance.getSize().height * 16;
                float txX0 = (float) (textureU + width - x * 16) / txWidth;
                float txX1 = (float) (textureU + width - (x + 1) * 16) / txWidth;
                float txY0 = (float) (textureV + height - y * 16) / txHeight;
                float txY1 = (float) (textureV + height - (y + 1) * 16) / txHeight;

                // Create 3D model
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
                // Front
                bufferbuilder.pos(ltX1, ltY0, -z).tex(txX1, txY0).normal( 0, 0,-1).endVertex();
                bufferbuilder.pos(ltX0, ltY0, -z).tex(txX0, txY0).normal( 0, 0,-1).endVertex();
                bufferbuilder.pos(ltX0, ltY1, -z).tex(txX0, txY1).normal( 0, 0,-1).endVertex();
                bufferbuilder.pos(ltX1, ltY1, -z).tex(txX1, txY1).normal( 0, 0,-1).endVertex();
                // Back
                bufferbuilder.pos(ltX1, ltY1,  z).tex(x0, y0).normal( 0, 0, 1).endVertex();
                bufferbuilder.pos(ltX0, ltY1,  z).tex(x2, y0).normal( 0, 0, 1).endVertex();
                bufferbuilder.pos(ltX0, ltY0,  z).tex(x2, y2).normal( 0, 0, 1).endVertex();
                bufferbuilder.pos(ltX1, ltY0,  z).tex(x0, y2).normal( 0, 0, 1).endVertex();
                // Top
                bufferbuilder.pos(ltX1, ltY1, -z).tex(x0, y1).normal( 0, 1, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY1, -z).tex(x2, y1).normal( 0, 1, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY1,  z).tex(x2, y1).normal( 0, 1, 0).endVertex();
                bufferbuilder.pos(ltX1, ltY1,  z).tex(x0, y1).normal( 0, 1, 0).endVertex();
                // Bottom
                bufferbuilder.pos(ltX1, ltY0,  z).tex(x0, y1).normal( 0,-1, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY0,  z).tex(x2, y1).normal( 0,-1, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY0, -z).tex(x2, y1).normal( 0,-1, 0).endVertex();
                bufferbuilder.pos(ltX1, ltY0, -z).tex(x0, y1).normal( 0,-1, 0).endVertex();
                // Left
                bufferbuilder.pos(ltX1, ltY1,  z).tex(x1, y0).normal(-1, 0, 0).endVertex();
                bufferbuilder.pos(ltX1, ltY0,  z).tex(x1, y2).normal(-1, 0, 0).endVertex();
                bufferbuilder.pos(ltX1, ltY0, -z).tex(x1, y2).normal(-1, 0, 0).endVertex();
                bufferbuilder.pos(ltX1, ltY1, -z).tex(x1, y0).normal(-1, 0, 0).endVertex();
                // Right
                bufferbuilder.pos(ltX0, ltY1, -z).tex(x1, y0).normal( 1, 0, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY0, -z).tex(x1, y2).normal( 1, 0, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY0,  z).tex(x1, y2).normal( 1, 0, 0).endVertex();
                bufferbuilder.pos(ltX0, ltY1,  z).tex(x1, y0).normal( 1, 0, 0).endVertex();
                tessellator.draw();
            }
        }
    }

    private void setLightmap(EntityPainting entity, float x, float y) {
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY + y / 16);
        int k = MathHelper.floor(entity.posZ);
        EnumFacing enumfacing = entity.facingDirection;

        if (enumfacing == EnumFacing.NORTH) {
            i = MathHelper.floor(entity.posX + x / 16);
        }

        if (enumfacing == EnumFacing.WEST) {
            k = MathHelper.floor(entity.posZ - x / 16);
        }

        if (enumfacing == EnumFacing.SOUTH) {
            i = MathHelper.floor(entity.posX - x / 16);
        }

        if (enumfacing == EnumFacing.EAST) {
            k = MathHelper.floor(entity.posZ + x / 16);
        }

        int l = this.renderManager.world.getCombinedLight(new BlockPos(i, j, k), 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i1, (float) j1);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1EntityPainting) {
        return TEXTURE;
    }

    @Override
    public Render createRenderFor(RenderManager manager) {
        return this;
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityPainting) par1Entity, par2, par4, par6, par8, par9);
    }
}
