package mypham.wch.windows.cleartool;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author phammt
 *
 */
public class CiManager extends Cleartool 
{
       
    /**
     * Find all elements and the latest version of them in a specific view.<br>
     * @param view  
     * @return
     * @throws IOException
     */
    public List<String> find(String view) throws IOException  {
        setView(view);
        return super.execQuietly("find -avobs -print");
    }
    
    /**
     * Find all elements of a specific branch in all VOBs.<br>
     * @param view  
     * @param branch    selected branch
     * @return
     * @throws IOException
     */
    public List<String> find(String view, String branch) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find -avobs -version \"version(.../%s/LATEST)\" -print", branch, branch);      
        return super.execQuietly(ctCmd);
    }
    
    /**
     * Find all elements of a specific branch in a specific VOB.<br>
     * @param view  
     * @param branch    selected branch
     * @return
     * @throws IOException
     */
    public List<String> find(String view, String vob, String branch) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find %s -version \"version(.../%s/LATEST)\" -print", super.getContext(vob), branch, branch);      
        return super.execQuietly(ctCmd, vob);
    }
    
    /**
     * Find in a VOB, on a specific branch, elements has the latest version is not the specified version<br>
     * @param view  
     * @param branch    selected branch
     * @return
     * @throws IOException
     */
    public List<String> findNot(String view, String vob, String branch, int version) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find %s -version \"version(.../%s/LATEST) && ! version(.../%s/%s)\" -print", super.getContext(vob),  branch, branch, version);      
        return super.execQuietly(ctCmd, vob);
    }
    
    /**
     * Find in all VOBs, on a specific branch, elements has the latest version is not the specified version<br>
     * @param view  
     * @param branch    selected branch
     * @param version   ignored version <br>
     * Ex: element A has 3 versions on selected branch: 0, 1, 2
     * <br> if version=2 then this element will not be included in the result
     * <br> if version<>2 then this element will be included in the result
     * @return
     * @throws IOException
     */
    public List<String> findNotInAllVobs(String view, String branch, int version) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find -avobs -version \"version(.../%s/LATEST) && ! version(.../%s/%s)\" -print", branch, branch, version);      
        return super.execQuietly(ctCmd);
    }
    
    /**
     * Find in all VOBs, on a branch, elements has the latest version is the specified version<br>
     * @param view  
     * @param branch    selected branch
     * @param version   the latest version <br>
     * Ex: element A has 3 versions on selected branch: 0, 1, 2
     * <br> if version=2 then this element will be included in the result
     * <br> if version<>2 then this element will not be included in the result
     * @return
     * @throws IOException
     */
    public List<String> findOnlyInAllVobs(String view, String branch, int version) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find -avobs -version \"version(.../%s/LATEST) && version(.../%s/%s)\" -print", branch, branch, version);      
        return super.execQuietly(ctCmd);
    }
    
    /**
     * Find in a VOB, on a branch, elements has the latest version is the specified version<br>
     * @param view  
     * @param branch    selected branch
     * @param version skipped version
     * @return
     * @throws IOException
     */
    public List<String> findOnly(String view, String vob, String branch, int version) throws IOException
    {
        super.setView(view);
        String ctCmd = String.format("find -version \"version(.../%s/LATEST) && version(.../%s/%s)\" -print", branch, branch, version);      
        return super.execQuietly(ctCmd, vob);
    }
    
    public void getTo(String view, String pname, String dest) throws IOException {
        super.setView(view);
        String ctCmd = String.format("get -to %s %s", dest, pname);
        super.execQuietly(ctCmd);
    }
}
