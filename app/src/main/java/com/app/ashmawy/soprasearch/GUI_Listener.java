package com.app.ashmawy.soprasearch;

import java.sql.Date;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface GUI_Listener {

    /**
     * AUTHENTICATION
     */

    void performAuthentication(String nickname, boolean UserOrAdmin);



    /**
     * ROOM BOOKING
     */

    void performSearchRoom(String desc, Date begin, Date end, int num_collab, boolean visio, boolean phone, boolean secu, boolean digilab);
    void performBookRoom(Room r);



    /**
     * PROFIL MANAGEMENT
     */

    void performSaveLocalisationSite(int id_user, int id_site);



    /**
     * SITE MANAGEMENT
     */

    void performDeleteSite(int id_site);
    void performInfoSite(int id_site);



    /**
     * ADD/MODIFY SITE
     */

    void performNewSite(String name_site, int nb_salles_site, String address_site);
    void performModifySite(int id_site, String name_site, int nb_salles_site, String address_site);



    /**
     * ROOM MANAGEMENT
     */

    void performDeleteRoom(int id_room);
    void performInfoRoom(int id_room);



    /**
     * ADD/MODIFY ROOM
     */

    void performNewRoom(int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab);
    void performModifyRoom(int id_room, int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab);
}
