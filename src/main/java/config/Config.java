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
