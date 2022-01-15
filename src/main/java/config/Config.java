package config;

public class Config {
    private String IPAddress;
    private String AUTH;
    private int Tile_Width;
    private int Tile_Height;
    private boolean show_live;
    private boolean show_status;
    private boolean show_fluid;


    public Config(String IPAddress, String AUTH) {
        this.IPAddress = IPAddress;
        this.AUTH = AUTH;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getAUTH() {
        return AUTH;
    }

    public void setAUTH(String AUTH) {
        this.AUTH = AUTH;
    }

    public int getTile_Width() {
        return Tile_Width;
    }

    public void setTile_Width(int tile_Width) {
        Tile_Width = tile_Width;
    }

    public int getTile_Height() {
        return Tile_Height;
    }

    public void setTile_Height(int tile_Height) {
        Tile_Height = tile_Height;
    }

    public boolean isShow_live() {
        return show_live;
    }

    public void setShow_live(boolean show_live) {
        this.show_live = show_live;
    }

    public boolean isShow_status() {
        return show_status;
    }

    public void setShow_status(boolean show_status) {
        this.show_status = show_status;
    }

    public boolean isShow_fluid() {
        return show_fluid;
    }

    public void setShow_fluid(boolean show_fluid) {
        this.show_fluid = show_fluid;
    }
}
