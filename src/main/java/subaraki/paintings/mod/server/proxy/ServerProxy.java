package subaraki.paintings.mod.server.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraftforge.fml.common.Loader;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.IPaintingsProxy;
import subaraki.paintings.mod.Paintings;

public class ServerProxy implements IPaintingsProxy {
    public void registerRenderInformation() {}

    public JsonObject getPatternFile(String patternName) {

        File configDirectory = Loader.instance().getConfigDir();
        Gson gson = new Gson();

        // Server side patterns can be stored in:
        String[] validPatternDirectories = {"morepaintings", "paintings--", "Paintings--"};
        String patternFileName = patternName + ".json";

        try {
            InputStream in = null;

            // Look for a pattern in the config directory
            for (String child : validPatternDirectories) {
                File patternDirectory = new File(configDirectory, child);

                if (patternDirectory.exists()) {
                    File patternFile = new File(patternDirectory, patternFileName);

                    if (patternFile.exists() && patternFile.canRead()) {
                        in = new FileInputStream(patternFile);
                        Paintings.log.info(patternFileName + " found in " + patternDirectory);
                        break;
                    }
                }
            }

            // Try patterns supplied by Paintings++
            if (in == null && Arrays.asList(ConfigurationHandler.BUILTIN_PATTERNS).contains(patternName)) {
                in = Paintings.class.getResourceAsStream("/assets/" + Paintings.RESOURCE_DOMAIN + "/patterns/" + patternFileName);
                Paintings.log.info("Loading built-in " + patternName + " pattern");
            } else {
                in = Paintings.class.getResourceAsStream("/assets/" + Paintings.RESOURCE_DOMAIN + "/patterns/vanilla.json");
                Paintings.log.info("Unable to locate " + patternFileName + ". Defaulting to vanilla pattern.");
            }

            if (in == null) {
                Paintings.log.error("Unable to find a valid pattern. Use of paintings will crash you Minecraft instance.");
            } else {
                BufferedReader resourceReader = new BufferedReader(new InputStreamReader(in));
                return gson.fromJson(resourceReader, JsonElement.class).getAsJsonObject();
            }

        } catch (Exception e) {
            Paintings.log.error(e.getLocalizedMessage());
        }

        return null;
    }

    public void configurePaintingsGuiButtonTexture() {}
}
