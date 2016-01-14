package com.app.ashmawy.soprasearch.Interfaces;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface DB_Output {

    /**
     * AUTHENTICATION
     */

    void inClientList(String nickname, boolean userOrAdmin);



    /**
     * ROOM BOOKING
     */

    void searchAvailableRooms(int id_site, String desc, String begin, String end, int num_collab, int particul) throws SQLException;
    void searchAndBookRoom(int id_room, int id_site, String desc, String begin, String end, int num_collab, int id_client);



    /**
     * PROFIL MANAGEMENT
     */

    void updateProfile(int id_user, int id_site) throws SQLException;
    String getCurrentSite(int id_site);



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
    void deleteSiteFromDatabase(int id_site);
    void infoSite(int id_site);



    /**
     * ADD/MODIFY SITE
     */

    void addNewSite(String name_site, int nb_info_site, String address);
    void modifySite(int id_site, String name_site, int nb_info_site, String address);



    /**
     * ROOM MANAGEMENT
     */

    void searchRoom(int id_site);
    void deleteRoomFromDatabase(int id_room);
    void infoRoom(int id_room);



    /**
     * ADD/MODIFY ROOM
     */

    void addNewRoom(String name_room, int floor, int capacity, int particularities);
    void modifyRoom(int id_room, String name_room, int floor, int capacity, int particularities);

}