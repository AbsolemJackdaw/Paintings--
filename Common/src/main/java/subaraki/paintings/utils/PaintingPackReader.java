package subaraki.paintings.utils;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import static subaraki.paintings.Paintings.LOGGER;

public class PaintingPackReader {

    public static final ArrayList<PaintingEntry> PAINTINGS = new ArrayList<>();
    /**
     * Called individually. Scanpacks is ran twice this way, but cached.
     * scanpacks is intensive and shouldn't be called too many times
     */
    public static final Set<Path> FORCE_LOAD = new TreeSet<>();
    private static final Gson gson = new GsonBuilder().create();

    /**
     * called once on mod class initialization. the loadFromJson called in here reads
     * json files directly out of a directory.
     */
    public void init() {
        LOGGER.info("loading json file and contents for paintings.");
        duplicateBaseToFolder();
        makeEntriesFromStream(getClass().getResourceAsStream("/assets/paintings/paintings.json"));
        scanPacks();
    }

    private void makeEntriesFromPath(Path jsonPaintingFile) throws IOException {

        LOGGER.info(String.format("resolving %s ...", jsonPaintingFile.getFileName().toString()));

        if (Files.isRegularFile(jsonPaintingFile) && jsonPaintingFile.toString().endsWith(".json")) {
            InputStream stream = Files.newInputStream(jsonPaintingFile);
            makeEntriesFromStream(stream);
        }
    }

    private void makeEntriesFromStream(InputStream stream) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        JsonElement je = gson.fromJson(reader, JsonElement.class);
        JsonObject json = je.getAsJsonObject();

        JsonArray array = json.getAsJsonArray("paintings");

        for (int index = 0; index < array.size(); index++) {

            JsonObject jsonObject = array.get(index).getAsJsonObject();

            String textureName = jsonObject.get("name").getAsString();
            if (!ResourceLocation.isValidResourceLocation(textureName)) {
                LOGGER.error("Non [a-z0-9/._-] character in path of location: " + textureName);
                continue;
            }

            int sizeX = 0;
            int sizeY = 0;
            int animY;

            if (jsonObject.has("x")) {
                sizeX = jsonObject.get("x").getAsInt();
            }

            if (jsonObject.has("y")) {
                sizeY = jsonObject.get("y").getAsInt();
            }

            if (jsonObject.has("square")) {
                sizeX = sizeY = jsonObject.get("square").getAsInt();
            }

            animY = sizeY; //init variable if there is no data
            if (jsonObject.has("anim")) {
                animY = jsonObject.get("anim").getAsInt();
            }

            if (sizeX == 0 || sizeY == 0) {
                LOGGER.error("Tried loading a painting where one of the sides was 0 ! ");
                LOGGER.error("Painting name is : " + textureName);
                LOGGER.error("Skipping...");
                continue;
            } else if (sizeX % 16 != 0 || sizeY % 16 != 0) {
                LOGGER.error("Tried loading a painting with a size that is not a multiple of 16 !! ");
                LOGGER.error("Painting name is : " + textureName);
                LOGGER.error("Skipping...");
                continue;
            }

            PaintingEntry entry = new PaintingEntry(textureName, sizeX, sizeY, animY);
            LOGGER.info(String.format("Loaded json painting %s , %d x %d", entry.getPaintingName(), entry.getSizeX(), entry.getSizeY()));
            PAINTINGS.add(entry);
        }
    }


    /**
     * read out all resourcepacks, exclusively in zips, to look for any other pack and copy their json file over.
     * since 1.19, also responsable for a list of the resourcepacks to force-load.
     */
    private void scanPacks() {
        FORCE_LOAD.clear();//clear forceloaders to refill
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("./resourcepacks"))) {
            LOGGER.info("Reading out ResourcePacks to find painting related json files");

            for (Path resourcePackPath : directoryStream) {
                LOGGER.info("Reading `{}`", resourcePackPath.getFileName().toString());
                try {
                    boolean isFolder = Files.isDirectory(resourcePackPath);

                    if (isFolder) {
                        walk(resourcePackPath);
                    } else {
                        URI uriToExplore = new URI("jar:%s".formatted(resourcePackPath.toUri().getScheme()), resourcePackPath.toUri().getPath(), null);
                        try (FileSystem system = initFileSystem(uriToExplore)) {
                            walk(system.getPath("/"), resourcePackPath);
                        } catch (Exception e) {
                            LOGGER.warn("************************************");
                            LOGGER.error("Invalid ResourcePack  {}", resourcePackPath.getFileName().toString());
                            LOGGER.error(e.getMessage());
                            LOGGER.warn("************************************");
                        }
                    }

                } catch (URISyntaxException e) {
                    LOGGER.warn("************************************");
                    LOGGER.error("Error Detected in ResourcePack `{}` ", resourcePackPath.getFileName().toString());
                    LOGGER.warn(e);
                    LOGGER.warn("************************************");
                }
            }
        } catch (IOException e) {

            LOGGER.warn("************************************");
            LOGGER.warn("!*!*!*!*!");
            LOGGER.error("A fatal error occurred reading the resource pack directory");
            LOGGER.error("SKIPPING ENTIRE PROCESS");
            LOGGER.warn("!*!*!*!*!");
            LOGGER.warn("************************************");
            LOGGER.warn(e);
        }
    }

    private void walk(Path path) throws IOException {
        walk(path, path);
    }

    private void walk(Path path, Path packToForceLoadpath) {
        try (Stream<Path> walker = Files.walk(path)) {
            Iterator<Path> unzipped = walker.iterator();

            while (unzipped.hasNext()) {
                boolean copyOver = false;

                Path next = unzipped.next();
                if (Files.isRegularFile(next) && next.toString().endsWith("json")) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(next)))) {

                        Gson gson = new GsonBuilder().create();
                        JsonElement je = gson.fromJson(reader, JsonElement.class);
                        JsonObject json = je.getAsJsonObject();

                        if (json.has("paintings")) {
                            copyOver = true;
                            FORCE_LOAD.add(packToForceLoadpath);
                            //FLRP stand for Force Loaded ResourcePack. abreviated to not scare end users with the words 'Force Loaded'
                            LOGGER.info("FLRP & Validated: {}", next.getFileName().toString());
                        }
                    } catch (Exception e) {
                        LOGGER.warn("************************************");
                        LOGGER.error("`{}` Errored. Skipping.", next.getFileName().toString());
                        LOGGER.error(e.getMessage());
                        LOGGER.warn("************************************");
                    }
                }
                if (copyOver) {
                    try {
                        makeEntriesFromPath(next);
                    } catch (IOException e) {
                        LOGGER.warn("************************************");
                        LOGGER.warn(String.format("json file %s could not parse.", next.getFileName()));
                        LOGGER.warn("************************************");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.warn("************************************");
            LOGGER.error("Couldn't walk `{}` ", path.getFileName().toString());
            LOGGER.warn(e);
            LOGGER.warn("************************************");
        }
    }


    private FileSystem initFileSystem(URI uri) throws IOException {
        try {
            return FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException e) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            return FileSystems.newFileSystem(uri, env);
        }
    }

    private void duplicateBaseToFolder() {
        // duplicate the base painting's template to our custom folder
        LOGGER.info("**Paintings++ no longer copies the base file outside if the mod. **");
    }
}
