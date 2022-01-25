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

package domain.configuration;

public class WidgetConfig {


    private String size;
    private String layout;
    private boolean show_live;
    private boolean show_status;
    private boolean show_fluid;
    private int update_status_sec;
    private int update_fluid_sec;
    private int update_active_sec;

    public WidgetConfig(String size, String layout, boolean show_live, boolean show_status, boolean show_fluid, int update_status_sec, int update_fluid_sec, int update_active_sec) {
        this.size = size;
        this.layout = layout;
        this.show_live = show_live;
        this.show_status = show_status;
        this.show_fluid = show_fluid;
        this.update_status_sec = update_status_sec;
        this.update_fluid_sec = update_fluid_sec;
        this.update_active_sec = update_active_sec;
    }


    public String getSize() {
        return size;
    }

    public String getLayout() {
        return layout;
    }

    public boolean isShow_live() {
        return show_live;
    }

    public boolean isShow_status() {
        return show_status;
    }

    public boolean isShow_fluid() {
        return show_fluid;
    }

    public int getUpdate_status_sec() {
        return update_status_sec;
    }

    public int getUpdate_fluid_sec() {
        return update_fluid_sec;
    }

    public int getUpdate_active_sec() {
        return update_active_sec;
    }
}
