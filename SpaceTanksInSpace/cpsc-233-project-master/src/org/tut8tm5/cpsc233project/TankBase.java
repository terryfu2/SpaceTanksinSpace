package org.tut8tm5.cpsc233project;
import java.util.Arrays;

import javafx.scene.paint.Color;

public abstract class TankBase implements Drawable{
	
	enum TankType{
		PLAYER,
		ENEMY,
	}
	
    private Vector2f currentPos=new Vector2f(0, 0);
    private Vector2f size = new Vector2f(24, 24); // this just defines how big the red square for the tank is drawn, it's temporary until we have a texture
    
	private TankType tankType;
	private char representation;
	//turretRotation is measured in radians, anti-clockwise.
    private float turretRotation;
    
    private boolean isDead;
	
	/**Constructor
	@parameters- Vector2f currentPos, char playerType, char representation, float turretRotation
	*/
	
	public TankBase(Vector2f currentPos, TankType tankType, char representation, float turretRotation) {
		this.currentPos=currentPos;
		this.tankType=tankType;
		this.representation=representation;
		this.setTurretRotation(turretRotation);
		
	}
    
    // getter
	public float getTurretRotation() {
		return turretRotation;
	}
    
    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        isDead = true;
    }
	
	/**moveTo- moves to a given location in screen coordinates
	@parameters- float x= the x coordinate, float y= the y coordinate
	*/
	
	public void moveTo(float x, float y) {
        this.currentPos= new Vector2f(x, y);
	}
    
    /**
    intersectsTile() - checks whether the tank intersects with a certain tile on the map
    @param byte x, byte y
    @return boolean isTrue
    **/
    private boolean intersectsTile(byte x, byte y) {
        if(Map.getTileAt(x, y) <= 0) return false;
        Vector2f tileWindowPos = new Vector2f((float)x, (float)y).times(Map.WALL_SIZE);

        return currentPos.x - size.x / 2f < tileWindowPos.x + Map.WALL_SIZE && 
            currentPos.x + size.x / 2f > tileWindowPos.x &&
            currentPos.y + size.y / 2f > tileWindowPos.y && 
            currentPos.y - size.y / 2f < tileWindowPos.y + Map.WALL_SIZE; 
    }

	/** move- moves in a specified direction, from current position
	@parameters- float x- coordinates to move horizontally, float y- coordinates to move vertically
	*/
	
	public void move(float x, float y) {

        // move the tank unconditionally
        Vector2f movement = new Vector2f(x, y);
        Vector2f dir = movement.direction();
        float mag = movement.magnitude();

        for (float i = 0; i < mag; i++) {
            Vector2f originalPos = currentPos; // keeps track of where the tank used to be
            this.moveTo(this.currentPos.x + dir.x, this.currentPos.y + dir.y);

            // checks every tile in the map to see if the tank intersects with it.
            for (byte xMap = 0; xMap < Map.WIDTH; xMap++) {
                for (byte yMap = 0; yMap < Map.HEIGHT; yMap++) {
                    // if the tank intersects with any tile, undo the movement we just did
                    if(intersectsTile(xMap, yMap)) {
                        this.moveTo(originalPos.x, originalPos.y);
                    }
                }
            }
        }
	}
	
	/**
	  setTurretRotation- sets turret rotation to given parameter
	  @parameters- float turretRotation= anti-clockwise turret rotation measured in degrees
	 */
	
	public void setTurretRotation(float turretRotation) {
		this.turretRotation = turretRotation ;
	}
	
	/**
	  This method sets the turret rotation using a vector 2f
	  @param MouseCoordinate the Vector2f containing coordinates to set turret rotation to
	  @return none
	 */
	
	public void setTurretRotation(Vector2f MouseCoordinate) {
		this.setTurretRotation(this.getAngle(MouseCoordinate));
		
	}

	/**
	 getCurrentPos- returns current position of the tank
	  @param none
	 return Vector2f of current position
	 */
	
	public Vector2f getCurrentPos() {
		return this.currentPos;
	}
	
	/*
	 * getSize- returns the size of the tank
	 * @return Vector2f of current size
	 */
	public Vector2f getSize() {
		return this.size;
	}
	
	/*
	 * getRepresentation- returns char used to represent tank
	 * @return representation char representation of tank
	 */
	public char getRepresentation() {
		return this.representation;
	}
	
	/**
	  draw- draws tank for text-based version
	  @param none
	  @return none
	 */ 
	
	public abstract void draw();// {
        //if(Program.isTextBased) TextRenderer.draw((int)currentPos.x,(int)currentPos.y, String.valueOf(representation) );
       // else Renderer.drawRectangle(currentPos.x - size.x / 2f, currentPos.y - size.y / 2f, size.x, size.y, Color.RED);
	//}
	
	
	
	/**
	  This function creates a bullet
	  @param none
	  @return none
	 */
	
	public Bullet shoot(){
		Bullet bullet = new Bullet(true, this.getCurrentPos().over(Map.WALL_SIZE).plus(new Vector2f(this.getTurretRotation()).times(0.5f)), this.getTurretRotation());
        Program.bullets.add(bullet);
        return bullet;
    }
    
    public void update(float dt) { }
	
	/**
	 This function gets the angle between one given coordinate point and the tank
	  Mainly utilised to get the angle to shoot a bullet
	 @param MouseCoordinate Vector2f coordinate for the given coordinate point
	 @return standardAngle or the specific angle between coordinate point and tank
	 */
	
	public float getAngle(Vector2f MouseCoordinate) {
		Vector2f standardVector= this.getCurrentPos().to(MouseCoordinate);
		float stdX= standardVector.x;
		float stdY= standardVector.y;
		//Special Degrees
		 if (stdX==0&&stdY<0) {
				return (float)Math.PI/2;
		}
		else if (stdX==0&&stdY>0) {
				return(float)Math.PI*3/2;
		}
		else if (stdX>0&&stdY==0) {
				return (float)0;
		}
		else if  (stdX<0&&stdY==0){
				return (float)Math.PI;
		}
		else {
			float standardAngle= this.getCurrentPos().to(MouseCoordinate).angle();
			return standardAngle;
		}
		
	}
	
	//test
	
	
}

