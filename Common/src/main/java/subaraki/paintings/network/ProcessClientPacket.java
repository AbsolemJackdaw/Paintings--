package subaraki.paintings.network;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import subaraki.paintings.Paintings;
import subaraki.paintings.utils.ClientReferences;

import java.util.Arrays;

public class ProcessClientPacket {

    public static void handle(int entityID, String[] resLocNames, ScreenSupplier screen) {
        if (resLocNames.length == 1) // we know what painting to set
        {
            Entity entity = ClientReferences.getClientPlayer().level.getEntity(entityID);
            if (entity instanceof Painting painting) {
                Motive type = Registry.MOTIVE.get(new ResourceLocation(resLocNames[0]));
                subaraki.paintings.Paintings.UTILITY.setArt(painting, type);
                Paintings.UTILITY.updatePaintingBoundingBox(painting);

            }
        } else // we need to open the painting gui to select a painting
        {
            Motive[] types = Arrays.stream(resLocNames).map(path -> Registry.MOTIVE.get(new ResourceLocation(path))).toArray(Motive[]::new);
            ClientReferences.openPaintingScreen(screen.make(types, entityID));
        }
    }
}
