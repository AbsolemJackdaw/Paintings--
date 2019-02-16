package subaraki.paintings.mod;

import com.google.gson.JsonObject;

public interface IPaintingsProxy {

    void registerRenderInformation();
    JsonObject getPatternFile(String patternName);
    void configurePaintingsGuiButtonTexture();

}
