package model;

import java.sql.Timestamp;

public class AccessLog {
    private int logId;
    private int userId;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public AccessLog() {}

    // Full constructor
    public AccessLog(int logId, int userId, Timestamp loginTime, Timestamp logoutTime) {
        this.logId = logId;
        this.userId = userId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    // Getters and setters
    public int getLogId() { return logId; }
    public void setLogId(int id) { this.logId = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getLoginTime() { return loginTime; }
    public void setLoginTime(Timestamp loginTime) { this.loginTime = loginTime; }

    public Timestamp getLogoutTime() { return logoutTime; }
    public void setLogoutTime(Timestamp logoutTime) { this.logoutTime = logoutTime; }
}
