package subaraki.paintings.utils;

import net.minecraft.resources.ResourceLocation;
import subaraki.paintings.Paintings;

public class PaintingEntry {

    private final String refName;
    private int sizeX = 16;
    private int sizeY = 16;
    private int animY = 16;

    public PaintingEntry(String refName, int sizeX, int sizeY, int animatedY) {
        this.refName = refName;
        if (sizeX > 0)
            this.sizeX = sizeX;
        if (sizeY > 0)
            this.sizeY = sizeY;

        if (animatedY > 0)
            this.animY = animatedY;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public int getAnimY() {
        return animY;
    }

    public String getNameSpace() {
        return refName.contains(":") ? refName.split(":")[0] : Paintings.MODID;
    }

    public String getPaintingName() {
        return refName.contains(":") ? refName.split(":")[1] : refName;
    }

    public ResourceLocation getResLoc() {
        return new ResourceLocation(getNameSpace(), getPaintingName());
    }
}
