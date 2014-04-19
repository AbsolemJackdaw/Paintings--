package Paintings;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;
import Paintings.config.ConfigFile;
import Paintings.sets.PaintingsGibea;
import Paintings.sets.PaintingsInsane;
import Paintings.sets.PaintingsNewInsane;
import Paintings.sets.PaintingsSphax;
import Paintings.sets.PaintingsTiny;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="PaintingsMod", name="Paintings++", version="1.7.2")
public class Paintings
{

	private static final String name = "Paintings++";
	private static final String version = "1.7.2 v1";
	public static Paintings instance;

	@SidedProxy(serverSide="Paintings.CommonProxy", clientSide="Paintings.ClientProxy")
	public static CommonProxy proxy;
	private static final String CLASS_LOC = "com.mcf.davidee.paintinggui.gui.PaintingButton";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigFile.instance.loadConfig(event.getSuggestedConfigurationFile());
	}

	String [] textureLoc= new String[5];
	int[] size = new int[5];
	boolean[] bools = new boolean[5];

	@Mod.EventHandler
	public void load(FMLInitializationEvent init)
	{
		boolean Insane = ConfigFile.instance.Insane;
		boolean sphax = ConfigFile.instance.Sphax;
		boolean tiny = ConfigFile.instance.TinyPics;
		boolean gib = ConfigFile.instance.Gibea;
		boolean nInsane = ConfigFile.instance.NewInsane;

		bools[0] = Insane;
		bools[1] = tiny;
		bools[2] = nInsane;
		bools[3] = gib;
		bools[4] = sphax;

		textureLoc[0] = "insane";
		textureLoc[1] = "tiny";
		textureLoc[2] = "newInsane";
		textureLoc[3] = "gib";
		textureLoc[4] = "sphax";

		size[0] = size[1] = size[2] = 512;
		size[3] = size[4] = 256;

		proxy.registerRenderInformation();

		for(int i = 0; i < 5; i++){
			if(bools[i])
				try {
					Class altClass = Class.forName("com.mcf.davidee.paintinggui.gui.PaintingButton");
					paintingGuiTextureHelper(altClass, "TEXTURE", new ResourceLocation("subaraki:art/"+ textureLoc[i] +".png"));
					paintingGuiHelper(altClass, "KZ_WIDTH", size[i]);
					paintingGuiHelper(altClass, "KZ_HEIGHT", size[i]);
				} catch (Exception e) {
					FMLLog.getLogger().info("Davidees painting mod not installed or to old/new. Skipping");
				}
		}

		if ((!tiny) && (!Insane) && (!sphax) && (gib))
			PaintingsGibea.addPaintings();

		if ((!tiny) && (!Insane) && (sphax) && (!gib))
			PaintingsSphax.addPaintings();

		if ((tiny) && (!Insane) && (!sphax) && (!gib))
			PaintingsTiny.addPaintings();

		if ((!tiny) && (Insane) && (!sphax) && (!gib))
			PaintingsInsane.addPaintings();

		if(nInsane && !Insane && !gib && !tiny && !sphax)
			PaintingsNewInsane.addPaintings();

	}

	private void paintingGuiHelper(Class c, String field, int value)
			throws Exception
			{
		Field f = c.getField(field);
		f.setAccessible(true);
		f.set(null, Integer.valueOf(value));
			}

	private void paintingGuiTextureHelper(Class c, String field, ResourceLocation loc)
			throws Exception
			{
		Field f = c.getField(field);
		f.setAccessible(true);
		f.set(null, loc);
			}
}
