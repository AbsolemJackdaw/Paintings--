//package subaraki.paintings.mod;
//
//import net.minecraft.entity.item.EntityPainting.EnumArt;
//import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
//
//public class PaintingsIgnore {
//
//	public static void ignoreVanillaPaintings(){
//		//set all vanilla paintings to nothing
//		
//		for (EnumArt art : EnumArt.values())
//		{
//			ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, art, 0, "sizeX", "field_75703_B");
//			ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, art, 0, "sizeY", "field_75704_C");
//		}
//	}
//}
