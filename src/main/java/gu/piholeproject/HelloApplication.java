package gu.piholeproject;

import domain.piholeproject.PiHole;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.TimeSection;
import eu.hansolo.tilesfx.addons.Indicator;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.RadarChartMode;
import eu.hansolo.tilesfx.colors.ColorSkin;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.skins.LeaderBoardItem;
import eu.hansolo.tilesfx.tools.Country;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import eu.hansolo.tilesfx.tools.Helper;
import eu.hansolo.tilesfx.tools.Rank;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HelloApplication extends Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws IOException {


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
        Random RND = new Random();
            double TILE_WIDTH  = 300;
         double TILE_HEIGHT = 300;



         ChartData       chartData1;
         ChartData       chartData2;
         ChartData       chartData3;

         ChartData       smoothChartData1;

         Tile            radialPercentageTile;
         Tile            statusTile;
         Tile            ledTile;
         Tile            fluidTile;


        smoothChartData1 = new ChartData("Item 1",  25, Tile.BLUE);
        chartData1 = new ChartData("Item 1", 24.0, Tile.GREEN);
        chartData2 = new ChartData("Item 2", 10.0, Tile.BLUE);
        chartData3 = new ChartData("Item 3", 12.0, Tile.RED);



        fluidTile = TileBuilder.create().skinType(Tile.SkinType.FLUID)
                .prefSize(150, 150)
                .title("Ads Blocked")
                //.text("Waterlevel")
                .unit("\u0025")
                .decimals(0)
                .barColor(Tile.RED) // defines the fluid color, alternatively use sections or gradientstops
                .animated(true)
                .build();
        Double v=RND.nextDouble();
        System.out.println(v);
        fluidTile.setValue(v * 100);

        ledTile = TileBuilder.create()
                .skinType(Tile.SkinType.LED)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Led Tile")
                .description("Description")
                .text("Whatever text")
                .build();
        ledTile.setActive(true);


        Indicator leftGraphics = new Indicator(Tile.RED);
        leftGraphics.setOn(true);

        Indicator middleGraphics = new Indicator(Tile.YELLOW);
        middleGraphics.setOn(true);

        Indicator rightGraphics = new Indicator(Tile.GREEN);
        rightGraphics.setOn(true);

        statusTile = TileBuilder.create()
                .skinType(Tile.SkinType.STATUS)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Status Tile")
                .description("Notifications")
                .leftText("CRITICAL")
                .middleText("WARNING")
                .rightText("INFORMATION")
                .leftGraphics(leftGraphics)
                .middleGraphics(middleGraphics)
                .rightGraphics(rightGraphics)
                .text("Text")
                .build();

        if (statusTile.getLeftValue() > 1000) { statusTile.setLeftValue(0); }
        if (statusTile.getMiddleValue() > 1000) { statusTile.setMiddleValue(0); }
        if (statusTile.getRightValue() > 1000) { statusTile.setRightValue(0); }
        statusTile.setLeftValue(statusTile.getLeftValue() + RND.nextInt(4));
        statusTile.setMiddleValue(statusTile.getMiddleValue() + RND.nextInt(3));
        statusTile.setRightValue(statusTile.getRightValue() + RND.nextInt(3));

        radialPercentageTile = TileBuilder.create().skinType(Tile.SkinType.RADIAL_PERCENTAGE)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                //.backgroundColor(Color.web("#26262D"))
                .maxValue(1000)
                .title("RadialPercentage Tile")
                .description("Product 1")
                .textVisible(false)
                .chartData(chartData1, chartData2, chartData3)
                .animated(true)
                .referenceValue(100)
                .value(chartData1.getValue())
                .descriptionColor(Tile.GRAY)
                //.valueColor(Tile.BLUE)
                //.unitColor(Tile.BLUE)
                .barColor(Tile.BLUE)
                .decimals(0)
                .build();

        radialPercentageTile.setNotifyRegionTooltipText("tooltip");
        radialPercentageTile.showNotifyRegion(true);
        radialPercentageTile.setValue(chartData1.getValue());

        FlowGridPane pane = new FlowGridPane(8, 6,
                fluidTile,ledTile,statusTile,radialPercentageTile);


        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secondaryStage = new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initOwner(primaryStage);

        //Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(root);

        Scene scene = new Scene(pane);
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

       // Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Hello!");
        //stage.setAlwaysOnTop(true);
        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.setResizable(false);
        //stage.setOpacity(0f);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}