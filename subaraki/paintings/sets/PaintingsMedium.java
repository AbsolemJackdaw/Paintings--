package subaraki.paintings.sets;

import net.minecraftforge.common.util.EnumHelper;

public class PaintingsMedium
{
	private static int i = 0;

	public static void addPaintings()
	{
		PaintingIgnore.ignoreVanillaPaintings();

	/*
	 * EnumHelper.addArt(String enum id, String name, int sizeX, int sixeY,   int topleftcornerX, int topleftcornerY);
	 *
	 * 	for(int y = 0; y <= (num Y - 1); y++)
	 * 		for(int x = 1; x <= num X; x++)
	 * 			EnumHelper.addArt("EnumArt_" + i, "ptg_XxY_"+(x+(y*num X)), X, Y, offsetX + X*x, offsetY + Y*y);i++;
	 *
	 * 	for(int x = 1; x <= num X; x++)
	 * 		EnumHelper.addArt("EnumArt_" + i, "ptg_XxY_"+(x), X, Y, offsetX + X*x, offsetY);i++;
	 *
	 */

		for(int y = 0; y <= 1; y++)
		{
			for(int x = 1; x <= 23; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_1x1_"+(x+(y*23)), 16, 16, 16*(x-1), 16*y);i++;
			}
		}
		for(int y = 0; y <= 1; y++)
		{
			for(int x = 1; x <= 3; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_"+(x+(y*23)), 32, 16, 400+(32*x), 16*y);i++;
			}
		}
		for(int x = 1; x <= 16; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_2x1_"+(x+6), 32, 16, 32*(x-1), 32);i++;
		}
		for(int y = 0; y <= 3; y++)
		{
			for(int x = 1; x <= 16; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_2x2_"+(x+(y*16)), 32, 32, 32*(x-1), 48+(32*y));i++;
			}
		}
		for(int y = 0; y <= 1; y++)
		{
			for(int x = 1; x <= 5; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_1x2_"+(x+(y*5)), 16, 32, 16*(x-1), 176+(32*y));i++;
			}
		}
		for(int y = 0; y <= 1; y++)
		{
			for(int x = 1; x <= 9; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_3x2_"+(x+(y*9)), 48, 32, 32+(48*x), 176+(32*y));i++;
			}
		}
		for(int x = 1; x <= 16; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_2x3_"+(x), 32, 48, 32*(x-1), 240);i++;
		}
		for(int x = 1; x <= 6; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_2x4_"+(x), 32, 64, 32*(x-1), 288);i++;
		}
		for(int y = 0; y <= 1; y++)
		{
			for(int x = 1; x <= 5; x++)
			{
				EnumHelper.addArt("EnumArt_" + i, "ptg_4x2_"+(x+(y*5)), 64, 32, 128+(64*x), 288+(32*y));i++;
			}
		}
		for(int x = 1; x <= 5; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_1x3_"+(x), 16, 48, 16*(x-1), 352);i++;
		}
		for(int x = 1; x <= 9; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_3x3_"+(x), 48, 48, 32+(48*x), 352);i++;
		}
		for(int x = 1; x <= 8; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_4x3_"+(x), 64, 48, 64*(x-1), 400);i++;
		}
		for(int x = 1; x <= 8; x++)
		{
			EnumHelper.addArt("EnumArt_" + i, "ptg_4x4_"+(x), 64, 64, 64*(x-1), 488);i++;
		}
	}
}