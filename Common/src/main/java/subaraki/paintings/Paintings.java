package subaraki.paintings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import subaraki.paintings.utils.PaintingUtility;

public class Paintings {

    public static final String MODID = "paintings";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final PaintingUtility UTILITY = new PaintingUtility();
}
