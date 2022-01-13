package services.piholeproject;

import domain.piholeproject.Gravity;
import domain.piholeproject.PiHole;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PiHoleHandler {

	private String IPAddress;
	private String Auth;

	private HttpURLConnection conn;
	private URL url;
	private InputStreamReader in;
	private BufferedReader br;

	private JSONObject jsonResult;
	private JSONParser parser;
	private String output;



	public PiHoleHandler(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	public PiHoleHandler(String IPAddress, String auth) {
		this.IPAddress = IPAddress;
		Auth = auth;
	}

	public PiHole getPiHoleStats() {

		initAPI("summary","","");


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



		JSONObject gravity_json=(JSONObject) jsonResult.get("gravity_last_updated");
		JSONObject relative_json=(JSONObject) gravity_json.get("relative");
		
		boolean file_exists =  (boolean) gravity_json.get("file_exists");
		Long absolute=(Long) gravity_json.get("absolute");
		Long days=(Long) relative_json.get("days");
		Long hours=(Long) relative_json.get("hours");
		Long minutes=(Long) relative_json.get("minutes");
				
		Gravity gravity = new Gravity(file_exists,absolute,days,hours,minutes);
		
		Long domains_being_blocked=(Long) jsonResult.get("domains_being_blocked");
		Long dns_queries_today=(Long) jsonResult.get("dns_queries_today");
		Long ads_blocked_today=(Long) jsonResult.get("ads_blocked_today");
		Double ads_percentage_today=(Double) jsonResult.get("ads_percentage_today");
		Long unique_domains=(Long) jsonResult.get("unique_domains");
		Long queries_forwarded=(Long) jsonResult.get("queries_forwarded");
		Long queries_cached=(Long) jsonResult.get("queries_cached");
		Long clients_ever_seen=(Long) jsonResult.get("clients_ever_seen");
		Long unique_clients=(Long) jsonResult.get("unique_clients");
		Long dns_queries_all_types=(Long) jsonResult.get("dns_queries_all_types");
		Long reply_NODATA=(Long) jsonResult.get("reply_NODATA");
		Long reply_NXDOMAIN=(Long) jsonResult.get("reply_NXDOMAIN");
		Long reply_CNAME=(Long) jsonResult.get("reply_CNAME");
		Long reply_IP=(Long) jsonResult.get("reply_IP");
		Long privacy_level=(Long) jsonResult.get("privacy_level");
		String status= (String) jsonResult.get("status");
		
		return new PiHole(domains_being_blocked,dns_queries_today,ads_blocked_today,ads_percentage_today,unique_domains
				,queries_forwarded,queries_cached,clients_ever_seen,unique_clients,dns_queries_all_types,reply_NODATA,
				reply_NXDOMAIN,reply_CNAME,reply_IP,privacy_level,status,gravity);
	}

	public String getLastBlocked(){
		return "";
	}

	private void initAPI(String Param,String ParamVal,String Auth) {

		String fullAuth="";
		String fullParam="";
		String fullParamVal="";

		if(!Param.isEmpty())
			fullParam="?"+Param;


		if(!ParamVal.isEmpty())
			fullParamVal="="+ParamVal;

		if(!Auth.isEmpty())
			fullAuth="&"+Auth;


		// API Settings
		try {


			url = new URL("http://"+IPAddress+"/admin/api.php"+fullParam+fullParamVal+fullAuth);
			System.out.println(url);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

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
	}

}
