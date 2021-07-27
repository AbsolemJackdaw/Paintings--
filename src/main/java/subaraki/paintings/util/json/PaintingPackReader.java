package subaraki.paintings.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.paintings.mod.Paintings;

@EventBusSubscriber(modid = Paintings.MODID, bus = Bus.FORGE)
public class PaintingPackReader {

    private static ArrayList<PaintingEntry> addedPaintings = new ArrayList<>();

    @SubscribeEvent
    public static void registerreloadListener(AddReloadListenerEvent event)
    {

//        event.addListener((ResourceManagerReloadListener) ((resourceManager) -> {
//            new PaintingPackReader().init();
//        }));

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

        // duplicate the gbase paintings template to our custom folder
        try
        {
            Paintings.LOG.info("Copying Over Base Template to /paintings");
            Path dir = Paths.get("./paintings");

            if (!Files.exists(dir))
            {

                Files.createDirectory(dir);
                Files.copy(getClass().getResourceAsStream("/assets/paintings/paintings.json"), dir.resolve("paintings.json"));
                // copyJsonToFolder
            }
        }
        catch (IOException e)
        {
            Paintings.LOG.warn("************************************");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.error("Copying Base Template Failed");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.warn("************************************");

            e.printStackTrace();
        }

        // read out all resourcepacks, exclusively in zips,
        // to look for any other pack
        // and copy their json file over
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("./resourcepacks"));)
        {
            Paintings.LOG.info("Reading out ResourcePacks to find painting related json files");

            for (Path resourcePackPath : ds)
            {
                if (resourcePackPath.toString().endsWith(".zip"))
                {
                    URI jarUri = new URI("jar:%s".formatted(resourcePackPath.toUri().getScheme()), resourcePackPath.toUri().getPath(), null);
                    
                    Paintings.LOG.info(jarUri);
                    
                    try (FileSystem system = initFileSystem(jarUri))
                    {
                        Iterator<Path> resourcePacks = Files.walk(system.getPath("/")).iterator();

                        while (resourcePacks.hasNext())
                        {
                            boolean copyOver = false;

                            Path next = resourcePacks.next();
                            if (Files.isRegularFile(next) && next.toString().endsWith("json"))
                            {
                                Paintings.LOG.info("Candidate Found " + next.getFileName());
                                try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(next))))
                                {

                                    Gson gson = new GsonBuilder().create();
                                    JsonElement je = gson.fromJson(reader, JsonElement.class);
                                    JsonObject json = je.getAsJsonObject();
                                    
                                    if (json.has("paintings"))
                                    {
                                        copyOver = true;
                                        Paintings.LOG.info("Candidate Validated");
                                    }
                                    else
                                    {
                                        Paintings.LOG.info("Candidate Not Valid, Rejected");
                                    }
                                }

                            }

                            if (copyOver)
                            {
                                Files.copy(next, Path.of("./paintings").resolve(next.getFileName().toString()));
                            }

                        }
                    }
                }
            }
        }
        catch (IOException | URISyntaxException e)
        {

            Paintings.LOG.warn("************************************");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.error("An error occured reading resourcepacks for painting related files. Skipping process.");
            Paintings.LOG.warn("!*!*!*!*!");
            Paintings.LOG.warn("************************************");

            e.printStackTrace();
        }

        // read out all json files in the painting directory

        Path dir = Paths.get("./paintings");

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir))
        {
            Paintings.LOG.info("Started Reading all json files in /painting directory");

            for (Path filesInDirPath : ds)
            {
                Paintings.LOG.info(filesInDirPath);
                Iterator<Path> jsonFiles = Files.walk(filesInDirPath).iterator();

                while (jsonFiles.hasNext())
                {
                    Path nextJson = jsonFiles.next();

                    if (Files.isRegularFile(nextJson) && nextJson.toString().endsWith(".json"))
                    {
                        InputStream stream = Files.newInputStream(nextJson);
                        Gson gson = new GsonBuilder().create();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        JsonElement je = gson.fromJson(reader, JsonElement.class);
                        JsonObject json = je.getAsJsonObject();

                        JsonArray array = json.getAsJsonArray("paintings");

                        for (int index = 0; index < array.size(); index++)
                        {

                            JsonObject jsonObject = array.get(index).getAsJsonObject();

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
                    }
                }

            }
        }
        catch (IOException e)
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
            Paintings.LOG.info("registered painting " + painting.getRegistryName());
        }
    }
    
    private FileSystem initFileSystem(URI uri) throws IOException
    {
        try
        {
            return FileSystems.getFileSystem(uri);
        }
        catch( FileSystemNotFoundException e )
        {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            return FileSystems.newFileSystem(uri, env);
        }
    }
}
