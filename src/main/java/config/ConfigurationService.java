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
