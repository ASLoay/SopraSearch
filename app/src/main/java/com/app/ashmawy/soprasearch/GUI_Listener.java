package com.app.ashmawy.soprasearch;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface GUI_Listener {
     void start();
     void setDB(DB_Output DB);
     void setGUI(GUI_Output GUI);
     void PerformAuthentication(String nickname, boolean UserOrAdmin);
}
