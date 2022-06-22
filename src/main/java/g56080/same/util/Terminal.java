package g56080.same.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

/**
 * Terminal class used to execute commands.
 **/
public class Terminal{
    
    private final static int MAX_CMD_LINE_ARG = 50;

    /**
     * Executes the given command with the given arguments and returns the output of this command. A value of
     * null will be returned if an error occured when processing the command. By default, this method strip the output.
     * @param cmd the command to execute
     * @param args the arguments for the command
     * @return the output of the given command or null if an error occured.
     **/
    public static String exec(String cmd, String ...args){
        return exec(cmd, true, args);
    }

    /**
     * Executes the given command with the given arguments and returns the output of this command. A value of
     * null will be returned if an error occured when processing the command. Using this method, the command can be
     * strip if stripOutput is set to true, or not if set to false.
     * @param cmd the command to execute
     * @param stripOutput the boolean value used to determine if the output has to be stripped or not.
     * @param args the arguments for the command
     * @return the output of the given command or null if an error occured.
     **/
    public static String exec(String cmd, boolean stripOutput, String ...args){
        if(cmd == null || args.length > MAX_CMD_LINE_ARG){
            IllegalArgumentException exc = new IllegalArgumentException("The given arguments are not valid.");
            if(cmd == null) 
                exc.initCause(new NullPointerException("The given command is null"));
            else 
                exc.initCause(new UnsafeVarargsException("Too many arguments have been passed to the command."));

            throw exc;
        }

        boolean isCmdExecuted = false;
        List<String> command = new ArrayList<String>(Collections.singletonList(cmd));
        command.addAll(List.of(args));
        ProcessBuilder pBuilder = new ProcessBuilder(command);
        String result = null;

        try{
            Process p = pBuilder.start(); p.waitFor();
            result = getProcessOutput(p, stripOutput);
            isCmdExecuted = true;
        } catch(IOException | InterruptedException exc){
            exc.printStackTrace(System.err);
        }

        return isCmdExecuted ? result : null;
    }

    /**
     * Checks if the given command can be executed.
     * @param cmd the command to check
     * @return true if the given command can be executed, false otherwise.
     **/
    public static boolean canExec(String cmd){
        if(cmd == null) return false;
        ProcessBuilder pBuilder = new ProcessBuilder(cmd);
        boolean cmdValidity = true;

        try{
            Process p = pBuilder.start();
            p.destroy();
        } catch(IOException exc){
            cmdValidity = false;
        }

        return cmdValidity;
    }

    private static final String getProcessOutput(Process p, boolean strip) throws IOException{
        StringBuilder output = new StringBuilder();

        try(InputStream ins = p.getInputStream()){
            int b = 0;

            while((b = ins.read()) != -1){
                output.append((char) b);
            }
            
        }

        output.trimToSize(); // to retrieve some extra space in memory
        
        return strip ? output.toString().strip() : output.toString();
    }
}
