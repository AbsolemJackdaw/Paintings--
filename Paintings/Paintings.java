package Paintings;

import java.lang.reflect.Field;

import Paintings.config.ConfigFile;
import Paintings.sets.PaintingsGibea;
import Paintings.sets.PaintingsInsane;
import Paintings.sets.PaintingsNewInsane;
import Paintings.sets.PaintingsSphax;
import Paintings.sets.PaintingsTiny;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Paintings.MODID, name = Paintings.NAME, version = Paintings.VERSION)
public class Paintings
{

	public static final String MODID = "morepaintings";
	public static final String VERSION = "1.10 1";
	public static final String NAME = "Paintings++";

	@SidedProxy(serverSide="Paintings.CommonProxy", clientSide="Paintings.ClientProxy")
	public static CommonProxy proxy;
	private static final String CLASS_LOC = "com.mcf.davidee.paintinggui.gui.PaintingButton";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigFile.instance.loadConfig(event.getSuggestedConfigurationFile());

		proxy.registerRenderInformation();

	}

	String [] textureLoc= new String[5];
	int[] size = new int[5];
	boolean[] bools = new boolean[5];

	@EventHandler
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
