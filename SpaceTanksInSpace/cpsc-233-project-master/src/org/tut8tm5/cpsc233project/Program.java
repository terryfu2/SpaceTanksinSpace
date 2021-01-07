/*

    About the feedback which said the following: 
    
        Figure out a way to not make static variables in the Program.java file! Static 
        variables and methods are 'shared' between all objects of that class. For example, 
        since the Program class is essentially a static class (all variable and methods 
        are static), we would not be able to have more than one instance of the game running!

    This is precisely the reason why all the variables are static: there will never be 
    a reason to have two instances of the class `Program` in the code. To run multiple 
    instances of the game, we would simply run the `java` command twice. 

    If we replaced the static class Program with a non-static class Program, it would simply
    be pretty much the same code, but you'd need to do 

        public class Program {
            Program instance;

            ...
            
            public static void main(String[] args) {
                instance = new Program();
                instance.launch(args);
            }
            
            ...
        }
        
    This is fundamentally against object-oriented principles because object-oriented programming
    was made so you could have multiple instances of a certain "type" of thing. "Car", for example,
    is a good type. "Animal" too. But a non-static type named "ProfessorWu" would make no sense--
    there is only one Professor Wu, in fact, making ProfessorWu into a non-static is actually
    counter-productive. There should never be multiple instances of Professor Wu. So we should
    make such a class static.

    The same thing goes for this Program class. This class represents our entire game, the whole
    of "Space Tanks in Space". Why would there ever be two "Space Tanks in Space" inside "Space 
    Tanks in Space" ? So this is why we have chosen to leave it static.

*/

package org.tut8tm5.cpsc233project;

import java.util.Scanner;

import org.tut8tm5.cpsc233project.TankBase.TankType;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Whoever's in charge of handling input should probably have a look at this:
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 * specifically, the "Handling User Input" section here is another article to
 * read if you need to understand game loops better:
 * https://code.tutsplus.com/tutorials/understanding-the-game-loop-basix--active-8510
 */

// minimal game skeleton
public class Program extends Application {

    // whether the game should continue
    private static boolean isRunning = false;

    // scanner for input
    private static Scanner scanner = new Scanner(System.in);

    // ArrayList containing bullets
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    // self explanatory
    public static PlayerTank playerTank;
    private static Stage mainWindow;

    public static boolean isTextBased = false;

    // main() function
    public static void main(String[] args) {
        if (args.length > 0 && args[0].trim().toLowerCase().equals("-text")) {
            isTextBased = true;
            textLoop();
        } else {
            launch(args);
        }
    }
    /**
    textLoop()
    @param none
    @return none
    **/
    private static void textLoop() {
        initialize(null);
        textDraw();

        while (isRunning) {
            float dt = 1f;

            if (!isRunning) break;
            update(dt);

            if (!isRunning) break;
            textDraw();
        }

        dispose();
        System.exit(0); // for some reason the program wouldnt exit without this line
    }
    /**
    textDraw()
    @param none
    @return none
    **/
    public static void textDraw() {
        TextRenderer.clear();
        Map.draw();

        playerTank.draw();

        for (Bullet b : bullets) {
            b.draw();
        }

        TextRenderer.display();
    }

    @Override
    /**
    start() - starts the gameloop
    @param Stage stage
    @return none
    **/
    public void start(Stage stage) throws Exception {
        initialize(stage);

        AnimationTimer gameLoop = new AnimationTimer() {
            long lastFrameTime = System.nanoTime();

            public void handle(long currentFrameTime) {
                float dt = (currentFrameTime - lastFrameTime) / 1000000000.0f;
                update(dt);
                draw(dt);

                if (!isRunning) {
                    dispose();
                }

                lastFrameTime = currentFrameTime;
            }
        };

        gameLoop.start();
        stage.show();
    }

