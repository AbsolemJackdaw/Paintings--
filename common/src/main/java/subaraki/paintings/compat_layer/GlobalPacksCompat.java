package subaraki.paintings.compat_layer;

import com.electronwill.nightconfig.core.file.FileConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GlobalPacksCompat implements IPackRepoDiscoveryService{

	private static List<String> REQUIRED_RESOURCEACKS;

	@Override
	public Collection<String> getPackRepos() {
		if (REQUIRED_RESOURCEACKS != null) return REQUIRED_RESOURCEACKS;

		boolean isGlobalPackLoaded = false;
		try{
			Class.forName("net.dark_roleplay.gdarp.CommonClass");
			isGlobalPackLoaded = true;
		} catch (ClassNotFoundException e) {}

		REQUIRED_RESOURCEACKS = Collections.emptyList();

		if(isGlobalPackLoaded) {
			if (Files.exists(Path.of(".", "config", "global_data_and_resourcepacks.toml"))) {
				FileConfig cfg = FileConfig.builder(Path.of(".", "config", "global_data_and_resourcepacks.toml").toFile()).build();
				cfg.load();

				REQUIRED_RESOURCEACKS = cfg.<List<String>>getOptional("resourcepacks.required").orElse(Collections.emptyList());

				boolean hasSystemGlobal = cfg.<Boolean>getOptional("enable_system_global_packs").orElse(false);
				if (hasSystemGlobal) {
					if (REQUIRED_RESOURCEACKS == Collections.EMPTY_LIST) REQUIRED_RESOURCEACKS = new ArrayList<>();
					String userHome = System.getProperty("user.home");
					REQUIRED_RESOURCEACKS.add(Path.of(userHome, ".minecraft_global_packs").resolve("required_resourcepacks").toFile().getPath());
				}
			}
		}

		return REQUIRED_RESOURCEACKS;
	}
}
