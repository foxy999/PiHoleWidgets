package gu.piholeproject;

import domain.piholeproject.PiHole;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import services.piholeproject.PiHoleHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HelloApplication extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws IOException {


/*
        try {
            // API Settings
            URL url = new URL("http://192.168.52.3/admin/api.php?summary");

            // Open Connection
            HttpURLConnection conn;
            while (true) {
                Thread.sleep(1500);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                // Get Response
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
                }

                // Transform Raw result to JSON
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                String output = br.readLine();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(output);

                // Transform JSON result to Objects
                PiHole pihole;
                try {
                    PiHoleHandler handler = new PiHoleHandler();
                    pihole = handler.getPiHoleFromJSON(json);

                    System.out.printf("DNS Querries: %s      ADS Blocked : %s", pihole.getDns_queries_today(), pihole.getAds_blocked_today());

                    System.out.print("\n");









                    //System.out.println(pihole.ads_blocked_today);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Disconnect
            //conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
*/
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initOwner(primaryStage);

        Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root);

        // Scene scene = new Scene(pane);
        secondaryStage.setScene(scene);
        secondaryStage.show();


        //Add support for drag and move
        //Drag = mouse click + drag
        scene.setOnMousePressed(event -> {
            xOffset = secondaryStage.getX() - event.getScreenX();
            yOffset = secondaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            secondaryStage.setX(event.getScreenX() + xOffset);
            secondaryStage.setY(event.getScreenY() + yOffset);
        });

        secondaryStage.setScene(scene);
        secondaryStage.show();

    }



    public static void main(String[] args) {
        launch();
    }
}