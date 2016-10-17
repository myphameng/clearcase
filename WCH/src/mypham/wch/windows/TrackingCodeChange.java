package mypham.wch.windows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mypham.wch.windows.cleartool.CiManager;

public class TrackingCodeChange extends Task{

    CiManager cim = new CiManager();
    
    /**
     * Get original code and the latest code (ignore elements have skipped version as the latest version) <br>
     * then put them in a directory for user to compare. 
     * @param view view name
     * @param brName selected branch.  
     * @param dDir The destination folder where the original and the latest code will be saved
     * @throws IOException
     */
    public void trackChange(String view, String vob
            , String brName, File dDir) throws IOException
    {
        String orDir = dDir + "\\original";
        String laDir = dDir + "\\latest";
        List<String> ciElements = cim.findNot(view, vob, brName, 0); //(skippedVersion < 0) ? cim.find(view, vob, brName) 
        List<String> downloadedFiles = getCodeFromCC(view, vob, brName, ciElements, orDir, laDir);
        writeToReport(vob, downloadedFiles, dDir);
    }
    
    protected void writeToReport(String vob, List<String> downloadedFiles, File dDir) {
        try {
            File fReport = new File(dDir + String.format("\\%s.txt", vob));
            if (!fReport.exists()) {
                fReport.createNewFile();
            }
            
            FileWriter fw = new FileWriter(fReport.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (String fullPath : downloadedFiles) {
                bw.write(fullPath);
                bw.write("\n");
            }
            
            bw.close();
 
        } catch (IOException e) {
            logger.debug(e);
        }
    }
    
    protected List<String> getCodeFromCC(String view, String vob, String brName
            , List<String>  ciElements, String orDir, String laDir) throws IOException 
    {
        List<String> listOfFiles = new ArrayList<String>(ciElements.size());
        for (String pname_latest : ciElements) 
        {
            String element = pname_latest.substring(0, pname_latest.indexOf("@@"));
            
            if (new File(element).isFile()) 
            {
                element = element.substring(element.indexOf(view) + view.length());
                listOfFiles.add(element);
                
                String orDest = orDir + element;
                String laDest = laDir + element;
                
                if (! getReady(orDest)) throw new IOException(String.format("Failed to create %s", orDest));
                if (! getReady(laDest)) throw new IOException(String.format("Failed to create %s", laDest));

                String pname_v0 = 
                        pname_latest.substring(0
                                , pname_latest.lastIndexOf(File.separator) + 1
                        ) + 0;
                
                cim.getTo(view, pname_v0, orDest);
                cim.getTo(view, pname_latest, laDest);
            }
        }
        return listOfFiles;
    }
    
    /**
     * 
     * @param dest
     * @return
     */
    protected boolean getReady(String dest) {
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        } else f.getParentFile().mkdirs();
        return f.getParentFile().isDirectory();
    }
}
