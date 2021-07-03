package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void addHexagon(TETile[][] world, Position p, int len, TETile t) {
        // draw a hexagon upward,  start from position p, with length len
        // 2*len rows in total
        int totalRow = 2*len;
        for (int yi = 0; yi < totalRow; yi++) {
            int xp = p.x + xOffset(len, yi);
            int yp = p.y + yi;
            Position pStart = new Position(xp,yp);
            int width = rowWidth(len,yi);

            addRow(world, pStart, width, t);
        }
    }

    private static void addRow(TETile[][] world, Position pStart, int width, TETile t) {
        // 画一行，从位置pstart开始，宽度width
        for (int i = 0; i < width; i++) {
            int x = pStart.x + i;
            int y = pStart.y;
            world[x][y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    private static int xOffset(int len, int i) {
        /** 返回 边长为len 第i行开始的位置到第0行的横向距离 */
        int effectiveI = i;
        if (i >= len) {
            effectiveI = 2*len - 1 - i;
        }
        return -effectiveI;
    }

    private static int rowWidth(int len, int i) {
        /** 返回 边长为len 第i行的宽度 */
        int effectiveI = i;
        if (i >= len) {
            effectiveI = 2*len - 1 - i;
        }
        return len + 2*effectiveI;
    }

    private static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // draw a hexagon
        addHexagon(world,new Position(10,15), 3,Tileset.WALL);

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 20; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }


        // draws the world to the screen
        ter.renderFrame(world);
    }

}
