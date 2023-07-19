package subaraki.paintings.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import subaraki.paintings.compat_layer.IPackRepoDiscoveryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static subaraki.paintings.Paintings.LOGGER;

public class PaintingPackReader {

    public static final List<PaintingEntry> PAINTINGS = new ArrayList<>();
    /**
     * Called individually. Scanpacks is run twice this way, but cached.
     * scanpacks is intensive and shouldn't be called too many times
     */
    public static final Set<Path> FORCE_LOAD = new TreeSet<>();

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

    private void makeEntriesFromStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
        parsePaintingsFromJson(json);
    }

    private void parsePaintingsFromJson(JsonObject json) {
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
     * read out all resource packs, exclusively in zips, to look for any other pack and copy their json file over.
     * since 1.19, also responsible for a list of the resource packs to force-load.
     */
    private void scanPacks() {
        FORCE_LOAD.clear();//clear force-loaders to refill

        Set<Path> packDirectories = new HashSet<>();
        packDirectories.add(Paths.get(".", "resourcepacks"));

        List<IPackRepoDiscoveryService> packRepos = ServiceLoader.load(IPackRepoDiscoveryService.class).stream().map(prov -> prov.get()).toList();
        packDirectories.addAll(packRepos.stream().flatMap(repoService -> repoService.getPackRepos().stream()).map(Path::of).collect(Collectors.toSet()));

        packDirectories.parallelStream()
                .filter(folder -> Files.exists(folder) && Files.isDirectory(folder))//Benefits of NIO here are tiny enough that we can avoid it's overhead
                .flatMap(folder -> Arrays.stream(folder.toFile().listFiles((dir, file) -> file.endsWith(".zip"))))
                .map(packFile -> {
                    try (FileSystem zipFs = FileSystems.newFileSystem(packFile.toPath())) {
                        Path json = zipFs.getPath("./paintings++.json");
                        if (!Files.exists(json)) json = zipFs.getPath("./paintings.json");//Fallback for backwards compat

                        if (Files.exists(json)) {
                            try (JsonReader reader = new JsonReader(Files.newBufferedReader(json))) { //Closing of stream is redundant here, but we'll do it anyways :shrug:
                                return Pair.of(packFile.toPath(), JsonParser.parseReader(reader).getAsJsonObject());
                            } catch (IOException e) {
                                LOGGER.warn("************************************");
                                LOGGER.error("`{}` Errored. Skipping.", json.getFileName().toString());
                                LOGGER.error(e.getMessage());
                                LOGGER.warn("************************************");
                            } catch (JsonParseException e) {
                                LOGGER.warn("************************************");
                                LOGGER.warn("json file `{}` could not parse.", json.getFileName().toString());
                                LOGGER.warn("************************************");
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        LOGGER.warn("************************************");
                        LOGGER.error("Invalid ResourcePack  {}", packFile.getName());
                        LOGGER.error(e.getMessage());
                        LOGGER.warn("************************************");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .filter(pair -> pair.getRight().has("paintings"))
                .forEach(pair -> {
                    FORCE_LOAD.add(pair.getLeft());
                    LOGGER.info("FLRP & Validated: {}", pair.getLeft().getFileName().toString());
                    parsePaintingsFromJson(pair.getRight());
                });
    }

    private void duplicateBaseToFolder() {
        // duplicate the base painting's template to our custom folder
        LOGGER.info("**Paintings++ no longer copies the base file outside if the mod. **");
    }
}
