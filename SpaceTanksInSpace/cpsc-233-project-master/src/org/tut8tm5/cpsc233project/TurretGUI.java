/**
package org.tut8tm5.cpsc233project;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.math.*;

import org.tut8tm5.cpsc233project.TankBase.TankType;
public class TurretGUI extends TankBase{//TankBaseGui{
	private Image turretImagine;
	
	public TurretGUI(Vector2f currentPos, TankType tankType, char representation, float turretRotation) {
		super(currentPos, tankType, representation, turretRotation);
		
		
	}
	
	public int getQuadrant(Vector2f MouseCoordinate) {
		int CoordinateNumber=900;
		if (MouseCoordinate.x<0 & MouseCoordinate.y>0){
			CoordinateNumber=2;
		}
		else if (MouseCoordinate.x>0 & MouseCoordinate.y>0) {
			CoordinateNumber=1;
		}
		else if (MouseCoordinate.x<0 &MouseCoordinate.y<0) {
			CoordinateNumber=3;
		}
		else if (MouseCoordinate.x>0 & MouseCoordinate.y<0) {
			CoordinateNumber=4;
		}
		
		else if (MouseCoordinate.x==0 & MouseCoordinate.y>0) {
			CoordinateNumber=90; //indicating the 90 degrees
		}
		else if (MouseCoordinate.x==0 & MouseCoordinate.y<0) {
			CoordinateNumber=270; //indicating the 270 degrees
		}
		else if (MouseCoordinate.x>0 & MouseCoordinate.y==0) {
			CoordinateNumber=0; //indicating the 0 degrees
		}
		else if (MouseCoordinate.x<0 & MouseCoordinate.y==0) {
			CoordinateNumber=180; //indicating the 180 degrees
		}
		return CoordinateNumber;
	}
	
	public float getAngle(Vector2f MouseCoordinate) {
		Vector2f MouseCoordDif= new Vector2f(MouseCoordinate.x-this.getCurrentPos().x, MouseCoordinate.y-this.getCurrentPos().y); 
		double xCoordToYCoordRatio=MouseCoordDif.y/MouseCoordDif.x;
		int quadrantNumber= getQuadrant(MouseCoordDif);
		if (quadrantNumber>4 || quadrantNumber==0) {
			return (float)quadrantNumber;
		}
		else {
			if (quadrantNumber==1){
				return (float)Math.atan(xCoordToYCoordRatio);
			}
			else if (quadrantNumber==2) {
				return (float)(Math.atan(-xCoordToYCoordRatio)+90);
			}
			else if (quadrantNumber==3) {
				return (float)(Math.atan(xCoordToYCoordRatio)+180);
			}
			else {
				return (float)(Math.atan(-xCoordToYCoordRatio)+270);
			}
		}
		
	}
	
	public void setTurretRotation(Vector2f MouseCoordinate) {
		this.setTurretRotation(this.getAngle(MouseCoordinate));
	}
	
	
	public void setImage(Image ImageToSet) {
		this.turretImagine=ImageToSet;
	}
	
	
	
		
	}
	//test
	
*/

