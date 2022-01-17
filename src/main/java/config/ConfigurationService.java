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

package config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationService {

    private File SETTTINGS_FILE = null;

    private final String folder_name = "Pihole Widget";
    private final String file_name = "settings.json";
    private final String home = System.getProperty("user.home");
    private final String file_path=home + "/" + folder_name + "/" + file_name;

    private PiholeConfig configDNS1;
    private PiholeConfig configDNS2;

    public void getConfiguration() {

        SETTTINGS_FILE = new java.io.File(file_path);
        System.out.println(SETTTINGS_FILE);
        if (SETTTINGS_FILE == null || (SETTTINGS_FILE != null && !SETTTINGS_FILE.exists()))
            setConfiguration();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(SETTTINGS_FILE));

            JSONArray jsonObject = (JSONArray) obj;
            JSONObject jsonDNS1= (JSONObject) jsonObject.get(0);
            JSONObject jsonDNS2= (JSONObject) jsonObject.get(1);

            if(!jsonDNS1.get("IP").toString().isEmpty())
            configDNS1 = new PiholeConfig((String) jsonDNS1.get("IP"), (String) jsonDNS1.get("Authentication"));
            else
                System.out.println("Pihole DNS 1 IP Address is empty");

            if(!jsonDNS2.get("IP").toString().isEmpty())
            configDNS2 = new PiholeConfig((String) jsonDNS2.get("IP"), (String) jsonDNS2.get("Authentication"));
            else
                System.out.println("Pihole DNS 2 IP Address is empty");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PiholeConfig getConfigDNS1() {
        return configDNS1;
    }

    public PiholeConfig getConfigDNS2() {
        return configDNS2;
    }

    public boolean setConfiguration() {

        SETTTINGS_FILE = createFile();

        return writeConfigFile();

    }

    public File createFile() {
        File folder = new File(home + "/" + folder_name);

        if (createFolder())
            try {
                File myObj = new File(file_path);
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
                return myObj;
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return null;
            }
        else {
            System.out.println("Couldn't create Folder: " + folder_name);
            return null;
        }
    }

    public boolean writeConfigFile()
    {
        JSONArray jsonObject = new JSONArray();

        JSONObject jsonDNS1 = new JSONObject();
        jsonDNS1.put("IP", "");
        jsonDNS1.put("Authentication Token", "");

        JSONObject jsonDNS2 = new JSONObject();
        jsonDNS2.put("IP", "");
        jsonDNS2.put("Authentication Token", "");

        jsonObject.add(jsonDNS1);
        jsonObject.add(jsonDNS2);

        FileWriter file = null;
        try {
            file = new FileWriter(file_path);

            file.write(jsonObject.toJSONString());

            file.close();

            System.out.println("JSON Written.");

            return true;
        } catch (IOException e) {

            System.out.println("Couldn't write JSON.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean createFolder() {
        File f1 = new File(home + "/" + folder_name);
        if(f1.exists())
            return true;
        return f1.mkdir();
    }
}
