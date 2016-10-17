package mypham.wch.windows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserInput {
    String view;
    String[] vobs;
    String[] branches;
    File dest;
    
    public UserInput(String propertiesFile) throws IOException {
        Properties p = new Properties();
        InputStream in = new FileInputStream(propertiesFile);
        p.load(in);
        view = p.getProperty("view");
        String s = p.getProperty("vobs");
        vobs = s.split(" ");
        s = p.getProperty("branches");
        branches = s.split(" ");
        s = p.getProperty("destination");
        dest = new File(s);
    }

    public String getView() {
        return view;
    }

    public String[] getVobs() {
        return vobs;
    }

    public String[] getBranches() {
        return branches;
    }

    public File getDest() {
        return dest;
    }
    
}
