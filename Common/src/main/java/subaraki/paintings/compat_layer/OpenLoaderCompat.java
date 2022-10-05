package subaraki.paintings.compat_layer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OpenLoaderCompat implements IPackRepoDiscoveryService{
	private final Path configDir = Path.of("configs");

	@Override
	public Collection<String> getPackRepos() {
		Set<String> staticFolders = new HashSet();

		final File configFile = configDir.resolve("advanced_options.json").toFile();

		if(configFile.exists()){
			try(FileReader reader = new FileReader(configFile)){
				JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
				if(config.has("resourcePacks ")){
					JsonObject packConfig = config.get("resourcePacks").getAsJsonObject();
					if(packConfig.has("enabled") && packConfig.get("enabled").getAsBoolean() && packConfig.has("additionalFolders"))
						packConfig.get("additionalFolders").getAsJsonArray().forEach(elem -> staticFolders.add(elem.getAsString()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return staticFolders;
	}
}
