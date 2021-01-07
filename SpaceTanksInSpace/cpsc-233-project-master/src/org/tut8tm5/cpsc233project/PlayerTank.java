package org.tut8tm5.cpsc233project;

import org.tut8tm5.cpsc233project.TankBase.TankType;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PlayerTank extends TankBase {
    
    public EventHandler<KeyEvent> keyPressedEventHandler;
    public EventHandler<MouseEvent> mouseClickEventHandler;
	//constructor
	public PlayerTank(Vector2f currentPos, char representation, float turretRotation) {
		super(currentPos, TankType.PLAYER, representation, turretRotation);
        
	//checks for mouse action and acts accordingly
        mouseClickEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent myMe) {
                Vector2f MouseCoordinate = new Vector2f((float) myMe.getSceneX(), (float) myMe.getSceneY());
                setTurretRotation(MouseCoordinate);
                shoot();
            }
        };
	//checks for wasd movement keys pressed
        keyPressedEventHandler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent Ke) {
                final float speed = 5f;
                if (Ke.getCode() == KeyCode.RIGHT) {
                    move(speed, 0);
                } else if (Ke.getCode() == KeyCode.LEFT) {
                    move(-speed, 0);
                } else if (Ke.getCode() == KeyCode.DOWN) {
                    move(0, speed);
                } else if (Ke.getCode() == KeyCode.UP) {
                    move(0, -speed);
                }
            }
        };
	}
	/**
	draw()
	@param none
	@return none
	**/
	public void draw() {
		Vector2f currentPos= this.getCurrentPos();
		char representation= this.getRepresentation();
		Vector2f size = this.getSize();
        if(Program.isTextBased) TextRenderer.draw((int)currentPos.x,(int)currentPos.y, String.valueOf(representation) );
        else Renderer.drawRectangle(currentPos.x - size.x / 2f, currentPos.y - size.y / 2f, size.x, size.y, Color.RED);
	}
	
}
