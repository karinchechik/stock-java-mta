package il.ac.mta.dto;

import java.util.Date;


/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 11, 2015
 */
public class StatusDto {

	public final static String SUCCESS = "success";
	public final static String ERROR = "error";
	
	public String status = SUCCESS;
	public Date time;
	public String error;
	
	public StatusDto() {
		time = new Date();
	}
	
	public StatusDto(String errorReason) {
		this();
		status = ERROR;
		error = errorReason;
	}

	public String getStatus() {
		return status;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getError() {
		return error;
	}

	public boolean isSuccess() {
		return status.equals(SUCCESS);
	}
}
