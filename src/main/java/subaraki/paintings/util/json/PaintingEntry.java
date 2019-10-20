package subaraki.paintings.util.json;

public class PaintingEntry {

    private int sizeX = 16;
    private int sizeY = 16;

    private String refName;

    public PaintingEntry(String refName, int sizeX, int sizeY, int square) {
        
        this.refName = refName;
        
        if(sizeX > 0)
            this.sizeX = sizeX;
        if(sizeY > 0)
            this.sizeY = sizeY;
        if(square > 0 )
            this.sizeY = this.sizeX = square;
    }

    public int getSizeX() {

        return this.sizeX;
    }

    public int getSizeY() {

        return this.sizeY;
    }

    public String getRefName() {

        return this.refName;
    }
}
