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

import config.PiholeConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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


        FXMLLoader loader = new FXMLLoader(getClass().getResource("WidgetContainer.fxml"));
        Parent root = loader.load();

        HelloController rc = loader.getController();


        PiholeConfig configDNS1 = rc.getConfigDNS1();
        PiholeConfig configDNS2 = rc.getConfigDNS2();

        Scene scene;

        if (configDNS1 != null || configDNS2 != null) {

            scene = new Scene(root);


            for (Node truc : rc.rootPane.getChildren()) {

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

        } else {
            /*
            loader = new FXMLLoader(getClass().getResource("Configuration.fxml"));
            root = loader.load();
            scene = new Scene(root);
            */
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please input your configuration before opening the widget", ButtonType.OK);
           alert.setHeaderText("Configuration Missing");
           alert.showAndWait();

            /*  if (alert.getResult() == ButtonType.OK) {
                System.exit(0);
            }*/
            System.exit(0);
        }



    }


    public static void main(String[] args) {
        launch();
    }
}