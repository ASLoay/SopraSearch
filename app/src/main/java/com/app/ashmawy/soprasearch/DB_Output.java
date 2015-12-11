package com.app.ashmawy.soprasearch;

import java.util.Date;

/**
 * Created by lenovo on 04-Dec-15.
 */
public interface DB_Output {
    public void InClientList(String nickname, boolean userOrAdmin);

    public void SearchAvailableRooms(int id_site, String desc, Date begin, Date end, int num_collab, int particul);

    public void SearchAndBookRoom(int id_room);

    public void UpdateProfile(int id_user, int id_site);

}
