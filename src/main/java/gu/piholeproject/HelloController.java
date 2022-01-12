package gu.piholeproject;

import domain.piholeproject.PiHole;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.piholeproject.PiHoleHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController implements Initializable {

    private double TILE_WIDTH = 300;
    private double TILE_HEIGHT = 300;

    private String IPAdress;
    private Tile statusTile;
    private Tile ledTile;
    private Tile fluidTile;

    private ScheduledExecutorService executorService;

    private HttpURLConnection conn;
    private URL url;
    private InputStreamReader in;
    private BufferedReader br;

    JSONObject json = null;
    JSONParser parser;
    String output;

    @FXML
    public Pane rootPane;

    @FXML
    Button initButton;

    @FXML
    public void initStuff(ActionEvent event) {
        initTiles();
    }


    public void initialize(URL location, ResourceBundle resources) {
        /*try {
            configModel = new ConfigurationService().getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        IPAdress = "192.168.52.3";
        initTiles();
        initAPI();
        initializeScheduler();
        initializeContextMenu();

        //rootPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        //rootPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
        rootPane.setStyle("-fx-background-color: transparent;");

        rootPane.getChildren().add(fluidTile);
        rootPane.getChildren().add(ledTile);
        rootPane.getChildren().add(statusTile);

    }

    private void initializeScheduler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::loadData, 0, 5, TimeUnit.SECONDS);
    }

    private PiHole fetchPiholeData() {

        initAPI();
        int responscode = 0;
        // Get Response
        try {
            responscode = conn.getResponseCode();
            if (responscode != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + responscode);
            }
        } catch (IOException e) {
            System.out.println("Error GETTING RESPONSE");
            e.printStackTrace();
        }

        // Transform Raw result to JSON

        try {
            in = new InputStreamReader(conn.getInputStream());

            br = new BufferedReader(in);

            output = br.readLine();
            parser = new JSONParser();
            json = (JSONObject) parser.parse(output);


        } catch (IOException | ParseException ioe) {
            ioe.printStackTrace();
        }


        // Transform JSON result to Objects
        PiHole pihole;
        try {
            PiHoleHandler handler = new PiHoleHandler();
            pihole = handler.getPiHoleFromJSON(json);

            return pihole;
            //System.out.printf("DNS Querries: %s      ADS Blocked : %s", pihole.getDns_queries_today(), pihole.getAds_blocked_today());

            // System.out.print("\n");

            //System.out.println(pihole.ads_blocked_today);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void inflateData() {
        PiHole pihole = fetchPiholeData();

        fluidTile.setValue(pihole.getAds_percentage_today());


        if (pihole.getStatus().equals("enabled"))
            ledTile.setActive(true);
        else ledTile.setActive(false);

        statusTile.setLeftValue(pihole.getDns_queries_today());
        statusTile.setMiddleValue(pihole.getAds_blocked_today());
        statusTile.setRightValue(pihole.getQueries_forwarded() + fetchPiholeData().getQueries_cached());

    }

    private void loadData() {
        System.out.println("Refreshing data...");

        Platform.runLater(() -> {
            inflateData();
        });
    }


    private void initTiles() {

        initFluidTile(0, 0);

        initLEDTile(300, 0);

        initStatusTile(0, 300, "PiHole", IPAdress, "Queries Processed", "Ads Blocked", "Queries Accepted", "A");


        //initRadialTile();

    }

    private void initRadialTile() {
        /*--Other Percentage Tile--*/
/*

        chartData1 = new ChartData("Item 1", 24.0, Tile.GREEN);
        chartData2 = new ChartData("Item 2", 10.0, Tile.BLUE);
        chartData3 = new ChartData("Item 3", 12.0, Tile.RED);

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
        radialPercentageTile.showNotifyRegion(true);*/
    }

    private void initFluidTile(double x, double y) {
        /*--Fluid Percentage Tile--*/
        fluidTile = TileBuilder.create().skinType(Tile.SkinType.FLUID).prefSize(50, 50)
                //.title("Ads Blocked")
                .text("ADS Blocked")
                .unit("\u0025").decimals(0).barColor(Tile.RED) // defines the fluid color, alternatively use sections or gradientstops
                .animated(true).build();

        fluidTile.setLayoutX(x);
        fluidTile.setLayoutY(y);
        fluidTile.setValue(0);
    }

    private void initLEDTile(double x, double y) {
        /*--LED Tile--*/
        ledTile = TileBuilder.create().skinType(Tile.SkinType.LED).prefSize(TILE_WIDTH, TILE_HEIGHT).title("Led Tile").description("Description").text("Whatever text").build();
        ledTile.setLayoutX(x);
        ledTile.setLayoutY(y);
        ledTile.setActive(false);
    }

    private void initStatusTile(double x, double y, String statusTitle, String notifications, String leftText, String middleText, String rightText, String text) {

        Indicator leftGraphics;
        Indicator middleGraphics;
        Indicator rightGraphics;
        /*--Status Tile--*/
        leftGraphics = new Indicator(Tile.RED);
        leftGraphics.setOn(true);

        middleGraphics = new Indicator(Tile.YELLOW);
        middleGraphics.setOn(false);

        rightGraphics = new Indicator(Tile.GREEN);
        rightGraphics.setOn(true);

        statusTile = TileBuilder.create().skinType(Tile.SkinType.STATUS).prefSize(TILE_WIDTH, TILE_HEIGHT).
                title(statusTitle).
                description(notifications).
                leftText(leftText).
                middleText(middleText).
                rightText(rightText).leftGraphics(leftGraphics).middleGraphics(middleGraphics).rightGraphics(rightGraphics).
                text(text).build();

        statusTile.setLayoutX(x);
        statusTile.setLayoutY(y);

        statusTile.setLeftValue(0);
        statusTile.setMiddleValue(0);
        statusTile.setRightValue(0);
    }

    private void initializeContextMenu() {
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> {
            System.exit(0);
        });
        MenuItem refreshItem = new MenuItem("Refresh now");
        refreshItem.setOnAction(event -> {
            executorService.schedule(this::loadData, 0, TimeUnit.SECONDS);
        });
        final ContextMenu contextMenu = new ContextMenu(exitItem, refreshItem);
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isSecondaryButtonDown()) {
                contextMenu.show(rootPane, event.getScreenX(), event.getScreenY());
            } else {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
            }
        });
    }

    private void initAPI() {
        // API Settings
        try {
            url = new URL("http://192.168.52.3/admin/api.php?summary");


            // Open Connection


            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}