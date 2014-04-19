package Paintings.sets;

import net.minecraftforge.common.util.EnumHelper;

public class PaintingsNewInsane {


	private static int i =0;

	public static void addPaintings(){
		PaintingIgnore.ignoreVanillaPaintings();

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 128, 128, 0, 0);i++;
		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 128, 96, 128, 0);i++;

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 128, 0, 128);i++;
		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 128, 128, 128);i++;

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 32, 48, 160, 384);i++;

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 128, 16, 320, 432);i++;

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 48, 32, 368, 384);i++;
		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 48, 32, 368+48, 384);i++;

		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 80, 80, 160, 512-80);i++;
		EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 80, 80, 240, 512-80);i++;

		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 2; y++){
				EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 48, 256 + 64 *x, 48*y);i++;
			}
		}

		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 3; y++){
				if( !((x == 2 || x == 3) && (y == 2))){
					EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 64, 256 + 64 *x, 96 + 64*y);i++;
				}
			}
		}

		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 5; y++){
				if( !((x == 0 || x == 1) && (y == 4))){
					EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 32, 32, 384 + 32 *x, 224 + 32*y);i++;
				}
			}
		}

		for(int x = 0; x < 2; x++){
			for(int y = 0; y < 4; y++){
				EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 16, 256 + 64 *x, 288 + 16*y);i++;
			}
		}

		for(int y = 0; y < 4; y++){
			EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 128, 64, 0, 256 + 64*y);i++;
		}

		for(int x = 0; x < 1; x++){
			for(int y = 0; y < 14; y++){
				EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 32, 16, 128 + 32 *x, 288 + 16*y);i++;
			}
		}

		for(int x = 0; x < 2; x++){
			for(int y = 0; y < 2; y++){
				EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 96, 128 + 64 *x, 96 + 96*y);i++;
			}
		}

		for(int x = 0; x < 6; x++){
			EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 16, 48, 160 + 16 *x, 288 );i++;
		}

		for(int x = 0; x < 3; x++){
			EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 32, 48, 160 + 32 *x, 288+48 );i++;
		}

		for(int x = 0; x < 3; x++){
			EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 48, 48, 192 + 48 *x, 384 );i++;
		}

		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 2; y++){
				EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 48, 16, 256 + 48 *x, 352 + 16*y);i++;
			}
		}

		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 3; y++){
				if(!(y == 0 && x == 0)){
					EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 16, 32, 448 + 16 *x, 384 + 32*y);i++;
				}
			}
		}

		for(int x = 0; x < 7; x++){
			for(int y = 0; y < 3; y++){
				if(!(y < 2 &&  x > 1)){
					EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 16, 16, 336 + 16 *x, 384 + 16*y);i++;
				}
			}
		}

		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 2; y++){
				if(!(y == 0 && x > 1)){
					EnumHelper.addArt("EnumArt_" + i, "new_ins_"+i, 64, 32, 320 + 64 *x, (512-64)+ 32*y);i++;
				}
			}
		}
	}
}
