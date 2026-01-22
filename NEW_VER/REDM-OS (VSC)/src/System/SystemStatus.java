package System;

import java.util.HashSet;
import IO.*;

public class SystemStatus {
    String stability;
    int integrity;
    int activeProcesses;
    int quequedTasks;
    HashSet<String> modes;

    public static void execute(){
        IO.output("Retrieving system information...");
        IO.loadingBar(60, 40);
        IO.output("[SYSTEM STATUS]");
        IO.output("--General--");
        IO.output("Identity: RedM-OS " + SystemInfo.getVersion());
        IO.output("Uptime: " + SystemState.getUptime());
        IO.output("--Users--");
        IO.output("Registered users: Not decided yet.");
        IO.output("Active user: Not decided yet.");//user(type[role])
        IO.output("Permission level: Not decided yet.");
        IO.output("User passby: Not decided yet.");//comando 'passby [user]'...
        IO.output("--Files--");
        IO.output("Filesystem integrity: Not decided yet."); //decidir estado dinámicamente
        IO.output("Suspicious files: Not decided yet."); //archivos que tengan una extensión determinada se consideran sospechosos
        IO.output("Tags: Not decided yet.");
        IO.output("Locked files: Not decided yet.");
        IO.output("[More things coming soon...]");
    }


}
