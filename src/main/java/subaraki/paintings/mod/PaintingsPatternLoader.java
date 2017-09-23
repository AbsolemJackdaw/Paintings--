package subaraki.paintings.mod;

import java.util.HashMap;
import java.util.IllegalFormatException;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLLog;

public class PaintingsPatternLoader {

    private static Integer enumCounter = 0;

    private String type;
    private String name;
    private String[] pattern;
    private HashMap<String, HashMap<String, Integer>> key;

    public void loadPatterns() throws IllegalFormatException {
        Integer width = this.pattern[0].length();
        Integer height = this.pattern.length;

        try {
            for (int offsetY = 0; offsetY < height; offsetY++) {
                for (int offsetX = 0; offsetX < width; offsetX++) {
                    String symbol = this.pattern[offsetY].substring(offsetX, offsetX + 1);

                    if (symbol.equals(" ")) {
                        continue;
                    }

                    HashMap size = this.key.get(symbol);
                    if (size != null) {
                        Integer sizeX = this.key.get(symbol).get("x");
                        Integer sizeY = this.key.get(symbol).get("y");
                        FMLLog.log.debug(String.format("Adding %d x %d painting at %d, %d", sizeX, sizeY, offsetX, offsetY));
                        this.addPatternSection(sizeX, sizeY, offsetX, offsetY);
                        this.updatePattern(sizeX, sizeY, offsetX, offsetY);
                    } else {
                        StringBuilder error = new StringBuilder();
                        error.append(String.format("Error processing pattern at offset: %d, %d\n", offsetX, offsetY));
                        error.append("Pattern:");
                        for (int i=0; i<this.pattern.length; i++) {
                            error.append(String.format("    %s\n", this.pattern[i]));
                        }
                        error.append(String.format("Key:\n    %s", this.key.toString()));
                        FMLLog.log.error(error);
                    }
                }
            }
        } catch (Exception e) {
            FMLLog.log.error(e);
        }
    }

    /**
     * Append the art enum with the painting specification
     * @param sizeX Width in blocks
     * @param sizeY Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void addPatternSection(Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {

        EnumHelper.addArt(
                String.format("EnumArt_%d", PaintingsPatternLoader.enumCounter++),
                String.format("ptg%3d%3d", offsetX, offsetY),
                sizeX * 16,
                sizeY * 16,
                offsetX * 16,
                offsetY * 16
        );
    }

    /**
     * Remove (replace with ' ') a region of the pattern, used to remove a read-in painting
     * @param sizeX Width in blocks
     * @param sizeY Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void updatePattern(Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {

        for (int row = offsetY; row < offsetY + sizeY; row++) {
            byte[] rowBytes = this.pattern[row].getBytes();
            for (int column = offsetX; column < offsetX + sizeX; column++) {
                rowBytes[column] = ' ';
            }
            this.pattern[row] = new String(rowBytes);
        }
    }
}
