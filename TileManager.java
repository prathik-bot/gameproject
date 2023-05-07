// Prathik Kumar and Hrithik Mallireddy
// 5/4/2023
// TileManager.java (Explorador Español)
// Working on:
	// Week 2: This class manages tiles, we can load tiles based on numbers in a txt file
		// We are loading tiles and the text file system is working well.
// Practicing: ImageIO, Components(JButtons, MenuBar), and different layouts, like border,
// grid, and flow layout. Practicing using handler classes as well

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.Scanner;

/// Prathik Kumar did this entire class
public class TileManager 
{
	private Image[] tiles;
    private int mapTileNum[][];
    private GamePanel gp;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;

    private int mapX, mapY;
    private int screenX, screenY;

    //Map settings to implement camera layout

    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    private final int worldWidth = 48 * maxScreenCol; //TIle Size times Max Col width
    private final int worldCol = 50;
    private final int worldRow = 50;

    private int gamePlayerWorldX = 48 * 23;
    private int gamePlayerWorldY = 48 * 21;

    private final int gamePanelScreenWidth = 48 * maxScreenCol; // 768 pixels
    private final int gamePanelScreenHeight = 48 * maxScreenRow; // 576 pixels

    private int gamePlayerScreenX = gamePanelScreenWidth/2 - (48/2);
    private int gamePlayerScreenY = gamePanelScreenHeight/2 - (48/2);
    
    public TileManager(GamePanel gp) 
    {
        this.gp = gp;
        tiles = new Image[10];
        mapTileNum = new int[worldCol][worldRow];
        loadMap("world01.txt");
        getTileImage();
    }

    public void getTileImage() 
    {

        tiles[0] = gp.getImage("grass01.png");
        tiles[1] = gp.getImage("wall.png");
        tiles[2] = gp.getImage("water00.png");

        tiles[3] = gp.getImage("earth.png");

        tiles[4] = gp.getImage("tree.png");
        tiles[5] = gp.getImage("sand.png");
        tiles[6] = gp.getImage("coin.png"); 
    }

    //Reads Text file from map file to load tiles for water, grass & wall.
    //Scans text file line by line and get that number to load tiles based on index
    public void loadMap(String filePath)
    {
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
            //Logic to go over all the elements in the map file and get the index and load the images
            int worldX = wordCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            //This setting is to know where on the screen we need to draw the tile.
            int screenX = worldX - gp.worldX + gamePlayerScreenX;
            int screenY = worldY - gp.worldY + gamePlayerScreenY;
            if (worldX + gp.tileSize > gp.worldX - gp.screenX && worldX - gp.tileSize < gp.worldX + gp.screenX && 
				worldY + gp.tileSize > gp.worldY - gp.screenY && worldY - gp.tileSize < gp.worldY + gp.screenY)
				{
					g2.drawImage(tiles[tileNum], screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
            wordCol++;
            if (wordCol == maxWorldCol) {
                wordCol =0;
                worldRow++;
            }
        }

    }
}
