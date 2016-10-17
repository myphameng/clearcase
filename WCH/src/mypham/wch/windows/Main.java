package mypham.wch.windows;

import java.io.IOException;

public class Main {
       
    public static void main(String...strings)
    {
        UserInput u = null;
        try {
            u = new UserInput("wch.properties");
        } catch (IOException e1) {
            LogManager.getLogger().error(e1);
        }
        TrackingCodeChange tracking = new TrackingCodeChange();
        for (String vob : u.getVobs()) {
            for (String brName : u.getBranches())
                try {
                    tracking.trackChange(u.getView(), vob, brName, u.getDest());
                } catch (IOException e) {
                    LogManager.getLogger().error(e);;
                }
        }
    }

}
