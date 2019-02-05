package subaraki.paintings.mod.server.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraftforge.fml.common.Loader;
import subaraki.paintings.mod.Paintings;

public class CommonProxy {
    public void registerRenderInformation() {
    }

    public JsonObject getPatternJson(String patternName) {

        File configDirectory = Loader.instance().getConfigDir();
        Gson gson = new Gson();

        // Server side patterns can be stored in:
        String[] validPatternDirectories = {"morepaintings", "paintings--", "Paintings--"};
        patternName = patternName + ".json";

        try {
            InputStream in = null;

            // Look for a pattern in the config directory
            for (String child : validPatternDirectories) {
                File patternDirectory = new File(configDirectory, child);

                if (patternDirectory.exists()) {
                    File patternFile = new File(patternDirectory, patternName);

                    if (patternFile.exists() && patternFile.canRead()) {
                        in = new FileInputStream(patternFile);
                        break;
                    }
                }
            }

            // Fallback to a mod resource
            if (in == null) {
                in = Paintings.class.getResourceAsStream("/assets/" + Paintings.RESOURCE_DOMAIN + "/patterns/" + patternName);
            }

            if (in != null) {
                BufferedReader resourceReader = new BufferedReader(new InputStreamReader(in));
                return gson.fromJson(resourceReader, JsonElement.class).getAsJsonObject();
            }

        } catch (FileNotFoundException e) {
            Paintings.log.error(e.getLocalizedMessage());
        } catch (IllegalStateException e) {
            Paintings.log.error(e.getLocalizedMessage());
        }

        return null;
    }
}
