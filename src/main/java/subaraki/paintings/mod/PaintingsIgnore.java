package subaraki.paintings.mod;

import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class PaintingsIgnore {

	public static void ignoreVanillaPaintings(){
		//set all vanilla paintings to nothing
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.ALBAN, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.ALBAN, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.AZTEC, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.AZTEC, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.AZTEC_2, 0, "sizeX","field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.AZTEC_2, 0, "sizeY","field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BOMB, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BOMB, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BURNING_SKULL, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BURNING_SKULL, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BUST, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.BUST, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.COURBET, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.COURBET, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.CREEBET, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.CREEBET, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.DONKEY_KONG, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.DONKEY_KONG, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.FIGHTERS, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.FIGHTERS, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.GRAHAM, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.GRAHAM, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.KEBAB, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.KEBAB, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.MATCH, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.MATCH, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.PIGSCENE, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.PIGSCENE, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.PLANT, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.PLANT, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.POINTER, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.POINTER, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.POOL, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.POOL, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SEA, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SEA, 0, "sizeY", "field_75704_C");

		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SKELETON, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SKELETON, 0, "sizeY", "field_75704_C");

		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SKULL_AND_ROSES, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SKULL_AND_ROSES, 0, "sizeY", "field_75704_C");

		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.STAGE, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.STAGE, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SUNSET, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.SUNSET, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.VOID, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.VOID, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WANDERER, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WANDERER, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WASTELAND, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WASTELAND, 0, "sizeY", "field_75704_C");
		
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WITHER, 0, "sizeX", "field_75703_B");
		ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, EnumArt.WITHER, 0, "sizeY", "field_75704_C");
	}

	static void setFinalStatic(Class clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {

	}

}
