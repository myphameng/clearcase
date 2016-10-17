package mypham.wch.windows.cleartool;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CoManager extends Cleartool
{
    
    /**
     * API for cleartool <br>
     * List all files and directories checked out from a specific branch by current user<br>
     *   
     * @param rDir root directory which view mapped to. Ex, C:
     * @param branch selected branch
     * @return
     * @throws Exception
     */
    public List<String> lsCheckOut(String view, String branch) throws IOException
    {
        Cleartool ct = new Cleartool();
        ct.setView(view);
        String ctCmd = String.format("lscheckout -cview -me -avobs -s -brtype \"%s\"", branch);        
        List<String> checkedOutFiles = ct.execQuietly(ctCmd);
        return checkedOutFiles;
    }
    
    /**
     * Find and copy file checked out to a directory
     * @param view root directory which view is mapped to. Ex, C:
     * @param branch selected branch
     * @param toDir destination
     * @throws IOException
     */
    public void copy(String view, String branch, File toDir) throws IOException
    {
        Cleartool ct = new Cleartool();
        ct.setView(view);
        String ctctx = ct.getViewContext();
        if ( toDir.isDirectory() )
        {
            List<String> checkedOutFiles = lsCheckOut(view, branch);
            for (String s : checkedOutFiles)
            {
                File src = new File(ctctx + s);
                File dest = new File(toDir + s);
                if (src.isFile())
                {
                    new File(dest.getParent()).mkdirs();
                    FileUtils.copyFile(src, dest, true);
                }
            }
        } else {
            throw new IOException(String.format("%s is not a directory.", toDir));
        }
    }
    
    public static void main(String...args) throws IOException
    {
        String view = "phammt_wr8202_batch_dev";
        String brtype = "regcore_4.59_regcore_8078_par_officials_reports";
        CoManager lsco = new CoManager();
        lsco.copy(view, brtype, new File("C:\\test"));
    }
}
