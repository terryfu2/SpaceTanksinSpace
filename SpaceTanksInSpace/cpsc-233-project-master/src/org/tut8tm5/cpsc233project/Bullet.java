package org.tut8tm5.cpsc233project;

import javafx.scene.paint.Color;

//Bullet class that represents the bullets the tanks will shoot

public class Bullet implements Drawable {

    Vector2f direction;
    Vector2f position;
    float speed = 5;
    float radius = 5;
    float diameter = radius * 2;
    int bounces = 0;
    int maximumBounces = 5;
    int WALL_SIZE = 32;
    int OFFSET = 10;
    private boolean isFriendly;

    /**
     * Constructor for class
     * 
     * @param float currentX,float currentY,float angle updates direction and
     *              position
     * @return none
     **/
    public Bullet(boolean isFriendly, float currentX, float currentY, float angle) {
        this.isFriendly = isFriendly;
        direction = new Vector2f(angle);
        position = new Vector2f(currentX, currentY);

    }

    /**
     * Alternate Constructor
     * 
     * @param Vector2f position, float angle updates direction and position
     * @return none
     **/
    public Bullet(boolean isFriendly, Vector2f position, float angle) {
        this.isFriendly = isFriendly;
        direction = new Vector2f(angle);
        this.position = position;
    }

    public void setFriendly(boolean isFriendly) {
        this.isFriendly = isFriendly;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void destroy() {
        Program.bullets.remove(this);
    }

    /**
     * draw() - draws the bullet at its current location
     * 
     * @param none
     * @return none
     **/
    public void draw() {
        if (Program.isTextBased)
            TextRenderer.draw((int) (position.x * Map.WALL_SIZE), (int) (position.y * Map.WALL_SIZE), ".");
        else {
            Color colour = isFriendly ? Color.CHARTREUSE : Color.BLACK;
            Renderer.drawCircle(position.x * Map.WALL_SIZE, position.y * Map.WALL_SIZE, radius, colour);
        }
    }

    /**
     * update() - checks to see if bullet contacts a wall and tells it to reflect
     * off of it and increments the number of bounces of the bullet
     * 
     * @param float dt
     * @return none
     **/
    public void update(float dt) {

        RaycastHit hitData = Map.raycast(position, direction, Math.max(radius / Map.WALL_SIZE, speed * dt));

        if (hitData != null) {

            this.bounces = this.bounces + 1;
            position = hitData.point;
            direction = direction.reflectedOff(hitData.normal);

        } else {
            position = position.plus(direction.walk(speed * dt));
        }

        if (bounces == maximumBounces) {
            destroy();
        }
    }

    public boolean collidesWith(TankBase tB) {
        if (this.position.x * WALL_SIZE < tB.getCurrentPos().x + tB.getSize().x - OFFSET
                && this.position.x * WALL_SIZE + (this.diameter) > tB.getCurrentPos().x
                && this.position.y * WALL_SIZE < tB.getCurrentPos().y + tB.getSize().y - OFFSET
                && this.position.y * WALL_SIZE + (this.diameter) > tB.getCurrentPos().y) {
            return true;
        } else {
            return false;
        }
    }

	public void checkCollisions(PlayerTank playerTank) {
        if(this.collidesWith(playerTank)) {
            this.destroy();
            playerTank.kill();
        }

        for (EnemyTank tank : Map.getEnemyTanks()) {
            if(this.isFriendly() && this.collidesWith(tank)) {
                this.destroy();
                tank.kill();
            }
        }
	}
}
