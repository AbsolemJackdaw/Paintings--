package subaraki.paintings.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import subaraki.paintings.gui.PaintingScreen;

public class ClientReferences {

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void openPaintingScreen(PaintingScreen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
