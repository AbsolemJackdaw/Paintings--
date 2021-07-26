package subaraki.paintings.util.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.event.RegistryEvent;
import subaraki.paintings.mod.Paintings;

public class PaintingPackReader {

    private static ArrayList<PaintingEntry> addedPaintings = new ArrayList<>();

    public PaintingPackReader registerReloadListener()
    {

        Paintings.LOG.info("Registering Resource Reloading");
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        if (rm instanceof ReloadableResourceManager)
        {
            ((ReloadableResourceManager) rm).registerReloadListener(new PreparableReloadListener() {

                @Override
                public CompletableFuture<Void> reload(PreparationBarrier p_10638_, ResourceManager p_10639_, ProfilerFiller p_10640_, ProfilerFiller p_10641_, Executor p_10642_, Executor p_10643_)
                {

                    return CompletableFuture.runAsync(() -> {
                        loadFromJson();
                    });
                }

            });
            // (PreparableReloadListener) (resourceManager, resourcePredicate) -> {
            // if (resourcePredicate.test(VanillaResourceType.TEXTURES))
            // {
            // loadFromJson();
            // }
            // });
        }

        return this;
    }

    /**
     * called once on mod clas initialization. the loadFromJson called in here reads
     * json files directly out of a directory.
     */
    public PaintingPackReader init()
    {

        Paintings.LOG.info("loading json file and contents for paintings.");
        loadFromJson();

        return this;
    }

    private void loadFromJson()
    {

        try
        {

            File directory = new File("./paintings");

            if (!directory.exists())
            {

                directory.mkdir();

                // read provided base json
                // copy over to exterior folder
                // copy contents of base
                try (InputStream is = getClass().getResourceAsStream("/assets/paintings/paintings.json");
                        OutputStream os = new FileOutputStream("./paintings/paintings.json");)
                {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0)
                    {
                        os.write(buffer, 0, length);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            for (File jsonfile : directory.listFiles())
            {
                if (jsonfile.isFile())
                {
                    if (jsonfile.getName().endsWith(".json"))
                    {

                        InputStream stream = new FileInputStream(jsonfile);
                        Gson gson = new GsonBuilder().create();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        JsonElement je = gson.fromJson(reader, JsonElement.class);
                        JsonObject json = je.getAsJsonObject();

                        JsonArray array = json.getAsJsonArray("paintings");

                        for (int i = 0; i < array.size(); i++)
                        {

                            JsonObject jsonObject = array.get(i).getAsJsonObject();

                            String textureName = jsonObject.get("name").getAsString();

                            int sizeX = 0;
                            int sizeY = 0;
                            int sizeSquare = 0;

                            if (jsonObject.has("x"))
                            {
                                sizeX = jsonObject.get("x").getAsInt();
                            }

                            if (jsonObject.has("y"))
                            {
                                sizeY = jsonObject.get("y").getAsInt();
                            }

                            if (jsonObject.has("square"))
                            {
                                sizeSquare = jsonObject.get("square").getAsInt();
                            }

                            if (sizeSquare == 0)
                                if ((sizeX == 0 || sizeY == 0))
                                {
                                    Paintings.LOG.error("Tried loading a painting where one of the sides was 0 ! ");
                                    Paintings.LOG.error("Painting name is : " + textureName);
                                    Paintings.LOG.error("Skipping...");
                                    continue;
                                }

                            if (sizeSquare % 16 != 0)
                                if ((sizeX % 16 != 0 || sizeY % 16 != 0))
                                {
                                    Paintings.LOG.error("Tried loading a painting with a size that is not a multiple of 16 !! ");
                                    Paintings.LOG.error("Painting name is : " + textureName);
                                    Paintings.LOG.error("Skipping...");
                                    continue;
                                }

                            PaintingEntry entry = new PaintingEntry(textureName, sizeX, sizeY, sizeSquare);
                            Paintings.LOG.info(String.format("Loaded json painting %s , %d x %d", entry.getRefName(), entry.getSizeX(), entry.getSizeY()));
                            addedPaintings.add(entry);

                        }
                        stream.close();
                    }
                }
            }
        }
        catch (

        IOException e)
        {
            Paintings.LOG.warn("************************************");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.warn("No Painting Packs Detected. You will not be able to use ");
            Paintings.LOG.warn("the Paintings ++ Mod correctly.");
            Paintings.LOG.warn("Make sure to select or set some in the resourcepack gui !");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.warn("************************************");

            e.printStackTrace();
        }
    }

    public static void registerToMinecraft(RegistryEvent.Register<Motive> event)
    {

        for (PaintingEntry entry : addedPaintings)
        {
            Motive painting = new Motive(entry.getSizeX(), entry.getSizeY()).setRegistryName(Paintings.MODID, entry.getRefName());
            event.getRegistry().register(painting);
            Paintings.LOG.info("registered painting " + painting);
        }
    }

}
