package domain.piholeproject;

public class PiHole {

	Long domains_being_blocked;
	Long dns_queries_today;
	Long ads_blocked_today;
	Double ads_percentage_today;
	Long unique_domains;
	Long queries_forwarded;
	Long queries_cached;
	Long clients_ever_seen;
	Long unique_clients;
	Long dns_queries_all_types;
	Long reply_NODATA;
	Long reply_NXDOMAIN;
	Long reply_CNAME;
	Long reply_IP;
	Long privacy_level;
	String status;
	Gravity gravity;

	public PiHole() {

	}
	
	

	public PiHole(Long domains_being_blocked, Long dns_queries_today, Long ads_blocked_today,
			Double ads_percentage_today, Long unique_domains, Long queries_forwarded, Long queries_cached,
			Long clients_ever_seen, Long unique_clients, Long dns_queries_all_types, Long reply_NODATA,
			Long reply_NXDOMAIN, Long reply_CNAME, Long reply_IP, Long privacy_level, String status,
			Gravity gravity) {
		super();
		this.domains_being_blocked = domains_being_blocked;
		this.dns_queries_today = dns_queries_today;
		this.ads_blocked_today = ads_blocked_today;
		this.ads_percentage_today = ads_percentage_today;
		this.unique_domains = unique_domains;
		this.queries_forwarded = queries_forwarded;
		this.queries_cached = queries_cached;
		this.clients_ever_seen = clients_ever_seen;
		this.unique_clients = unique_clients;
		this.dns_queries_all_types = dns_queries_all_types;
		this.reply_NODATA = reply_NODATA;
		this.reply_NXDOMAIN = reply_NXDOMAIN;
		this.reply_CNAME = reply_CNAME;
		this.reply_IP = reply_IP;
		this.privacy_level = privacy_level;
		this.status = status;
		this.gravity = gravity;
	}



	public Long getDomains_being_blocked() {
		return domains_being_blocked;
	}

	public void setDomains_being_blocked(Long domains_being_blocked) {
		this.domains_being_blocked = domains_being_blocked;
	}

	public Long getDns_queries_today() {
		return dns_queries_today;
	}

	public void setDns_queries_today(Long dns_queries_today) {
		this.dns_queries_today = dns_queries_today;
	}

	public Long getAds_blocked_today() {
		return ads_blocked_today;
	}

	public void setAds_blocked_today(Long ads_blocked_today) {
		this.ads_blocked_today = ads_blocked_today;
	}

	public Double getAds_percentage_today() {
		return ads_percentage_today;
	}

	public void setAds_percentage_today(Double ads_percentage_today) {
		this.ads_percentage_today = ads_percentage_today;
	}

	public Long getUnique_domains() {
		return unique_domains;
	}

	public void setUnique_domains(Long unique_domains) {
		this.unique_domains = unique_domains;
	}

	public Long getQueries_forwarded() {
		return queries_forwarded;
	}

	public void setQueries_forwarded(Long queries_forwarded) {
		this.queries_forwarded = queries_forwarded;
	}

	public Long getQueries_cached() {
		return queries_cached;
	}

	public void setQueries_cached(Long queries_cached) {
		this.queries_cached = queries_cached;
	}

	public Long getClients_ever_seen() {
		return clients_ever_seen;
	}

	public void setClients_ever_seen(Long clients_ever_seen) {
		this.clients_ever_seen = clients_ever_seen;
	}

	public Long getUnique_clients() {
		return unique_clients;
	}

	public void setUnique_clients(Long unique_clients) {
		this.unique_clients = unique_clients;
	}

	public Long getDns_queries_all_types() {
		return dns_queries_all_types;
	}

	public void setDns_queries_all_types(Long dns_queries_all_types) {
		this.dns_queries_all_types = dns_queries_all_types;
	}

	public Long getReply_NODATA() {
		return reply_NODATA;
	}

	public void setReply_NODATA(Long reply_NODATA) {
		this.reply_NODATA = reply_NODATA;
	}

	public Long getReply_NXDOMAIN() {
		return reply_NXDOMAIN;
	}

	public void setReply_NXDOMAIN(Long reply_NXDOMAIN) {
		this.reply_NXDOMAIN = reply_NXDOMAIN;
	}

	public Long getReply_CNAME() {
		return reply_CNAME;
	}

	public void setReply_CNAME(Long reply_CNAME) {
		this.reply_CNAME = reply_CNAME;
	}

	public Long getReply_IP() {
		return reply_IP;
	}

	public void setReply_IP(Long reply_IP) {
		this.reply_IP = reply_IP;
	}

	public Long getPrivacy_level() {
		return privacy_level;
	}

	public void setPrivacy_level(Long privacy_level) {
		this.privacy_level = privacy_level;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}



	@Override
	public String toString() {
		return "PiHole [domains_being_blocked=" + domains_being_blocked + ", dns_queries_today=" + dns_queries_today
				+ ", ads_blocked_today=" + ads_blocked_today + ", ads_percentage_today=" + ads_percentage_today
				+ ", unique_domains=" + unique_domains + ", queries_forwarded=" + queries_forwarded
				+ ", queries_cached=" + queries_cached + ", clients_ever_seen=" + clients_ever_seen
				+ ", unique_clients=" + unique_clients + ", dns_queries_all_types=" + dns_queries_all_types
				+ ", reply_NODATA=" + reply_NODATA + ", reply_NXDOMAIN=" + reply_NXDOMAIN + ", reply_CNAME="
				+ reply_CNAME + ", reply_IP=" + reply_IP + ", privacy_level=" + privacy_level + ", status=" + status
				+ ", gravity=" + gravity + "]";
	}
	
	

}
