package domain;

public class TopAd {

    String domain;
    Long numberBlocked;

    public TopAd(String domain, Long numberBlocked) {
        this.domain = domain;
        this.numberBlocked = numberBlocked;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getNumberBlocked() {
        return numberBlocked;
    }

    public void setNumberBlocked(Long numberBlocked) {
        this.numberBlocked = numberBlocked;
    }
}
