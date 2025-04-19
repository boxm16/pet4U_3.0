package BasicModel;

import java.util.ArrayList;
import java.util.Date;

public class Logger {

    private String user;
    private String invoiceId;
    private Date creationDate;

    private ArrayList<Log> logs;

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

}
