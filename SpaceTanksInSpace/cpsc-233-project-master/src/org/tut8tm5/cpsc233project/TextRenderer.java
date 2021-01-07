// Obsolete and may be deleted

package org.tut8tm5.cpsc233project;

/** emulates a very low-resolution window, but through text
 */

public class TextRenderer {

    // simulated window's width and height
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    // how many simulated pixels each cell corresponds to
    public static final int CELL_TO_PIXELS = 32;

    // the width and height of the buffer that is drawn to
    public static final int BUFFER_WIDTH = WINDOW_WIDTH / CELL_TO_PIXELS;
    public static final int BUFFER_HEIGHT = WINDOW_HEIGHT / CELL_TO_PIXELS;

    // the buffer: any drawing operation writes to this buffer and the buffer is used to display an image in the display() function
    private static char[][] buffer;

    /** 
    clear() - the screen must be cleared (and this function must be called) each "frame"
    @param none
    @return none
    **/
    public static void clear() {
        buffer = new char[BUFFER_WIDTH * 2][BUFFER_HEIGHT];

        for (int y = 0; y < BUFFER_HEIGHT; y++) {
            for (int x = 0; x < BUFFER_WIDTH * 2; x++) {
                buffer[x][y] = ' ';
            }
        }
    }

    /** 
    draw() - takes in pixel coordinates and draws a cell at the approximate position.
    @param int x,int y, String newCell
    @return none
    */

    public static void draw(int x, int y, String newCell) {
        drawDirectly(Math.floorDiv(x, CELL_TO_PIXELS), Math.floorDiv(y, CELL_TO_PIXELS), newCell);
    }

    /**
    drawDirectly() -draws directly to the buffer using buffer coordinates
    @param int x, int y, String newCell
    @return none
    **/
    private static void drawDirectly(int x, int y, String newCell) {
        for (int i = 0; i < newCell.length(); i++) {
            if(x < 0 || y < 0 || (x * 2 + i) >= BUFFER_WIDTH * 2 || y >= BUFFER_HEIGHT) continue;
            buffer[x * 2 + i][y] = newCell.charAt(i);
        }
    }

    /** 
    display() - displays the buffer to the screen
    @param none
    @return none
    **/
    public static void display() {
        String toDisplay = "";
        for (int y = 0; y < BUFFER_HEIGHT; y++) {
            for (int x = 0; x < BUFFER_WIDTH * 2; x++) {
                toDisplay += buffer[x][y];
            }
            toDisplay += "\n"; // insert line break after each row
        }
        System.out.println(toDisplay);
    }
}
