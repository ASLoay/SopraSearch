package com.app.ashmawy.soprasearch;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface DB_Listener {

    public void ProcessAvailableRooms(String Room);

    public void ProcessResponseAuthentication(boolean accessGranted);

    public void ProcessUpdateProfile();
}
