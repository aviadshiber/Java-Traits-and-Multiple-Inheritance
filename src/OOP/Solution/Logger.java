package OOP.Solution;

/**
 * Created by ashiber on 07-Jun-17.
 */
public class Logger {
    public static boolean isActive=false;

    public static void log(Object message){
        if(isActive)
            System.out.println(message);
    }

}
