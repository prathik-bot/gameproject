import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.Scanner;

public class TileManager {
    public Image[] tiles;
    public int mapTileNum[][];
    GamePanel gp;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;

    private int mapX, mapY;
    private int screenX, screenY;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = 48 * maxScreenCol; //TIle Size times Max Col width
    private final int worldCol = 50;
    private final int worldRow = 50;

    private int gamePlayerWorldX = 48 * 23;
    private int gamePlayerWorldY = 48 * 21;

    private final int gamePanelScreenWidth = 48 * maxScreenCol; // 768 pixels
    private final int gamePanelScreenHeight = 48 * maxScreenRow; // 576 pixels

    private int gamePlayerScreenX = gamePanelScreenWidth/2 - (48/2);
    private int gamePlayerScreenY = gamePanelScreenHeight/2 - (48/2);
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Image[10];
        mapTileNum = new int[worldCol][worldRow];
        loadMap("world01.txt");
        getTileImage();
    }

    public void getTileImage() {

        tiles[0] = gp.getImage("grass01.png");
        tiles[1]  = gp.getImage("wall.png");
        tiles[2] = gp.getImage("water00.png");

        tiles[3] = gp.getImage("earth.png");

        tiles[4] = gp.getImage("tree.png");
        tiles[5] = gp.getImage("sand.png");

    }

    public void loadMap(String filePath){
        try
        {
            File fileText = new File(filePath);
            Scanner fileScanner = new Scanner(fileText);
            int col = 0;
            int row = 0;
            while (col < maxWorldCol && row < maxWorldRow)
            {
                String line = fileScanner.nextLine();

                while (col < maxWorldRow)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            fileScanner.close();
        } catch (Exception e)
        {
            System.out.println("ERROR LOADING FILE");
        }
    }
    public void draw(Graphics2D g2) {
        int wordCol = 0; //for bigger map drawing
        int worldRow = 0;
        while (wordCol < maxWorldCol && worldRow < maxWorldRow) {
            int tileNum  = mapTileNum[wordCol][worldRow];
            //Logic to go over all the elemnts in the map file and get the index and load the images
            int worldX = wordCol * 48;
            int worldY = worldRow * 48;

            //This setting is to know where on the screen we need to draw the tile.
            int screenX = worldX - gp.worldX + gamePlayerScreenX;
            int screenY = worldY - gp.worldY + gamePlayerScreenY;
            g2.drawImage(tiles[tileNum], screenX, screenY, 100, 100, null);
            wordCol++;
            //x += 48;
            if (wordCol == maxWorldCol) {
                wordCol =0;
                //x = 0;
                worldRow++;
                //y+= 48;
            }
        }

    }
}
