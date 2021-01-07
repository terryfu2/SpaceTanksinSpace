package org.tut8tm5.cpsc233project;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** renderer, to be used in the draw() functions of anything
 (but can be used outside of them)
 */

public class Renderer {

    // width and height of the game
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    // used for drawing on the window
    private static GraphicsContext graphics;

    // creates canvas and graphics context
    public static void initialize(Group root) {
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(canvas);

        graphics = canvas.getGraphicsContext2D();        
        graphics.setFill( Color.RED );
        graphics.setStroke( Color.BLACK );
        graphics.setLineWidth(1);
    }

    // clears the screen
    public static void clear() {
        graphics.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // draws a rectangle at (x, y) with a certain width and height
    public static void drawRectangle(double x, double y, double width, double height, Color colour) {
        if(colour != null) graphics.setFill(colour);

        graphics.fillRect(x, y, width, height);
    }

    // draws a circle at (x, y) with a certain radius
    public static void drawCircle(double x, double y, double radius, Color colour) {
        if(colour != null) graphics.setFill(colour);

        graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    // draws text with (x, y) being the bottom left
    public static void drawString(double x, double y, String text, Color colour) {
        if(colour != null) graphics.setFill(colour);
        graphics.fillText(text, x, y);
    }
    
    //test
}