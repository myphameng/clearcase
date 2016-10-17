package mypham.wch.windows;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 
 * @author Mr.USA
 *
 */
public class LogManager
{
	private static final String LOG4J_CONFIG_FILE = "log_config.xml";
    
    static
    {
        DOMConfigurator.configure( LOG4J_CONFIG_FILE );
    }
    
    public static Logger getLogger()
    {
    	return Logger.getLogger( "wch" );
    }
    
    public static Logger getCTLogger()
    {
        return Logger.getLogger("cleartool");
    }
}
