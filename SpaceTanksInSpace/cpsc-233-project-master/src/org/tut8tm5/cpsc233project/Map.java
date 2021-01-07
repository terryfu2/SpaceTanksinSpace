package org.tut8tm5.cpsc233project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javafx.scene.paint.Color;

public class Map {

    /** each wall's size in pixels
     currently set so that one wall matches one square in the text renderer
    */
    public static final byte WALL_SIZE = 32;

    /** size of the map
    1. the unit here is tiles, not pixels or anything
    2. it is currently set so that the map's width and height make it fill up the whole window
    */
    public static final byte WIDTH = Renderer.WINDOW_WIDTH / WALL_SIZE;
    public static final byte HEIGHT = Renderer.WINDOW_HEIGHT / WALL_SIZE;

    //the amount of 1x1, 2x2, and 3x3 squares placed onto the map during generation, respectively
    private static final byte LITTLE_SQUARE_COUNT = 0;
    private static final byte MEDIUM_SQUARE_COUNT = 15;
    private static final byte LARGE_SQUARE_COUNT = 5;

    // the 2D array that represents the map, where 0 is empty and >0 is a wall
    public static final byte[][] tiles = new byte[WIDTH][HEIGHT];

    // generate a random byte inside a specified range
    // TODO: move this to some kind of utility class
    private static Random random = new Random();
    /**
    randomByte() - creates a random byte within a given range
    @param int minInclusive, int maxExclusive
    @return byte newInt
    **/
    private static byte randomByte(int minInclusive, int maxExclusive) {
        return (byte)Math.floor(minInclusive + random.nextDouble() * (maxExclusive - minInclusive));
    }

    // ArrayList containing Enemytanks
    private static ArrayList<EnemyTank> enemyTanks = new ArrayList<EnemyTank>();

    /**
    newLevel() - creates a new level
    @param none
    @return none
    **/
    public static void newLevel() {
        
        enemyTanks.clear();

        // random.setSeed(2);

        // create walls around the arena
        for (byte x = 0; x < WIDTH; x++) {
            for (byte y = 0; y < HEIGHT; y++) {
                tiles[x][y] = 0;

                if(x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                    tiles[x][y] = 1;
                }
            }
        }

        // place 1x1 squares as cover
        createCover(LITTLE_SQUARE_COUNT, 1);

        // same for 2x2
        createCover(MEDIUM_SQUARE_COUNT, 2);

        // 3x3
        createCover(LARGE_SQUARE_COUNT, 3);

        // spawn `enemyCount` enemies
        final float enemyCount = 4;
        for (int i = 0; i < enemyCount; i++) {
            byte randomX, randomY;
            do {
                randomX = randomByte(0, WIDTH);
                randomY = randomByte(0, HEIGHT);
            } while(getTileAt(randomX, randomY) != 0);

            EnemyTank enemyTank = new EnemyTank(new Vector2f(randomX, randomY).plus(new Vector2f(0.5f, 0.5f)).times(WALL_SIZE), 'E', 1);
            enemyTanks.add(enemyTank);
        }

        placePlayer();
    }
    /**
    placePlayer() - spawns in a player
    @param none
    @return none
    **/
    private static void placePlayer() {
        // ensure player does not spawn inside a wall by moving him
        byte randomX, randomY;
        do {
            randomX = randomByte(0, WIDTH);
            randomY = randomByte(0, HEIGHT);
        } while(getTileAt(randomX, randomY) != 0);

        Program.playerTank.moveTo((randomX + 0.5f) * WALL_SIZE, (randomY + 0.5f) * WALL_SIZE);
    }

    /**
    draw() - draws map
    @param none
    @return none
    **/
    public static void draw() {
        for (byte x = 0; x < WIDTH; x++) {
            for (byte y = 0; y < HEIGHT; y++) {
                if(getTileAt(x, y) > 0) {
                    if(Program.isTextBased) TextRenderer.draw(x * WALL_SIZE, y * WALL_SIZE, "[]");
                    else Renderer.drawRectangle(x * WALL_SIZE, y * WALL_SIZE, WALL_SIZE, WALL_SIZE, Program.playerTank.isDead() ? Color.DARKRED : Color.BLUE);
                }
            }
        }

        for (EnemyTank enemyTank : enemyTanks) {
            enemyTank.draw();
        }
    }
    
