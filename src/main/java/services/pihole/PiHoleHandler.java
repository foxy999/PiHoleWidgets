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

package services.pihole;

import domain.pihole.Gravity;
import domain.pihole.PiHole;
import domain.pihole.TopAd;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PiHoleHandler {

    private final String IPAddress;
    private final String Auth;

    private HttpURLConnection conn;
    private InputStreamReader in;
    private BufferedReader br;

    private JSONObject jsonResult;
    private JSONParser parser;
    private String output;

    private int responseCode = 0;


    public PiHoleHandler(String IPAddress, String auth) {
        this.IPAddress = IPAddress;
        this.Auth = auth;
    }

    public PiHole getPiHoleStats() {

        if (!initAPI("summary", ""))
            return null;

        // Transform Raw result to JSON

        try {
            in = new InputStreamReader(conn.getInputStream());

            br = new BufferedReader(in);

            output = br.readLine();
            parser = new JSONParser();
            jsonResult = (JSONObject) parser.parse(output);


        } catch (IOException | ParseException ioe) {
            ioe.printStackTrace();
        }


        JSONObject gravity_json = (JSONObject) jsonResult.get("gravity_last_updated");
        JSONObject relative_json = (JSONObject) gravity_json.get("relative");

        boolean file_exists = (boolean) gravity_json.get("file_exists");
        Long absolute = (Long) gravity_json.get("absolute");
        Long days = (Long) relative_json.get("days");
        Long hours = (Long) relative_json.get("hours");
        Long minutes = (Long) relative_json.get("minutes");

        Gravity gravity = new Gravity(file_exists, absolute, days, hours, minutes);

        Long domains_being_blocked = (Long) jsonResult.get("domains_being_blocked");
        Long dns_queries_today = (Long) jsonResult.get("dns_queries_today");
        Long ads_blocked_today = (Long) jsonResult.get("ads_blocked_today");
        Double ads_percentage_today = (Double) jsonResult.get("ads_percentage_today");
        Long unique_domains = (Long) jsonResult.get("unique_domains");
        Long queries_forwarded = (Long) jsonResult.get("queries_forwarded");
        Long queries_cached = (Long) jsonResult.get("queries_cached");
        Long clients_ever_seen = (Long) jsonResult.get("clients_ever_seen");
        Long unique_clients = (Long) jsonResult.get("unique_clients");
        Long dns_queries_all_types = (Long) jsonResult.get("dns_queries_all_types");
        Long reply_NODATA = (Long) jsonResult.get("reply_NODATA");
        Long reply_NXDOMAIN = (Long) jsonResult.get("reply_NXDOMAIN");
        Long reply_CNAME = (Long) jsonResult.get("reply_CNAME");
        Long reply_IP = (Long) jsonResult.get("reply_IP");
        Long privacy_level = (Long) jsonResult.get("privacy_level");
        String status = (String) jsonResult.get("status");

        return new PiHole(domains_being_blocked, dns_queries_today, ads_blocked_today, ads_percentage_today, unique_domains
                , queries_forwarded, queries_cached, clients_ever_seen, unique_clients, dns_queries_all_types, reply_NODATA,
                reply_NXDOMAIN, reply_CNAME, reply_IP, privacy_level, status, gravity);
    }

    public String getLastBlocked() {
        if (Auth != null && !Auth.isEmpty()) {
            if (!initAPI("recentBlocked", ""))
                return "";

            // Transform Raw result to JSON

            try {
                in = new InputStreamReader(conn.getInputStream());

                br = new BufferedReader(in);

                output = br.readLine();
                parser = new JSONParser();
                return output;


            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else return "Please verify your Authentication Token";
    }

    public String getVersion() {

        if (!initAPI("type%20&%20version", ""))
            return "";

        // Transform Raw result to JSON

        try {
            in = new InputStreamReader(conn.getInputStream());

            br = new BufferedReader(in);

            output = br.readLine();
            parser = new JSONParser();
            try {
                jsonResult = (JSONObject) parser.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return jsonResult.get("version").toString();


        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String getTopXBlocked(int x) {
        if (Auth != null && !Auth.isEmpty()) {


            if (!initAPI("topItems", String.valueOf(x)))
                return "";

            // Transform Raw result to JSON

            try {
                in = new InputStreamReader(conn.getInputStream());

                br = new BufferedReader(in);

                output = br.readLine();
                parser = new JSONParser();
                try {
                    StringBuilder sb = new StringBuilder();


                    jsonResult = (JSONObject) parser.parse(output);
                    JSONObject topADS = (JSONObject) jsonResult.get("top_ads");
                    List<TopAd> list = new ArrayList<>();

                    topADS.forEach((key, value) -> list.add(new TopAd((String) key, (Long) value)));

                    list.sort((s1, s2) -> Long.compare(s2.getNumberBlocked(), s1.getNumberBlocked()));

                    sb.append("Top ").append(x).append(" blocked: \n\n");
                    for (TopAd s : list) {
                        sb.append(s.getDomain()).append(": ").append(s.getNumberBlocked()).append("\n\n");
                    }

                    return sb.toString();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else return "Please verify your Authentication Token";
    }

    public String getGravityLastUpdate() {

        PiHole pihole1 = getPiHoleStats();
        if (pihole1 != null) {
            String textToDisplay = "";
            long days = pihole1.getGravity().getDays();
            if (days <= 1)
                textToDisplay += days + " day";
            else
                textToDisplay += days + " days";

            long hours = pihole1.getGravity().getHours();
            if (hours <= 1)
                textToDisplay += " " + hours + " hour";
            else
                textToDisplay += " " + hours + " hours";

            long mins = pihole1.getGravity().getMinutes();
            if (mins <= 1)
                textToDisplay += " " + mins + " min";
            else
                textToDisplay += " " + mins + " mins";

            return textToDisplay;
        }
        return "";
    }

    public String getIPAddress() {
        return IPAddress;
    }

    private boolean initAPI(String Param, String ParamVal) {

        String fullAuth = "";
        String fullParam = "";
        String fullParamVal = "";

        if (!Param.isEmpty())
            fullParam = "?" + Param;


        if (!ParamVal.isEmpty())
            fullParamVal = "=" + ParamVal;

        if (Auth != null && !Auth.isEmpty())
            fullAuth = "&auth=" + this.Auth;

        System.out.println("IPAddress: "+IPAddress);

        if (!IPAddress.isEmpty()) {
            try {
                URL url = new URL("http://" + IPAddress + "/admin/api.php" + fullParam + fullParamVal + fullAuth);

                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(1000);
                conn.setReadTimeout(1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                responseCode = conn.getResponseCode();

                if (responseCode != 200) {
                    System.out.println("Failed : HTTP Error code : " + responseCode);
                    conn.disconnect();
                    return false;
                }
                return true;

            } catch (IOException e) {
                System.out.println("API Error for:" + IPAddress + " " + e.getMessage());
                conn.disconnect();
                return false;
            }
        }
        return false;
    }

}
