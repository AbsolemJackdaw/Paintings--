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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import subaraki.paintings.mod.ConfigData;
import subaraki.paintings.mod.Paintings;

public class PaintingPackReader {

    private static ArrayList<PaintingEntry> addedPaintings = new ArrayList<>();

    public PaintingPackReader registerReloadListener() {

        Paintings.LOG.info("Registering Resource Reloading");
        IResourceManager rm = Minecraft.getInstance().getResourceManager();
        if (rm instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) rm).addReloadListener((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
                if (resourcePredicate.test(VanillaResourceType.TEXTURES)) {
                    loadFromJson();
                }
            });
        }

        return this;
    }

    /**
     * called once on mod clas initialization. the loadFromJson called in here reads
     * json files directly out opf a directory.
     */
    public PaintingPackReader init() {

        if(ConfigData.use_vanilla_only)
        {
            Paintings.LOG.info("Skipped loading any JSON files present in the install directory. Vanilla only will be used");
            return this;
        }
        
        Paintings.LOG.info("loading json file and contents for paintings.");
        loadFromJson();

        return this;
    }

    private void loadFromJson() {

        try {

            File directory = new File("./paintings");

            if (!directory.exists()) {

                directory.mkdir();

                //read provided base json
                //copy over to exterior folder
                //copy contents of base
                try (InputStream is = getClass().getResourceAsStream("/assets/paintings/paintings.json");
                        OutputStream os = new FileOutputStream("./paintings/paintings.json");) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (File jsonfile : directory.listFiles()) {
                if (jsonfile.isFile()) {
                    if (jsonfile.getName().endsWith(".json")) {

                        InputStream stream = new FileInputStream(jsonfile);
                        Gson gson = new GsonBuilder().create();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        JsonElement je = gson.fromJson(reader, JsonElement.class);
                        JsonObject json = je.getAsJsonObject();

                        JsonArray array = json.getAsJsonArray("paintings");

                        for (int i = 0; i < array.size(); i++) {

                            JsonObject jsonObject = array.get(i).getAsJsonObject();

                            String textureName = jsonObject.get("name").getAsString();

                            int sizeX = 0;
                            int sizeY = 0;
                            int sizeSquare = 0;

                            if (jsonObject.has("x")) {
                                sizeX = jsonObject.get("x").getAsInt();
                            }

                            if (jsonObject.has("y")) {
                                sizeY = jsonObject.get("y").getAsInt();
                            }

                            if (jsonObject.has("square")) {
                                sizeSquare = jsonObject.get("square").getAsInt();
                            }

                            PaintingEntry entry = new PaintingEntry(textureName, sizeX, sizeY, sizeSquare);
                            Paintings.LOG.info(String.format("Loaded json painting %s , %d x %d", entry.getRefName(), entry.getSizeX(), entry.getSizeY()));
                            addedPaintings.add(entry);

                            stream.close();
                        }
                    }
                }
            }
        } catch (

        IOException e) {
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

    public static void registerToMinecraft(RegistryEvent.Register<PaintingType> event) {

        for (PaintingEntry entry : addedPaintings) {
            PaintingType painting = new PaintingType(entry.getSizeX(), entry.getSizeY()).setRegistryName(Paintings.MODID, entry.getRefName());
            event.getRegistry().register(painting);
            Paintings.LOG.info("registered painting " + painting);
        }
    }

}
