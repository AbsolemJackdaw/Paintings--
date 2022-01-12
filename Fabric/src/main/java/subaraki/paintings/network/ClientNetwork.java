package subaraki.paintings.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import subaraki.paintings.gui.PaintingScreen;

public class ClientNetwork {

    public static final ResourceLocation CLIENT_PACKET = new ResourceLocation(subaraki.paintings.Paintings.MODID, "client_packet");


    public static void registerClientPackets() {
        //Handles when client packet is received on client
        ClientPlayNetworking.registerGlobalReceiver(CLIENT_PACKET, (client, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            String[] resLocNames = new String[buf.readInt()];
            for (int i = 0; i < resLocNames.length; i++) {
                resLocNames[i] = buf.readUtf();
            }
            client.execute(() -> {
                ProcessClientPacket.handle(entityId, resLocNames, PaintingScreen::new);
            });
        });
    }

}
