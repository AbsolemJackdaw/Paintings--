package subaraki.paintings.mod.entity.client;

import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.PaintingsPattern;
import subaraki.paintings.mod.entity.EntityNewPainting;

@SideOnly(Side.CLIENT)
public class RenderPaintingLate extends Render implements IRenderFactory {

	private static ResourceLocation TEXTURE = new ResourceLocation("subaraki:art/" + ConfigurationHandler.instance.texture + ".png");
	private static ResourceLocation PAINTINGS = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
	
	private TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(ConfigurationHandler.instance.background_texture);

	public RenderPaintingLate(RenderManager renderManager) {
		super(renderManager);
	}

	private void doRender(EntityNewPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();

		PaintingWrapper art = PaintingWrapper.PAINTINGS.get(entity.art);

		float f = 0.0625F;
		GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.renderPainting(entity, art.getX(), art.getY(), art.getU(), art.getV(), false);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.renderPainting(entity, art.getX(), art.getY(), art.getU(), art.getV(),true);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	private void renderPainting(EntityNewPainting painting, int width, int height, int textureU, int textureV, boolean isBack) {
		float centerX = -width / 2.0F;
		float centerY = -height / 2.0F;

		float txWidth = PaintingsPattern.instance.getSize().width * 16;
		float txHeight = PaintingsPattern.instance.getSize().height * 16;

		//an offset to use in painting borders to offset by one relative pixel
		float offsetX = 1f / txWidth;
		float offsetY = 1f / txHeight;

		// For each section in the painting:
		for (int y = 0; y < height / 16; ++y) {
			for (int x = 0; x < width / 16; ++x) {

				// Compute Lighting
				float ltX0 = centerX + x * 16F;
				float ltX1 = ltX0 + 16F;
				float ltY0 = centerY + y * 16F;
				float ltY1 = ltY0 + 16F;
				this.setLightmap(painting, (ltX1 + ltX0) / 2, (ltY1 + ltY0) / 2);

				// Compute Front Texture
				float frontTex_x_left = (float) (textureU + width - x * 16) / txWidth;
				float frontTex_x_right = (float) (textureU + width - (x + 1) * 16) / txWidth;
				float frontTex_y_up = (float) (textureV + height - y * 16) / txHeight;
				float frontTex_y_down = (float) (textureV + height - (y + 1) * 16) / txHeight;

				float z = 0.5f;

				// Create 3D model
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

				if(isBack)
				{
					this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

					// Back
					bufferbuilder.pos(ltX1, ltY1,  z).tex(sprite.getMinU(), sprite.getMinV()).normal( 0, 0, 1).endVertex();
					bufferbuilder.pos(ltX0, ltY1,  z).tex(sprite.getMaxU(), sprite.getMinV()).normal( 0, 0, 1).endVertex();
					bufferbuilder.pos(ltX0, ltY0,  z).tex(sprite.getMaxU(), sprite.getMaxV()).normal( 0, 0, 1).endVertex();
					bufferbuilder.pos(ltX1, ltY0,  z).tex(sprite.getMinU(), sprite.getMaxV()).normal( 0, 0, 1).endVertex();
				}
				else
				{
					this.bindEntityTexture(painting);
					// Front
					bufferbuilder.pos(ltX1, ltY0, -z).tex(frontTex_x_right, frontTex_y_up).normal( 0, 0,-1).endVertex();
					bufferbuilder.pos(ltX0, ltY0, -z).tex(frontTex_x_left, frontTex_y_up).normal( 0, 0,-1).endVertex();
					bufferbuilder.pos(ltX0, ltY1, -z).tex(frontTex_x_left, frontTex_y_down).normal( 0, 0,-1).endVertex();
					bufferbuilder.pos(ltX1, ltY1, -z).tex(frontTex_x_right, frontTex_y_down).normal( 0, 0,-1).endVertex();

					// Top
					bufferbuilder.pos(ltX1, ltY1, -z).tex(frontTex_x_right, frontTex_y_down).normal( 0, 1, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY1, -z).tex(frontTex_x_left, frontTex_y_down).normal( 0, 1, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY1,  z).tex(frontTex_x_left, frontTex_y_down).normal( 0, 1, 0).endVertex();
					bufferbuilder.pos(ltX1, ltY1,  z).tex(frontTex_x_right, frontTex_y_down).normal( 0, 1, 0).endVertex();
					// Bottom
					bufferbuilder.pos(ltX1, ltY0,  z).tex(frontTex_x_right, frontTex_y_up - offsetY).normal( 0,-1, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY0,  z).tex(frontTex_x_left, frontTex_y_up - offsetY).normal( 0,-1, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY0, -z).tex(frontTex_x_left, frontTex_y_up - offsetY).normal( 0,-1, 0).endVertex();
					bufferbuilder.pos(ltX1, ltY0, -z).tex(frontTex_x_right, frontTex_y_up - offsetY).normal( 0,-1, 0).endVertex();
					// Left
					bufferbuilder.pos(ltX1, ltY1,  z).tex(frontTex_x_right, frontTex_y_down).normal(-1, 0, 0).endVertex();
					bufferbuilder.pos(ltX1, ltY0,  z).tex(frontTex_x_right, frontTex_y_up).normal(-1, 0, 0).endVertex();
					bufferbuilder.pos(ltX1, ltY0, -z).tex(frontTex_x_right, frontTex_y_up).normal(-1, 0, 0).endVertex();
					bufferbuilder.pos(ltX1, ltY1, -z).tex(frontTex_x_right, frontTex_y_down).normal(-1, 0, 0).endVertex();
					// Right
					bufferbuilder.pos(ltX0, ltY1, -z).tex(frontTex_x_left - offsetX, frontTex_y_down).normal( 1, 0, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY0, -z).tex(frontTex_x_left - offsetX, frontTex_y_up).normal( 1, 0, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY0,  z).tex(frontTex_x_left - offsetX, frontTex_y_up).normal( 1, 0, 0).endVertex();
					bufferbuilder.pos(ltX0, ltY1,  z).tex(frontTex_x_left - offsetX, frontTex_y_down).normal( 1, 0, 0).endVertex();
				}


				tessellator.draw();

			}
		}
	}

	private void setLightmap(EntityNewPainting painting, float x, float y) {
		int i = MathHelper.floor(painting.posX);
		int j = MathHelper.floor(painting.posY + y / 16);
		int k = MathHelper.floor(painting.posZ);
		EnumFacing enumfacing = painting.facingDirection;

		if (enumfacing == EnumFacing.NORTH) {
			i = MathHelper.floor(painting.posX + x / 16);
		}

		if (enumfacing == EnumFacing.WEST) {
			k = MathHelper.floor(painting.posZ - x / 16);
		}

		if (enumfacing == EnumFacing.SOUTH) {
			i = MathHelper.floor(painting.posX - x / 16);
		}

		if (enumfacing == EnumFacing.EAST) {
			k = MathHelper.floor(painting.posZ + x / 16);
		}

		int l = this.renderManager.world.getCombinedLight(new BlockPos(i, j, k), 0);
		int i1 = l % 65536;
		int j1 = l / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i1, (float) j1);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityPainting) {
		return ConfigurationHandler.instance.texture.equals("vanilla") ? PAINTINGS : TEXTURE;
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.doRender((EntityNewPainting) par1Entity, par2, par4, par6, par8, par9);
	}
}
