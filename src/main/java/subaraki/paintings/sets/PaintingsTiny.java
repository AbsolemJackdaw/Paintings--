package subaraki.paintings.sets;

import net.minecraftforge.common.util.EnumHelper;

public class PaintingsTiny {

	private static int i = 0;

	public static void addPaintings(){
		PaintingIgnore.ignoreVanillaPaintings();

		for(int x = 1; x <= 23; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(x), 16, 16, 16*(x-1), 0);i++;
		}
		for(int x = 1; x <= 6; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(23+x), 16, 16, 400+(x*16), 0);i++;
		}

		for(int x = 1; x <= 23; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(x+29), 16, 16, 16*(x-1), 16);i++;
		}
		for(int x = 1; x <= 6; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(52+x), 16, 16, 400+(x*16), 16);i++;
		}

		for(int x = 1; x <= 8; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(58+x), 16, 16, 112+(x*16), 432);i++;
		}

		for(int x = 1; x <= 4; x++){
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(66+x), 16, 16, 432+(x*16), 448);i++;
		}


		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_6", 32, 16, 160, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_7", 32, 16, 192, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_8", 32, 16, 224, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_9", 32, 16, 256, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_10", 32, 16, 288, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_11", 32, 16, 320, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_12", 32, 16, 352, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_13", 32, 16, 384, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_14", 32, 16, 416, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_15", 32, 16, 448, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_16", 32, 16, 480, 32);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_17", 32, 16, 0, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_18", 32, 16, 32, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_19", 32, 16, 64, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_20", 32, 16, 96, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_21", 32, 16, 128, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_22", 32, 16, 160, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_23", 32, 16, 192, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_24", 32, 16, 224, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_25", 32, 16, 256, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_26", 32, 16, 288, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_27", 32, 16, 320, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_28", 32, 16, 352, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_29", 32, 16, 384, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_30", 32, 16, 416, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_31", 32, 16, 448, 48);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_32", 32, 16, 480, 48);i++;


		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_3", 16, 32, 32, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_4", 16, 32, 48, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_5", 16, 32, 64, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_6", 16, 32, 80, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_7", 16, 32, 96, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_8", 16, 32, 112, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_9", 16, 32, 128, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_10", 16, 32, 144, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_11", 16, 32, 160, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_12", 16, 32, 176, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_13", 16, 32, 192, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_14", 16, 32, 208, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_15", 16, 32, 224, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_16", 16, 32, 240, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_17", 16, 32, 256, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_18", 16, 32, 272, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_19", 16, 32, 288, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_20", 16, 32, 304, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_21", 16, 32, 320, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_22", 16, 32, 336, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_23", 16, 32, 352, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_24", 16, 32, 368, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_25", 16, 32, 384, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_26", 16, 32, 400, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_27", 16, 32, 416, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_28", 16, 32, 432, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_29", 16, 32, 448, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_30", 16, 32, 464, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_31", 16, 32, 480, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_32", 16, 32, 496, 64);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_1", 48, 16, 0, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_2", 48, 16, 48, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_3", 48, 16, 96, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_4", 48, 16, 144, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_5", 48, 16, 192, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_6", 48, 16, 240, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_7", 48, 16, 288, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_8", 48, 16, 336, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_9", 48, 16, 0, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_10", 48, 16, 48, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_11", 48, 16, 96, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_12", 48, 16, 144, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_13", 48, 16, 192, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_14", 48, 16, 240, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_15", 48, 16, 288, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_16", 48, 16, 336, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_17", 48, 16, 0, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_18", 48, 16, 48, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_19", 48, 16, 96, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_20", 48, 16, 144, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_21", 48, 16, 192, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_22", 48, 16, 240, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_23", 48, 16, 288, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_24", 48, 16, 336, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_25", 48, 16, 0, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_26", 48, 16, 48, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_27", 48, 16, 96, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_28", 48, 16, 144, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_29", 48, 16, 192, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_30", 48, 16, 240, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_31", 48, 16, 288, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x1_32", 48, 16, 336, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_1", 64, 16, 384, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_2", 64, 16, 448, 96);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_3", 64, 16, 384, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_4", 64, 16, 448, 112);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_5", 64, 16, 384, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_6", 64, 16, 448, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_7", 64, 16, 384, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x1_8", 64, 16, 448, 144);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_1", 16, 48, 0, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_2", 16, 48, 16, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_3", 16, 48, 32, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_4", 16, 48, 48, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_5", 16, 48, 64, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_6", 16, 48, 80, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_7", 16, 48, 96, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_8", 16, 48, 112, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_9", 16, 48, 128, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_10", 16, 48, 144, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_11", 16, 48, 160, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_12", 16, 48, 176, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_13", 16, 48, 192, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_14", 16, 48, 208, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_15", 16, 48, 224, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_16", 16, 48, 240, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_17", 16, 48, 256, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_18", 16, 48, 272, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_19", 16, 48, 288, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_20", 16, 48, 304, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_21", 16, 48, 320, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_22", 16, 48, 336, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_23", 16, 48, 352, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_24", 16, 48, 368, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_25", 16, 48, 384, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_26", 16, 48, 400, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_27", 16, 48, 416, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_28", 16, 48, 432, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_29", 16, 48, 448, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_30", 16, 48, 464, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_31", 16, 48, 480, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_32", 16, 48, 496, 160);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_1", 32, 32, 0, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_2", 32, 32, 32, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_3", 32, 32, 64, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_4", 32, 32, 96, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_5", 32, 32, 128, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_6", 32, 32, 160, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_7", 32, 32, 192, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_8", 32, 32, 224, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_9", 32, 32, 256, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_10", 32, 32, 288, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_11", 32, 32, 320, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_12", 32, 32, 352, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_13", 32, 32, 384, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_14", 32, 32, 416, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_15", 32, 32, 0, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_16", 32, 32, 32, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_17", 32, 32, 64, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_18", 32, 32, 96, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_19", 32, 32, 128, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_20", 32, 32, 160, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_21", 32, 32, 192, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_22", 32, 32, 224, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_23", 32, 32, 256, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_24", 32, 32, 288, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_25", 32, 32, 320, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_26", 32, 32, 352, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_27", 32, 32, 384, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_28", 32, 32, 416, 240);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_29", 32, 32, 192, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_30", 32, 32, 224, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_31", 32, 32, 256, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_32", 32, 32, 288, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_33", 32, 32, 320, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_34", 32, 32, 352, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_35", 32, 32, 384, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_36", 32, 32, 416, 432);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x4_1", 16, 64, 448, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x4_2", 16, 64, 464, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x4_3", 16, 64, 480, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_1x4_4", 16, 64, 496, 208);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_1", 48, 32, 0, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_2", 48, 32, 48, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_3", 48, 32, 96, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_4", 48, 32, 144, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_5", 48, 32, 192, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_6", 48, 32, 240, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_7", 48, 32, 288, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_8", 48, 32, 336, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_9", 48, 32, 0, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_10", 48, 32, 48, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_11", 48, 32, 96, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_12", 48, 32, 144, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_13", 48, 32, 192, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_14", 48, 32, 240, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_15", 48, 32, 288, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_16", 48, 32, 336, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x2_1", 64, 32, 384, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x2_2", 64, 32, 448, 272);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x2_3", 64, 32, 384, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x2_4", 64, 32, 448, 304);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_1", 32, 48, 0, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_2", 32, 48, 32, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_3", 32, 48, 64, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_4", 32, 48, 96, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_5", 32, 48, 128, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_6", 32, 48, 160, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_7", 32, 48, 192, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_8", 32, 48, 224, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_9", 32, 48, 256, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_10", 32, 48, 288, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_11", 32, 48, 320, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_12", 32, 48, 352, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_13", 32, 48, 384, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_14", 32, 48, 416, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_15", 32, 48, 448, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_16", 32, 48, 480, 336);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x4_1", 32, 64, 0, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x4_2", 32, 64, 32, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x4_3", 32, 64, 64, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_2x4_4", 32, 64, 96, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_1", 48, 48, 128, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_2", 48, 48, 176, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_3", 48, 48, 224, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_4", 48, 48, 272, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_5", 48, 48, 320, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_6", 48, 48, 368, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_7", 48, 48, 416, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_8", 48, 48, 464, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x4_1", 64, 64, 0, 448);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x4_2", 64, 64, 64, 448);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x4_3", 64, 64, 128, 448);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_1", 64, 48, 192, 464);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_2", 64, 48, 256, 464);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_3", 64, 48, 320, 464);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_4", 64, 48, 384, 464);i++;
		EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_5", 64, 48, 448, 464);i++;

	}
}
