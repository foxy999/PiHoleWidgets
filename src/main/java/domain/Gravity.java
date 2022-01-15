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
