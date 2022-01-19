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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import services.configuration.ConfigurationService;

import java.net.URL;
import java.util.ResourceBundle;


public class ConfigurationController implements Initializable {

    @FXML
    private Button button_cancel, button_save, button_load,button_apply;

    @FXML
    private TitledPane dns1TitledPane;
    @FXML
    private Accordion accord;

    @FXML
    private TextField TF_IP1, TF_IP2, TF_AUTH1, TG_AUTH2;


    private PiholeConfig configDNS1;
    private PiholeConfig configDNS2;
    private WidgetConfig widgetConfig = null;


    public ConfigurationController(PiholeConfig configDNS1, PiholeConfig configDNS2/*,WidgetConfig widgetConfig*/) {
        this.configDNS1 = configDNS1;
        this.configDNS2 = configDNS2;
       // this.widgetConfig = widgetConfig;
    }

    public void initialize(URL location, ResourceBundle resources) {

        dns1TitledPane.setExpanded(true);
        accord.setExpandedPane(dns1TitledPane);
        loadConfiguration();
        button_apply.setOnMouseClicked(event -> WidgetApplication.applyAndCloseConfigurationWindow());
        button_save.setOnMouseClicked(event -> saveConfiguration());
        button_load.setOnMouseClicked(event -> loadConfiguration());
        button_cancel.setOnMouseClicked(event -> WidgetApplication.closeConfigurationWindow());

    }


    @FXML
    public void saveConfiguration() {

        ConfigurationService confService = new ConfigurationService();
        confService.writeConfigFile(TF_IP1.getText(), TF_AUTH1.getText(), TF_IP2.getText(), TG_AUTH2.getText()/*, widgetConfig.getTile_Width(), widgetConfig.getTile_Height(), widgetConfig.isShow_live(), widgetConfig.isShow_status(), widgetConfig.isShow_fluid()*/);

    }

    @FXML
    public void loadConfiguration() {
        ConfigurationService confService = new ConfigurationService();
        confService.getConfiguration();
        configDNS1=confService.getConfigDNS1();
        configDNS2=confService.getConfigDNS2();

        TF_IP1.setText(configDNS1.getIPAddress());
        TF_AUTH1.setText(configDNS1.getAUTH());

        TF_IP2.setText(configDNS2.getIPAddress());
        TG_AUTH2.setText(configDNS2.getAUTH());
    }


}