    /**
    initialize() - runs before the game starts and sets everything up
    @param Stage stage
    @return none
    **/
    public static void initialize(Stage stage) {
        Scene scene = null;

        if (isTextBased)
            TextRenderer.clear();
        else {
            mainWindow = stage;
            mainWindow.setTitle("Space Tanks in Space");
            mainWindow.setResizable(false);

            Group root = new Group();
            scene = new Scene(root);
            mainWindow.setScene(scene);

            Renderer.initialize(root);
        }


        playerTank = new PlayerTank(new Vector2f(10, 10).times(Map.WALL_SIZE), // arbitrary spawnpoint
                'x', // what the tank will look like in the text based demo
                0f); // starting turret rotation of 0 degrees

        Map.newLevel();

        isRunning = true;

        if (isTextBased)
            return;
        // Event Handler for mouse clicks--- Creates a bullet in a specified direction
        scene.setOnMouseClicked(playerTank.mouseClickEventHandler);

        // Event Handler for keyboard Movements--
        scene.setOnKeyPressed(playerTank.keyPressedEventHandler);
    }

    /** 
        updates the game every frame and checks to see if the bullet bounces has
        reached number of bounces then removes it from the array
        @param float dt
        @return none
     **/
    public static void update(float dt /* short for delta-time */) {

        if (isTextBased) {
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("exit")) {
                isRunning = false;
                return;
            }

            if (input.startsWith("move")) {
                String[] segments = input.split(" ");
                float x = Float.parseFloat(segments[1]);
                float y = Float.parseFloat(segments[2]);
                Vector2f movement = new Vector2f(x, y);
                for (int i = 0; i < Math.ceil(movement.length() / 10f); i++) {
                    Vector2f toMove = movement.direction().times(10f);
                    playerTank.move(toMove.x, toMove.y);
                }
            }

            if (input.startsWith("aim")) {
                String[] segments = input.split(" ");
                float t = Float.parseFloat(segments[1]);
                playerTank.setTurretRotation((float) -Math.toRadians(t));
                System.out.println("Aimed.");
            }

            if (input.startsWith("shoot")) {
                // float angle = playerTank.getTurretRotation();
                // Vector2f bulletDirection = new Vector2f(angle);
                // Vector2f bulletSpawnPoint =
                // playerTank.getCurrentPos().plus(bulletDirection).over(Map.WALL_SIZE);
                // Bullet bullet = new Bullet(bulletSpawnPoint.x, bulletSpawnPoint.y, angle);
                // bullets.add(bullet);

                playerTank.shoot();

                /*
                 * float enemyAngle = 10; Vector2f bulletDirectionE = new Vector2f(enemyAngle);
                 * Vector2f bulletSpawnPointE =
                 * enemyTank.getCurrentPos().plus(bulletDirectionE).over(Map.WALL_SIZE); Bullet
                 * bulletE = new Bullet(bulletSpawnPointE.x, bulletSpawnPointE.y, enemyAngle);
                 * bulletsE.add(bulletE);
                 */
                // for(EnemyTank tank : enemyTanks) {
                // tank.setTurretRotation(1f);
                // tank.shoot();
                // }
            }
        }

        playerTank.update(dt);
        Map.update(dt);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update(dt);
            // for (EnemyTank enemyTank : enemyTanks) {
            //     /*
            //      * if (b.collidesWith(enemyTank)) { Program.bullets.remove(b);
            //      * Program.enemyTanks.remove(enemyTank); }
            //      */
            // }
            
            b.checkCollisions(playerTank);

            if (b.bounces == b.maximumBounces) {
                Program.bullets.remove(b);
            }

        }

    }
    
    /**
    draw() - does all the game's drawing each frame
    @param float dt
    @return none
    **/
    public static void draw(float dt) {
        Renderer.clear();

        Map.draw();

        playerTank.draw();

        for (Bullet b : bullets) {
            b.draw();
        }

        Renderer.drawString(20, 20, "FPS: " + Math.round(1f / dt), Color.WHITE);
    }

    /**
    dispose() - called at the end of the game's lifetime to free resources (such as textures,sounds, filestreams, etc)
    @param none
    @return none
    **/
    public static void dispose() {
        if (!isTextBased)
            mainWindow.close();
        scanner.close();
    }

    // test

}
