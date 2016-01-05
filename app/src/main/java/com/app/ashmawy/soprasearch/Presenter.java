package com.app.ashmawy.soprasearch;

/**
 * Created by lenovo on 04-Dec-15.
 */
public class Presenter implements GUI_Listener,DB_Listener {
    DB_Output DB;
    GUI_Output GUI;

    public void setDB(DB_Output DB){
        this.DB=DB;
    }
    public void setGUI(GUI_Output GUI){
        this.GUI=GUI;
    }
    @Override
    public void ProcessAvailableRooms(String Room) {

    }

    @Override
    public void ProcessResponseAuthentication(boolean accessGranted) {
        if (accessGranted){
            GUI.ShowSearchScreen();
        }
    }

    @Override
    public void ProcessUpdateProfile() {
        GUI.LocalisationSaved();
    }

    @Override
    public void PerformAuthentication(String nickname, boolean UserOrAdmin) {
        DB.InClientList(nickname, UserOrAdmin);
    }

    public void start(){
        //DB.initDB();
    }
}
