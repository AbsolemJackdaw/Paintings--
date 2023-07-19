package subaraki.paintings.utils;

import subaraki.paintings.Paintings;

import java.util.ServiceLoader;

public class Services {

    public static final IConfigHelper CONFIG = load(IConfigHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Paintings.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
