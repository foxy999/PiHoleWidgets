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

package pihole;

import domain.configuration.PiholeConfig;
import domain.configuration.WidgetConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.configuration.ConfigurationService;

import java.io.IOException;


public class WidgetApplication extends Application {

    private double xOffset;
    private double yOffset;

    static PiholeConfig configDNS1 ;
    static PiholeConfig configDNS2 ;
    static WidgetConfig widgetConfig;

    static Parent root2;
    static ConfigurationService confService;
    static Stage configurationStage;
    static Stage widgetStage;
    static WidgetController widgetController;


    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        widgetStage = new Stage();
        widgetStage.initStyle(StageStyle.UNDECORATED);
        widgetStage.initOwner(primaryStage);

        configurationStage = new Stage();
        configurationStage.initOwner(primaryStage);
        configurationStage.initStyle(StageStyle.UNDECORATED);


        confService = new ConfigurationService();
        confService.readConfiguration();




        configDNS1 = confService.getConfigDNS1();
        configDNS2 = confService.getConfigDNS2();
        widgetConfig= confService.getWidgetConfig();


        ConfigurationController configurationController = new ConfigurationController(configDNS1, configDNS2,widgetConfig);

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Configuration.fxml"));
        loader2.setController(configurationController);
        root2 = loader2.load();

        widgetController = new WidgetController(configDNS1, configDNS2,widgetConfig);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("WidgetContainer.fxml"));
        loader.setController(widgetController);
        Parent root = loader.load();

        Scene scene = new Scene(widgetController.getGridPane());

        /*
        for (Node truc : widgetController.rootPane.getChildren()) {

            truc.setOnMousePressed(event -> {
                xOffset = widgetStage.getX() - event.getScreenX();
                yOffset = widgetStage.getY() - event.getScreenY();
            });
            truc.setOnMouseDragged(event -> {
                widgetStage.setX(event.getScreenX() + xOffset);
                widgetStage.setY(event.getScreenY() + yOffset);
            });
        }

        root.setOnMousePressed(event -> {
            xOffset = widgetStage.getX() - event.getScreenX();
            yOffset = widgetStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            widgetStage.setX(event.getScreenX() + xOffset);
            widgetStage.setY(event.getScreenY() + yOffset);
        });

        root.setOnMousePressed(event -> {
            xOffset = widgetStage.getX() - event.getScreenX();
            yOffset = widgetStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            widgetStage.setX(event.getScreenX() + xOffset);
            widgetStage.setY(event.getScreenY() + yOffset);
        });
        */

        for (Node truc : widgetController.getGridPane().getChildren()) {

            truc.setOnMousePressed(event -> {
                xOffset = widgetStage.getX() - event.getScreenX();
                yOffset = widgetStage.getY() - event.getScreenY();
            });
            truc.setOnMouseDragged(event -> {
                widgetStage.setX(event.getScreenX() + xOffset);
                widgetStage.setY(event.getScreenY() + yOffset);
            });
        }

        root.setOnMousePressed(event -> {
            xOffset = widgetStage.getX() - event.getScreenX();
            yOffset = widgetStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            widgetStage.setX(event.getScreenX() + xOffset);
            widgetStage.setY(event.getScreenY() + yOffset);
        });

        root.setOnMousePressed(event -> {
            xOffset = widgetStage.getX() - event.getScreenX();
            yOffset = widgetStage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged(event -> {
            widgetStage.setX(event.getScreenX() + xOffset);
            widgetStage.setY(event.getScreenY() + yOffset);
        });


        widgetStage.setScene(scene);
        widgetStage.show();

        Scene scene2 = new Scene(root2);
        configurationStage.setScene(scene2);
        configurationStage.setOpacity(0);
        configurationStage.setAlwaysOnTop(true);
        configurationStage.show();

        if ((configDNS1==null || configDNS1.getIPAddress().isEmpty()) && (configDNS2==null ||configDNS2.getIPAddress().isEmpty()))
        openConfigurationWindow();

        /*} else {
            configurationController = new ConfigurationController(configDNS1,configDNS2);

            loader2 = new FXMLLoader(getClass().getResource("Configuration.fxml"));
            loader2.setController(configurationController);
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            configurationStage.setScene(scene2);
            configurationStage.show();
          /* Alert alert = new Alert(Alert.AlertType.ERROR, "Please input your configuration before opening the widget", ButtonType.OK);
           alert.setHeaderText("Configuration Missing");
           alert.showAndWait();

            /*  if (alert.getResult() == ButtonType.OK) {
                System.exit(0);
            }
            System.exit(0);*/
    //}

    }

    public static void openConfigurationWindow()
    {
        configurationStage.setOpacity(1);
    }

    public static void applyAndCloseConfigurationWindow()
    {
        configurationStage.setOpacity(0);
        confService.readConfiguration();


        configDNS1 = confService.getConfigDNS1();
        configDNS2 = confService.getConfigDNS2();

        widgetConfig=confService.getWidgetConfig();

        widgetController.setConfigDNS1(configDNS1);
        widgetController.setConfigDNS2(configDNS2);
        widgetController.setWidgetConfig(widgetConfig);


        widgetController.refreshPihole();

    }

    public static void closeConfigurationWindow(){
        configurationStage.setOpacity(0);
    }


    public static void main(String[] args) {
        launch();
    }
}