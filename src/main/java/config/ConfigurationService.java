package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class ConfigurationService {

    private final File SETTTINGS_FILE = new File("settings.json");

    public Config getConfiguration(){

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(SETTTINGS_FILE));

            JSONObject jsonObject = (JSONObject) obj;

            Config conf=new Config((String) jsonObject.get("IP"), (String) jsonObject.get("Authentication"));

            return conf;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
