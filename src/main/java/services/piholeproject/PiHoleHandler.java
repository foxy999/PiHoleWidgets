package services.piholeproject;

import domain.piholeproject.Gravity;
import domain.piholeproject.PiHole;
import org.json.simple.JSONObject;

public class PiHoleHandler {
	
	
	public PiHole getPiHoleFromJSON(JSONObject jsonResult) {
		
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

}
