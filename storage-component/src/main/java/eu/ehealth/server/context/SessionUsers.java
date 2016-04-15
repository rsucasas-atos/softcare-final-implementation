package eu.ehealth.server.context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * 
 * @author a572832
 *
 */
public class SessionUsers {

	
	private String userId;
	private String userName;
	private String userRol;
	private int rol;
	private UUID sessionId;
	private long creationTime;
	private long connections;
	private long connectedTime;
	private long lastConnectionTime;
	private String status;
	private String comments;
	
	
	/**
	 * 
	 * @param userId
	 * @param rol
	 * @param username
	 * @param srol
	 */
	public SessionUsers(String userId, int rol, String username, String srol) {
		this.userId = userId;
		this.rol = rol;
		this.sessionId = UUID.randomUUID();
		this.creationTime = System.currentTimeMillis();
		this.connections = 0;
		this.connectedTime = 0;
		this.lastConnectionTime = this.creationTime;
		this.userName = username;
		this.userRol = srol;
	}


	public String getUserId() {
		return userId;
	}
	
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public int getRol() {
		return rol;
	}
	
	
	public void setRol(int rol) {
		this.rol = rol;
	}
	
	
	public UUID getSessionId() {
		return sessionId;
	}
	
	
	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}
	
	
	public long getTime() {
		return creationTime;
	}
	
	
	public long getLastConnectionTime() {
		return lastConnectionTime;
	}
	
	
	public void setLastConnectionTime(long lastConnectionTime) {
		this.lastConnectionTime = lastConnectionTime;
	}
	
	
	public void updateValues() 
	{
		long currentTime = System.currentTimeMillis();
		
		this.lastConnectionTime = currentTime;
		this.connectedTime = currentTime - this.creationTime;
		this.connections++;
		
		/*
		StorageComponentMain.scLog("DEBUG", "**** User's connected ....");
		StorageComponentMain.scLog("DEBUG", "**** USER: '" + userName + "' with ID: '" + userId + "' and ROLE: " + userRol + " ");
		StorageComponentMain.scLog("DEBUG", "**** SESSION ID: 		" + sessionId.toString());
		StorageComponentMain.scLog("DEBUG", "**** CONNECTIONS: 		" + connections);
		StorageComponentMain.scLog("DEBUG", "**** TIME CONNECTED: 	" + (connectedTime / 1000) + " seconds");
		StorageComponentMain.scLog("DEBUG", "**** 					" + ((connectedTime / 1000) / 60) + " minutes");
		*/
	}


	public long getCreationTime() {
		return creationTime;
	}


	public long getConnections() {
		return connections;
	}


	public long getConnectedTime() {
		return connectedTime;
	}


	public String getUserName() {
		return userName;
	}


	public String getUserRol() {
		return userRol;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String[] getFormattedValues() 
	{
		// time connected
		long currentTime = System.currentTimeMillis();
		this.connectedTime = currentTime - this.creationTime;
		// format initial time
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date resultdate = new Date(creationTime);
		
		String[] values = new String[9];
		values[0] = userName;
		values[1] = userId;
		values[2] = userRol;
		values[3] = ""; //sessionId.toString();
		values[4] = sdf.format(resultdate);
		values[5] = (connectedTime / 1000) + "s";
		values[6] = connections + "";
		values[7] = status;
		values[8] = comments;
		return values;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
