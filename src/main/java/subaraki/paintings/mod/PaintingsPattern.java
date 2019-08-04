package subaraki.paintings.mod;

import java.util.HashMap;

import com.mcf.davidee.paintinggui.wrapper.PaintingWrapper;
import net.minecraft.util.ResourceLocation;

public class PaintingsPattern {

    public class Size {
        public Integer width;
        public Integer height;

        Size(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }

        public String toString() {
            return String.format("[%d, %d]", this.width, this.height);
        }
    }

    public static PaintingsPattern instance = null;
    public ResourceLocation texture = null;

    private String type = "subaraki:pattern";
    private String name = null;
    private String[] pattern = null;
    private HashMap<String, Size> key = null;
    private HashMap<Size, Integer> sizeCounts = new HashMap<>();

    public void parsePattern() {
        Integer width = this.pattern[0].length();
        Integer height = this.pattern.length;
        Integer count = 0;

        for (int offsetY = 0; offsetY < height; offsetY++) {
            for (int offsetX = 0; offsetX < width; offsetX++) {
                String symbol = this.pattern[offsetY].substring(offsetX, offsetX + 1);

                if (symbol.equals(" ")) {
                    continue;
                }

                Size size = this.key.get(symbol);
                if (size != null) {

                    // Paintings are named by dimensions, use an index value to include offsets
                    int sizeCount = sizeCounts.get(size) != null ? sizeCounts.get(size) : 0;

                    this.addPainting(sizeCount, size.width, size.height, offsetX, offsetY);
                    this.updatePattern(size.width, size.height, offsetX, offsetY);

                    sizeCounts.put(size, ++sizeCount);
                    count++;
                } else {
                    StringBuilder error = new StringBuilder();
                    error.append(String.format("Error processing pattern at offset: %d, %d\n", offsetX, offsetY));
                    Paintings.log.error(error);
                }
            }
        }

        Paintings.log.info(String.format("%d paintings found in %s/%s.", count, this.type, this.name));
    }

    public Size getSize() {
        return new Size(
                this.pattern[0].length(),
                this.pattern.length
        );
    }

    /**
     * Append the art enum with the painting specification
     *
     * @param sizeX   Width in blocks
     * @param sizeY   Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void addPainting(Integer sizeIndex, Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {

        // By using the sizeIndex last in the painting name, shapes will be grouped
        String name = String.format("p++%dx%d#%d", sizeX, sizeY, sizeIndex);
    	new PaintingWrapper(name,
    			sizeX * 16,
                sizeY * 16,
                offsetX * 16,
                offsetY * 16
        ).append();

        Paintings.log.info(String.format("Added %s at %d, %d.", name, offsetX, offsetY));
    }

    /**
     * Remove a region of the pattern, used to remove a painting that has already been parsed
     *
     * @param sizeX   Width in blocks
     * @param sizeY   Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void updatePattern(Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {
    	if (this.pattern[offsetY].length() < offsetX + sizeX) {
            Paintings.log.warn("Added painting extends beyond pattern dimensions.");
            return;
        }

        for (int row = offsetY; row < offsetY + sizeY; row++) {
            byte[] rowBytes = this.pattern[row].getBytes();
            for (int column = offsetX; column < offsetX + sizeX; column++) {
                rowBytes[column] = ' ';
            }
            this.pattern[row] = new String(rowBytes);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pattern: " + this.type + "/" + this.name + "\n");
        for (int i = 0; i < this.pattern.length; i++) {
            sb.append(String.format("    %s\n", this.pattern[i]));
        }
        sb.append(String.format("Key:\n    %s", this.key.toString()));

        return sb.toString();
    }
}
