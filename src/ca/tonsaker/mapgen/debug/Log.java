package ca.tonsaker.mapgen.debug;

import java.time.LocalDateTime;

public abstract class Log {

    private static DebugWindow debugWindow;

    public static void setDebugWindow(DebugWindow debugWindow){
        Log.debugWindow = debugWindow;
    }

    private static void log(String stamp, String s){
        String consoleText = LocalDateTime.now().toString()+": "+s;
        System.out.println(stamp+" "+consoleText);
        debugWindow.addToConsole(consoleText);
    }

    public static void d(String s){
        Log.log("DEBUG",s);
    }

}
