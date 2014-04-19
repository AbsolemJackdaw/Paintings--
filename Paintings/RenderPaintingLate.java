package Paintings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Paintings.config.ConfigFile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPaintingLate extends Render
{
	static boolean Insane = ConfigFile.instance.Insane;
	static boolean sphax = ConfigFile.instance.Sphax;
	static boolean tiny = ConfigFile.instance.TinyPics;
	static boolean gib = ConfigFile.instance.Gibea;
	static boolean nInsane = ConfigFile.instance.NewInsane;
	
	protected static final ResourceLocation art =
			new ResourceLocation("subaraki:art/" + 
	(sphax ? "sphax" : Insane ? "insane" : tiny ? "tiny" : nInsane ? "newInsane" : "gib") + ".png");

	private float getSize()
	{
		if ((Insane || tiny || nInsane) && (!gib) && (!sphax)) {
			return 512.0F;
		}
		if ((!Insane) && (!tiny) && (gib || sphax)) {
			return 256.0F;
		}
		return 256.0F;
	}

	public void renderThePainting(EntityPainting par1EntityPainting, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(32826);
		Minecraft.getMinecraft().renderEngine.bindTexture(art);
		EntityPainting.EnumArt enumart = par1EntityPainting.art;
		float f2 = 0.0625F;
		GL11.glScalef(f2, f2, f2);
		func_77010_a(par1EntityPainting, enumart.sizeX, enumart.sizeY, enumart.offsetX, enumart.offsetY);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	private void func_77010_a(EntityPainting par1EntityPainting, int par2, int par3, int par4, int par5)
	{
		float f = -par2 / 2.0F;
		float f1 = -par3 / 2.0F;
		float f2 = 0.5F;
		float f3 = 0.75F;
		float f4 = 0.8125F;
		float f5 = 0.0F;
		float f6 = 0.0625F;
		float f7 = 0.75F;
		float f8 = 0.8125F;
		float f9 = 0.00195313F;
		float f10 = 0.00195313F;
		float f11 = 0.7519531F;
		float f12 = 0.7519531F;
		float f13 = 0.0F;
		float f14 = 0.0625F;
		for (int i1 = 0; i1 < par2 / 16; i1++) {
			for (int j1 = 0; j1 < par3 / 16; j1++)
			{
				float f15 = f + (i1 + 1) * 16;
				float f16 = f + i1 * 16;
				float f17 = f1 + (j1 + 1) * 16;
				float f18 = f1 + j1 * 16;
				func_77008_a(par1EntityPainting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
				float f19 = (par4 + par2 - i1 * 16) / getSize();
				float f20 = (par4 + par2 - (i1 + 1) * 16) / getSize();
				float f21 = (par5 + par3 - j1 * 16) / getSize();
				float f22 = (par5 + par3 - (j1 + 1) * 16) / getSize();
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(f15, f18, -f2, f20, f21);
				tessellator.addVertexWithUV(f16, f18, -f2, f19, f21);
				tessellator.addVertexWithUV(f16, f17, -f2, f19, f22);
				tessellator.addVertexWithUV(f15, f17, -f2, f20, f22);
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(f15, f17, f2, f3, f5);
				tessellator.addVertexWithUV(f16, f17, f2, f4, f5);
				tessellator.addVertexWithUV(f16, f18, f2, f4, f6);
				tessellator.addVertexWithUV(f15, f18, f2, f3, f6);
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(f15, f17, -f2, f7, f9);
				tessellator.addVertexWithUV(f16, f17, -f2, f8, f9);
				tessellator.addVertexWithUV(f16, f17, f2, f8, f10);
				tessellator.addVertexWithUV(f15, f17, f2, f7, f10);
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator.addVertexWithUV(f15, f18, f2, f7, f9);
				tessellator.addVertexWithUV(f16, f18, f2, f8, f9);
				tessellator.addVertexWithUV(f16, f18, -f2, f8, f10);
				tessellator.addVertexWithUV(f15, f18, -f2, f7, f10);
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(f15, f17, f2, f12, f13);
				tessellator.addVertexWithUV(f15, f18, f2, f12, f14);
				tessellator.addVertexWithUV(f15, f18, -f2, f11, f14);
				tessellator.addVertexWithUV(f15, f17, -f2, f11, f13);
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(f16, f17, -f2, f12, f13);
				tessellator.addVertexWithUV(f16, f18, -f2, f12, f14);
				tessellator.addVertexWithUV(f16, f18, f2, f11, f14);
				tessellator.addVertexWithUV(f16, f17, f2, f11, f13);
				tessellator.draw();
			}
		}
	}

	private void func_77008_a(EntityPainting par1EntityPainting, float par2, float par3)
	{
		int i = MathHelper.floor_double(par1EntityPainting.posX);
		int j = MathHelper.floor_double(par1EntityPainting.posY + par3 / 16.0F);
		int k = MathHelper.floor_double(par1EntityPainting.posZ);
		if (par1EntityPainting.hangingDirection == 2) {
			i = MathHelper.floor_double(par1EntityPainting.posX + par2 / 16.0F);
		}
		if (par1EntityPainting.hangingDirection == 1) {
			k = MathHelper.floor_double(par1EntityPainting.posZ - par2 / 16.0F);
		}
		if (par1EntityPainting.hangingDirection == 0) {
			i = MathHelper.floor_double(par1EntityPainting.posX - par2 / 16.0F);
		}
		if (par1EntityPainting.hangingDirection == 3) {
			k = MathHelper.floor_double(par1EntityPainting.posZ + par2 / 16.0F);
		}
		int l = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int i1 = l % 65536;
		int j1 = l / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderThePainting((EntityPainting)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityPainting)
	{
		return art;
	}

}
