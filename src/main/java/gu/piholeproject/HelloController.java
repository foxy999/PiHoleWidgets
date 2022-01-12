package gu.piholeproject;

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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController implements Initializable {

    private double TILE_WIDTH  = 300;
    private double TILE_HEIGHT = 300;

    private ChartData       chartData1;
    private ChartData       chartData2;
    private ChartData       chartData3;


    private Tile radialPercentageTile;
    private Tile            statusTile;
    private Tile            ledTile;
    private Tile            fluidTile;

    private Indicator leftGraphics;
    private Indicator middleGraphics;
    private Indicator rightGraphics;

    private ScheduledExecutorService executorService;


    @FXML
    public Pane rootPane;

    @FXML Button initButton;

    @FXML
    public void initStuff(ActionEvent event){
        initTiles();
    }


    public void initialize(URL location, ResourceBundle resources) {
        /*try {
            configModel = new ConfigurationService().getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("test1");
        initTiles();
        //initializeScheduler();
        initializeContextMenu();
        //textCountryCode.setText(configModel.getCountryCode());

        chartData1 = new ChartData("Item 1", 24.0, Tile.GREEN);
        chartData2 = new ChartData("Item 2", 10.0, Tile.BLUE);
        chartData3 = new ChartData("Item 3", 12.0, Tile.RED);



        ledTile.setActive(true);



                    /*
                    if (statusTile.getLeftValue() > 1000) { statusTile.setLeftValue(0); }
                    if (statusTile.getMiddleValue() > 1000) { statusTile.setMiddleValue(0); }
                    if (statusTile.getRightValue() > 1000) { statusTile.setRightValue(0); }
                    */
        statusTile.setLeftValue(2500);
        statusTile.setMiddleValue(17055);
        statusTile.setRightValue(44);


        //radialPercentageTile.setValue(chartData1.getValue());
       /* rootPane = new FlowGridPane(8, 6,
                fluidTile,ledTile,statusTile);*/
        rootPane.getChildren().add(fluidTile);
        rootPane.getChildren().add(ledTile);
        rootPane.getChildren().add(statusTile);

    }

    private void initializeScheduler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(null, 0, 5, TimeUnit.SECONDS);
    }

    private void loadData() {


       /* DataProviderService dataProviderService = new DataProviderService();
        CovidDataModel covidDataModel = dataProviderService.getData(configModel.getCountryName());
*/
        Platform.runLater(() -> {
           // inflateData(covidDataModel);
            System.out.println("Refreshing data...");
        });
    }

    private void initializeContextMenu() {
        System.out.println("1");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> {
            System.exit(0);
        });
        System.out.println("2");
        MenuItem refreshItem = new MenuItem("Refresh now");
        refreshItem.setOnAction(event -> {
            executorService.schedule(this::loadData, 0, TimeUnit.SECONDS);
        });
        System.out.println("3");
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

    private void initTiles()
    {

        /*--Fluid Percentage Tile--*/
        fluidTile = TileBuilder.create().skinType(Tile.SkinType.FLUID)
                .prefSize(150, 150)
                .title("Ads Blocked")
                //.text("Waterlevel")
                .unit("\u0025")
                .decimals(0)
                .barColor(Tile.RED) // defines the fluid color, alternatively use sections or gradientstops
                .animated(true)
                .build();



        fluidTile.setValue(0.20 * 100);

        /*--LED Tile--*/
        ledTile = TileBuilder.create()
                .skinType(Tile.SkinType.LED)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Led Tile")
                .description("Description")
                .text("Whatever text")
                .build();


        /*--Status Tile--*/
        leftGraphics = new Indicator(Tile.RED);
        leftGraphics.setOn(true);

        middleGraphics = new Indicator(Tile.YELLOW);
        middleGraphics.setOn(true);

        rightGraphics = new Indicator(Tile.GREEN);
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


        /*--Other Percentage Tile--*/
        /*
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
        */
    }


}