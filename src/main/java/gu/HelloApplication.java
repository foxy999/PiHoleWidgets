/*
 *
 *  Copyright (C) 2022.  Reda ELFARISSI aka foxy999
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package gu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HelloApplication extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initOwner(primaryStage);

        Stage thirdStage = new Stage();
        thirdStage.initStyle(StageStyle.UNDECORATED);
        thirdStage.initOwner(primaryStage);

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "hello-view.fxml" ) );
        Parent root = loader.load();
        Scene scene = new Scene( root );
        HelloController rc = loader.<HelloController>getController();


/*
        FXMLLoader loader2 = new FXMLLoader( getClass().getResource( "status.fxml" ) );
        Parent root2 = loader2.load();
        Scene scene2 = new Scene( root2 );

*/

        //Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
       // Scene scene = new Scene(root);


       // scene.setFill(Color.TRANSPARENT);
        //secondaryStage.setScene(scene);
        //secondaryStage.show();

        //Make it right-top aligned
        /*
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        secondaryStage.setX(visualBounds.getMaxX() - 25 - scene.getWidth());
        secondaryStage.setY(visualBounds.getMinY() + 25);
        */
        //Add support for drag and move
        //Drag = mouse click + drag
        /*
        scene.setOnMousePressed(event -> {
            xOffset = secondaryStage.getX() - event.getScreenX();
            yOffset = secondaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            secondaryStage.setX(event.getScreenX() + xOffset);
            secondaryStage.setY(event.getScreenY() + yOffset);
        });
        */

        for (Node truc: rc.rootPane.getChildren()) {

            truc.setOnMousePressed(event -> {
                xOffset = secondaryStage.getX() - event.getScreenX();
                yOffset = secondaryStage.getY() - event.getScreenY();
            });
            truc.setOnMouseDragged(event -> {
                secondaryStage.setX(event.getScreenX() + xOffset);
                secondaryStage.setY(event.getScreenY() + yOffset);
            });
        }

        root.setOnMousePressed(event -> {
            xOffset = secondaryStage.getX() - event.getScreenX();
            yOffset = secondaryStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            secondaryStage.setX(event.getScreenX() + xOffset);
            secondaryStage.setY(event.getScreenY() + yOffset);
        });



        secondaryStage.setScene(scene);
        secondaryStage.show();
/*
        thirdStage.setScene(scene2);
        thirdStage.show();*/

    }



    public static void main(String[] args) {
        launch();
    }
}