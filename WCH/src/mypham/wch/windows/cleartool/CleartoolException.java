package mypham.wch.windows.cleartool;

import java.util.List;

public class CleartoolException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 879172076366388012L;

    private String command;
    private List<String> result, error;
    
    public CleartoolException(String cmd, List<String> result,
            List<String> error) {
        this.command = cmd;
        this.result = result;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getResult() {
        return result;
    }

    public List<String> getError() {
        return error;
    }

}
