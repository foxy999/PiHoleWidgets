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

package domain;

public class Gravity {
	
	boolean file_exists;
	Long absolute;
	Long days;
	Long hours;
	Long minutes;
	
	
	public Gravity() {
		super();
	}

	

	public Gravity(boolean file_exists, Long absolute, Long days, Long hours, Long minutes) {
		super();
		this.file_exists = file_exists;
		this.absolute = absolute;
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}



	public boolean isFile_exists() {
		return file_exists;
	}


	public void setFile_exists(boolean file_exists) {
		this.file_exists = file_exists;
	}


	public double getAbsolute() {
		return absolute;
	}


	public void setAbsolute(Long absolute) {
		this.absolute = absolute;
	}


	public Long getDays() {
		return days;
	}


	public void setDays(Long days) {
		this.days = days;
	}


	public Long getHours() {
		return hours;
	}


	public void setHours(Long hours) {
		this.hours = hours;
	}


	public Long getMinutes() {
		return minutes;
	}


	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}



	@Override
	public String toString() {
		return "Gravity [file_exists=" + file_exists + ", absolute=" + absolute + ", days=" + days + ", hours=" + hours
				+ ", minutes=" + minutes + "]";
	}
	
	
}
