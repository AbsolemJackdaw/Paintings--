package subaraki.paintings.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.player.Player;
import subaraki.paintings.gui.PaintingScreen;

public class ClientReferences {

    public static Player getClientPlayer()
    {

        return Minecraft.getInstance().player;
    }

    public static void openPaintingScreen(Motive[] resLocs, int entityID)
    {

        Minecraft.getInstance().setScreen(new PaintingScreen(resLocs, entityID));
    }
}
