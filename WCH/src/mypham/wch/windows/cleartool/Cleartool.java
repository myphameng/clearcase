package mypham.wch.windows.cleartool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mypham.wch.windows.LogManager;

public class Cleartool {
    
    private String vobRoot = "M:\\";
    private String view;
    private File viewCtx;
    
    public void setView(String viewTag) {
        view = viewTag;
        this.viewCtx = new File(vobRoot + viewTag);
    }
    
    public String getView() {
        return view;
    }
  
    /**
     * Execute a cleartool command <br>
     * View must be be for 
     * @param ctCmd Cleartool command
     * @return
     * @throws IOException
     */
    public List<String> execQuietly(String ctCmd) throws IOException
    {
        String cmd = "cleartool " + ctCmd;
        LogManager.getCTLogger().info("***Executing: " + cmd);
        
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd, null, viewCtx);
        
        List<String> result = getResult(process); 
        
        logResult(result);  
        logErrors(process);
        
        return result;
    }

    /**
     * 
     * @param ctCmd Cleartool command
     * @param ctx context (working dir)
     * @return
     * @throws IOException
     */
    public List<String> execQuietly(String ctCmd, String vob) throws IOException
    {
        String cmd = "cleartool " + ctCmd;
        LogManager.getCTLogger().info("***Executing: " + cmd);
        
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd
                , null
                , new File(getContext(vob))
        );
        
        List<String> result = getResult(process); 
        logResult(result);
        
        logErrors(process);
        
        return result;
    }
    

    private List<String> getResult(Process process) throws IOException {
        BufferedInputStream buffer = new BufferedInputStream(process.getInputStream());
        BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));
        String line = commandResult.readLine();
        List<String> result = new ArrayList<String>();
        while ( null != line )
        {
            result.add(line);
            line = commandResult.readLine();
        }
        return result;
    }

    private void logResult(List<String> messages) throws IOException {       
        for (String m : messages)
        {
            LogManager.getCTLogger().info(m);
        }      
    }


    
    private void logErrors(Process process) throws IOException {
        BufferedInputStream errBuffer = new BufferedInputStream(process.getErrorStream());
        BufferedReader errReader = new BufferedReader(new InputStreamReader(errBuffer));
        String line = errReader.readLine();
        if ( null != line ) {
            LogManager.getCTLogger().info("- Warning/Error:");
        }
        while ( null != line )
        {
            line = errReader.readLine();
            LogManager.getCTLogger().info(line);
        }
    }

    protected String getContext(String vob) throws IOException {
        if (null == viewCtx) throw new IOException("View has not been set!");
        return viewCtx.getPath() + File.separator + vob;
    }
    
    protected String getViewContext() throws IOException {
        if (null == viewCtx) throw new IOException("View has not been set!");
        return viewCtx.getPath();
    }
}
