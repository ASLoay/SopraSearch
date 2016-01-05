package com.app.ashmawy.soprasearch;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface DB_Listener {

     void ProcessAvailableRooms(String Room);

     void ProcessResponseAuthentication(boolean accessGranted);

     void ProcessUpdateProfile();
}
