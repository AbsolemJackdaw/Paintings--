package subaraki.paintings.handler;

import java.util.HashMap;

import net.minecraftforge.common.util.EnumHelper;
import subaraki.paintings.fake_enum.Painting;
import subaraki.paintings.mod.MorePaintings;

public class PaintingPattern {

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

    public static PaintingPattern instance = null;

    private static Integer enumCounter = 0;

    private String type = "subaraki:pattern";
    private String name = null;
    private String[] pattern = null;
    private HashMap<String, Size> key = null;

    public void loadPatterns() {
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
                    this.addPatternSection(size.width, size.height, offsetX, offsetY);
                    this.updatePattern(size.width, size.height, offsetX, offsetY);
                    count++;
                } else {
                    StringBuilder error = new StringBuilder();
                    error.append(String.format("Error processing pattern at offset: %d, %d\n", offsetX, offsetY));
                    MorePaintings.log.error(error);
                }
            }
        }

        MorePaintings.log.info(String.format("%d paintings found in %s/%s.", count, this.type, this.name));
    }

    public Size getSize() {
    	int x =  this.pattern[0].length();
    	int y = this.pattern.length;

    	return new Size(x,y);
    }

    /**
     * Append the art enum with the painting specification
     *
     * @param sizeX   Width in blocks
     * @param sizeY   Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void addPatternSection(Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {

    	int count = PaintingPattern.enumCounter++;
    	
    	new Painting(sizeX*16, sizeY*16, offsetX*16, offsetY*16, String.format("EnumArt_%d", count)).enlist();
        
        MorePaintings.log.debug(String.format("Added %d x %d painting at %d, %d.", sizeX, sizeY, offsetX, offsetY));
    }

    /**
     * Remove (replace with ' ') a region of the pattern; used to remove a read-in painting
     * Removes any painting entry from the file that is considered out of bounds by the file's entries
     * 
     * @param sizeX   Width in blocks
     * @param sizeY   Height in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void updatePattern(Integer sizeX, Integer sizeY, Integer offsetX, Integer offsetY) {
        if (this.pattern[offsetY].length() < offsetX + sizeX) {
        	MorePaintings.log.warn("Tried adding a painting that extends beyond pattern dimensions. Removing the Entry !");
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
