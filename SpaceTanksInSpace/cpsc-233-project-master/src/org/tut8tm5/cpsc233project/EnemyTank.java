package org.tut8tm5.cpsc233project;
import java.util.Random;
import org.tut8tm5.cpsc233project.TankBase.TankType;
import javafx.scene.paint.Color;

public class EnemyTank extends TankBase {

    private final float SHOOT_INTERVAL = 5f;

    private float shootTimer = 0f;

    public EnemyTank(Vector2f currentPos, char representation, float turretRotation) {
		super(currentPos, TankType.ENEMY, representation, turretRotation);
    }
    
    // generate a random byte inside a specified range
    // TODO: move this to some kind of utility class
    private static Random random = new Random();
/**
randomFLoat() - returns a random float within a given range
@param doubleminInclusive, double maxExclusive
@return float newNumber
**/
    private static float randomFloat(double minInclusive, double maxExclusive) {
        return (float)(minInclusive + random.nextDouble() * (maxExclusive - minInclusive));
    }
/**
update() - calls the method for the tank to shoot
@param float dt
@return none
**/
    public void update(float dt) {
        shootTimer += dt;
        if(shootTimer >= SHOOT_INTERVAL) {
            shootTimer = 0f;
            this.shoot();
        }
    }
/**
shoot() - shoots a bullet from the rotated turret
@param none
@return none
**/
	public Bullet shoot() {
        setTurretRotation(randomFloat(0.01f, 2f * Math.PI));
        Bullet b = super.shoot();
        b.setFriendly(false);
        return b;
	}
/**
draw()- draws the tanks current location
@param none
@return none
**/
	public void draw() {
		Vector2f currentPos= this.getCurrentPos();
		char representation= this.getRepresentation();
		Vector2f size = this.getSize();
        	if(Program.isTextBased) TextRenderer.draw((int)currentPos.x,(int)currentPos.y, String.valueOf(representation) );
        	else Renderer.drawRectangle(currentPos.x - size.x / 2f, currentPos.y - size.y / 2f, size.x, size.y, Color.GREEN);
	}
	

}
