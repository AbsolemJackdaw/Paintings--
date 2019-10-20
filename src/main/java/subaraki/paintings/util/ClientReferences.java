package subaraki.paintings.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.item.PaintingType;
import subaraki.paintings.gui.PaintingScreen;

public class ClientReferences {

    public static ClientPlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
    
    public static void openPaintingScreen(PaintingType[] resLocs, int entityID) {
        Minecraft.getInstance().displayGuiScreen(new PaintingScreen(resLocs, entityID));
    }
}
