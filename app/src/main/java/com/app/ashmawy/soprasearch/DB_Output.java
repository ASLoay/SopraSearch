package com.app.ashmawy.soprasearch;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lenovo on 04-Dec-15.
 */
public interface DB_Output {

     void initDB();

     void InClientList(String nickname, boolean userOrAdmin);

     void SearchAvailableRooms(int id_site, String desc, Date begin, Date end, int num_collab, int particul) throws SQLException;

     void SearchAndBookRoom(int id_room);

     void UpdateProfile(int id_user, int id_site);

}
