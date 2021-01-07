/*package org.tut8tm5.cpsc233project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;




public class Menu extends Application {
	
	
	public static void main(String[] args) 
    {
        launch(args);
    }
	
	public void start(Stage theStage) 
	{
	    theStage.setTitle( "Space Tanks in Space" );
	         
	    Group root = new Group();
	    Scene theScene = new Scene( root );
	    theStage.setScene( theScene );
	         
	    Canvas canvas = new Canvas( 400, 200 );
	    root.getChildren().add( canvas );
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	         
	    gc.setFill( Color.BLUE );
	    gc.setStroke( Color.BLACK );
	    gc.setLineWidth(2);
	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
	    gc.setFont( theFont );
	    gc.fillText( "Space Tanks in Space", 60, 50 );
	    gc.strokeText( "Space Tanks in Space!", 60, 50 );
	    
        Button btn = new Button();
        btn.setText("Start New Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Program game = new Program();
            }
        });
        
        // this used to be called `root`, but that already exists so i renamed it,
        // idk what you meant to do with these two lines
        StackPane sp = new StackPane();
        root.getChildren().add(btn);
//	    Image earth = new Image( "earth.png" );
//	    gc.drawImage( earth, 180, 100 );
	         
	    theStage.show();
	}
	
	//test
}
*/
