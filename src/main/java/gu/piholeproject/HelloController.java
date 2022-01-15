package gu.piholeproject;

import config.ConfigurationService;
import domain.piholeproject.PiHole;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import services.piholeproject.PiHoleHandler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController implements Initializable {

    private double TILE_WIDTH = 250;
    private double TILE_HEIGHT = 250;

    private String IPAddress;
    private Tile statusTile;
    private Tile ledTile;
    private Tile fluidTile;

    private ScheduledExecutorService executorStatusService;
    private ScheduledExecutorService executorFluidService;
    private ScheduledExecutorService executorActiveService;

    private PiHoleHandler piholeDns1;
    private PiHoleHandler piholeDns2;

    @FXML
    Pane rootPane;

    @FXML
    Pane rootPane2;

    @FXML
    Button initButton;

    @FXML
    public void initStuff(ActionEvent event) {
        initTiles();
    }


    public void initialize(URL location, ResourceBundle resources) {

        ConfigurationService confservice=new ConfigurationService();
        piholeDns1 = new PiHoleHandler(confservice.getConfiguration().getIPAddress(),confservice.getConfiguration().getAUTH());
        piholeDns2 = null;//new PiHoleHandler("192.168.52.4");

        initTiles();

        initializeStatusScheduler();
        initializeActiveTileScheduler();
        initializeFluidTileScheduler();

        initializeContextMenu();

        rootPane.setStyle("-fx-background-color: rgba(42, 42, 42, 1);");

        rootPane.getChildren().add(fluidTile);
        rootPane.getChildren().add(ledTile);
        rootPane.getChildren().add(statusTile);


    }

    private void initializeStatusScheduler() {
        executorStatusService = Executors.newSingleThreadScheduledExecutor();
        executorStatusService.scheduleAtFixedRate(this::inflateStatusData, 0, 5, TimeUnit.SECONDS);
    }

    private void initializeFluidTileScheduler() {
        executorFluidService = Executors.newSingleThreadScheduledExecutor();
        executorFluidService.scheduleAtFixedRate(this::inflateFluidData, 0, 15, TimeUnit.SECONDS);
    }

    private void initializeActiveTileScheduler() {
        executorActiveService = Executors.newSingleThreadScheduledExecutor();
        executorActiveService.scheduleAtFixedRate(this::inflateActiveData, 0, 50, TimeUnit.SECONDS);
    }

    /*
    private PiHole fetchPiholeData() {
        return new PiHoleHandler("192.168.52.3","").getPiHoleStats();
    }*/

    public void inflateStatusData() {
          Platform.runLater(() -> {

            Long queries = Long.valueOf(0);
            Long blockedAds = Long.valueOf(0);
            Long queriesProcessed = Long.valueOf(0);


            PiHole pihole1 =null;

            if(piholeDns1!=null)
            pihole1= piholeDns1.getPiHoleStats();

            PiHole pihole2 =null;
            if(piholeDns2!=null)
            pihole2= piholeDns2.getPiHoleStats();


            if (pihole1 != null) {
                queries += pihole1.getDns_queries_today();
                blockedAds += pihole1.getAds_blocked_today();
                queriesProcessed += pihole1.getQueries_forwarded();
                queriesProcessed += pihole1.getQueries_cached();
            }
            if (pihole2 != null) {
                queries += pihole2.getDns_queries_today();
                blockedAds += pihole2.getAds_blocked_today();
                queriesProcessed += pihole2.getQueries_forwarded();
                queriesProcessed += pihole2.getQueries_cached();

            }

            statusTile.setLeftValue(queries);
            statusTile.setMiddleValue(blockedAds);
            statusTile.setRightValue(queriesProcessed);

            statusTile.setDescription(getHumanReadablePriceFromNumber( pihole1.getDomains_being_blocked()));


            statusTile.setText(piholeDns1.getLastBlocked());

        });
    }

    public void inflateFluidData() {
        Platform.runLater(() -> {

            Double adsPercentage = Double.valueOf(0);


            PiHole pihole1 =null;

            if(piholeDns1!=null)
                pihole1= piholeDns1.getPiHoleStats();

            PiHole pihole2 =null;
            if(piholeDns2!=null)
                pihole2= piholeDns2.getPiHoleStats();

            if (pihole1 != null)
                adsPercentage += pihole1.getAds_percentage_today();

            if (pihole2 != null)
                adsPercentage += pihole2.getAds_percentage_today();

            fluidTile.setValue(adsPercentage);

            fluidTile.setText(piholeDns1.getGravityLastUpdate());

        });
    }

    public void inflateActiveData() {
        Platform.runLater(() -> {
            // PiHole pihole = fetchPiholeData();
            PiHole pihole1 =null;

            if(piholeDns1!=null)
                pihole1= piholeDns1.getPiHoleStats();

            PiHole pihole2 =null;
            if(piholeDns2!=null)
                pihole2= piholeDns2.getPiHoleStats();

            if ((pihole1!=null && pihole1.isActive()) && (pihole2!=null && pihole2.isActive()))
                ledTile.setActiveColor(Color.LIGHTGREEN);

            if ((pihole1!=null && pihole1.isActive()) && (pihole2==null || !pihole2.isActive())  ||  (pihole1==null || !pihole1.isActive()) && (pihole2!=null && pihole2.isActive()))
                ledTile.setActiveColor(Color.LIGHTGREEN);

            if((pihole1==null || !pihole1.isActive()) && (pihole2==null || !pihole2.isActive()))
                ledTile.setActiveColor(Color.RED);


            ledTile.setText(piholeDns1.getTopXBlocked(7));
            ledTile.setDescription(piholeDns1.getIPAddress());
            ledTile.setTitle("API Version: "+ piholeDns1.getVersion());

        });

    }

    private void initTiles() {

        initFluidTile(0, 0);

        initLEDTile(TILE_WIDTH, 0);

        initStatusTile(0, TILE_HEIGHT, "Nbr of domains blocked: ", "", "Processed", "Blocked", "Accepted", "Gravity");


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
        fluidTile = TileBuilder.create().skinType(Tile.SkinType.FLUID).prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Gravity last update: ")
                .text("ADS Blocked")
                .unit("\u0025").decimals(0).barColor(Tile.RED) // defines the fluid color, alternatively use sections or gradientstops
                .animated(true).build();

        fluidTile.setLayoutX(x);
        fluidTile.setLayoutY(y);
        fluidTile.setValue(0);
    }

    private void initLEDTile(double x, double y) {
        /*--LED Tile--*/
        ledTile = TileBuilder.create().skinType(Tile.SkinType.LED).prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Version: ")
                .description("Description")
                .text("Whatever text").build();
        ledTile.setLayoutX(x);
        ledTile.setLayoutY(y);
        ledTile.setActive(true);
    }

    private void initStatusTile(double x, double y, String statusTitle, String notifications, String leftText, String middleText, String rightText, String text) {

        Indicator leftGraphics;
        Indicator middleGraphics;
        Indicator rightGraphics;
        /*--Status Tile--*/
        leftGraphics = new Indicator(Tile.BLUE);
        leftGraphics.setOn(true);

        middleGraphics = new Indicator(Tile.RED);
        middleGraphics.setOn(true);

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
        MenuItem refreshItem = new MenuItem("Refresh All Now");
        refreshItem.setOnAction(event -> {
            // executorStatusService.schedule(this::loadStatusData, 0, TimeUnit.SECONDS);
            inflateActiveData();
            inflateFluidData();
            inflateStatusData();
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

/*
        for (Node truc: rootPane.getChildren()) {


            truc.setOnMousePressed(event -> {
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(rootPane, event.getScreenX(), event.getScreenY());
                } else {
                    if (contextMenu.isShowing()) {
                        contextMenu.hide();
                    }
                }
            });
            /*truc.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                System.out.println("ffffffff");
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(rootPane, 450, 450);
                } else {
                    if (contextMenu.isShowing()) {
                        contextMenu.hide();
                    }
                }
            });
*/

    }

    public static String getHumanReadablePriceFromNumber(long number){

        if(number >= 1000000000){
            return String.format("%.2fB", number/ 1000000000.0);
        }

        if(number >= 1000000){
            return String.format("%.2fM", number/ 1000000.0);
        }

        if(number >= 100000){
            return String.format("%.2fL", number/ 100000.0);
        }

        if(number >=1000){
            return String.format("%.2fK", number/ 1000.0);
        }
        return String.valueOf(number);

    }

}