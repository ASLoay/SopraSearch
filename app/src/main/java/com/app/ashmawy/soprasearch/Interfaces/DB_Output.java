package com.app.ashmawy.soprasearch.Interfaces;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lenovo on 04-Dec-15.
 */
public interface DB_Output {

    /**
     * AUTHENTICATION
     */

    void inClientList(String nickname, boolean userOrAdmin);



    /**
     * ROOM BOOKING
     */

    void searchAvailableRooms(String name_site, String desc, Date begin, Date end, int num_collab, int particul) throws SQLException;
    void searchAndBookRoom(String name_room, String name_site, String desc, Date begin, Date end, int num_collab, int particul, String nickname);



    /**
     * PROFIL MANAGEMENT
     */

    void updateProfile(String nickname, String name_site);



    /**
     * GENERAL INFO
     */

    void getSitesNb();
    void getRoomsNb();
    void getReservationsNb();



    /**
     * SITE MANAGEMENT
     */

    void searchSites();
    void deleteSiteFromDatabase(String name_site);
    void infoSite(String name_site);



    /**
     * ADD/MODIFY SITE
     */

    void addNewSite(String name_site, int nb_info_site, String address);
    void modifySite(String name_site, String new_name_site, int nb_info_site, String address);



    /**
     * ROOM MANAGEMENT
     */

    void searchRoom(String name_room);
    void deleteRoomFromDatabase(String name_room);
    void infoRoom(String name_room);



    /**
     * ADD/MODIFY ROOM
     */

    void addNewRoom(String name_room, int floor, int capacity, int particularities);
    void modifyRoom(String name_room, String new_name_room, int floor, int capacity, int particularities);

}