    /** 
    getTileAt() - get a tile at x and y
    @param long x, long y
    @return tiles[][] tile
    **/
    public static byte getTileAt(long x, long y) {
        return tiles[(byte)x][(byte)y];
    }
    
    /**
    createCover() -populates the map with squares of walls of a certain size
    @param int amount, int size
    @return none
    **/
    private static void createCover(int amount, int size) {
        for (int i = 0; i < amount; i++) {
            byte randomX, randomY;
            do {
                randomX = randomByte(0, WIDTH - size + 1);
                randomY = randomByte(0, HEIGHT - size + 1);
            } while(getTileAt(randomX, randomY) != 0);
            fillSquare(randomX, randomY, size);
        }
    }

    /**
    fillSquare() - used by the map generator to fill a square of a certain size at a certain position
    @param int xOrigin, int yOrigin, int size
    @return none
    **/
    private static void fillSquare(int xOrigin, int yOrigin, int size) {
        for (byte x = 0; x < size; x++) {
            for (byte y = 0; y < size; y++) {
                tiles[xOrigin + x][yOrigin + y] = 1;
            }
        }
    }

    /** The following raycasting algorithm is based on a paper I wrote in highschool: 
     https://drive.google.com/file/d/1ZfjmTVpsKJG_Ch363LnWJc5VhSyGMwgh/view?usp=sharing
     */
    /**
    incCeil() 
    @param float x
    @return int x
    **/
    private static int incCeil(float x) {
        return x % 1 == 0 ? Math.round(x) + 1 : (int)Math.ceil(x);
    }
    /**
    incFloor() 
    @param float x
    @return int x
    **/
    private static int incFloor(float x) {
        return x % 1 == 0 ? Math.round(x) - 1 : (int)Math.floor(x);
    }
    /**
    raycast()
    @param Vector2f position, Vector2f direction, float distance
    @return none
    **/
    public static RaycastHit raycast(Vector2f position, Vector2f direction, float distance) {
        Vector2f origin = position;

        float theta = direction.angle();

        boolean isNorth = direction.y > 0;
        boolean isEast = direction.x > 0;

        float cosAngle = (float) Math.cos(theta);
        float sinAngle = (float) Math.sin(theta);

        byte tile;

        Vector2f normal = new Vector2f(0, 0);

        int wx = -69;
        int wy = -69;

        do {
            int h, v;

            if(direction.x < 0) h = incFloor(position.x);
            else h = incCeil(position.x);

            h = isEast ? incCeil(position.x) : incFloor(position.x);
            v = isNorth ? incCeil(position.y) : incFloor(position.y);

            float hStep = (h - position.x) / cosAngle;
            float vStep = (v - position.y) / sinAngle;

            position = position.plus(direction.times(Math.min(hStep, vStep)));

            if (hStep < vStep) position = position.setX(Math.round(position.x));
            else position = position.setY(Math.round(position.y));

            if(origin.distanceTo(position) > distance) break;

            wx = isEast ? (int)Math.floor(position.x) : incFloor(position.x);
            wy = isNorth ? (int)Math.floor(position.y) : incFloor(position.y);

            tile = getTileAt(wx, wy);

            if(tile > 0) {
                if(position.x % 1 == 0) {
                    normal = new Vector2f(isEast ? -1 : 1, 0);
                } else {
                    normal = new Vector2f(0, isNorth ? -1 : 1);
                }
                
                return new RaycastHit(position, normal, tile, new Vector2f(wx, wy));
            }

        } while(!(origin.distanceTo(position) > distance));
        
        return null;
    }
    /**
    update()
    @param float dt
    @return none
    **/
    public static void update(float dt) {
        // backwards for loop to allow removal of objects in collection
        for (int i = enemyTanks.size() - 1; i >= 0; i--) {
            EnemyTank tank = enemyTanks.get(i);
            tank.update(dt);

            if(tank.isDead()) {
                enemyTanks.remove(i);
            }
        }

        if(enemyTanks.size() == 0) {
            newLevel();
        }
    }
    /**
    getEnemyTanks() - getter
    @param none
    @return ArrayList<EnemyTank> tank
    **/
    public static ArrayList<EnemyTank> getEnemyTanks() {
        return new ArrayList<EnemyTank>(enemyTanks);
    }

//test
}
