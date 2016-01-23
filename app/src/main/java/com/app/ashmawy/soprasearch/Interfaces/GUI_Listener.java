package com.app.ashmawy.soprasearch.Interfaces;

import com.app.ashmawy.soprasearch.Model.Reservation;
import com.app.ashmawy.soprasearch.Model.Room;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface GUI_Listener {

    /**
     * AUTHENTICATION
     */

    void performAuthentication(String nickname, boolean UserOrAdmin);
    void performSearchListOfSites();



    /**
     * GENERAL INFO
     */

    ArrayList<Integer> performGeneralInfo();



    /**
     * ROOM BOOKING
     */

    void performSearchRoom(String desc, Date begin, int hourstart, int minutestart, Date end,int hourend, int minuteend, int num_collab, boolean visio, boolean phone, boolean secu, boolean digilab);
    void performBookRoom(String Room,String desc, Date begin, int hourstart, int minutestart, Date end,int hourend, int minuteend, int num_collab);



    /**
     * PROFIL MANAGEMENT
     */

    void performSaveLocalisationSite(int id_site);
    void performDeleteReservationProfile(String nameReservation);



    /**
     * SITE MANAGEMENT
     */

    void performDeleteSite(String name_site);
    void performInfoSite(String name_site);
    void performNewSite(String name_site, int nb_salles_site, String address_site) throws SQLException;
    void performModifySite(String nameSiteMngt, String name_site, int nb_salles_site, String address_site);



    /**
     * ROOM MANAGEMENT
     */

    void performDeleteRoom(int id_room);
    void performInfoRoom(int id_room);
    void performNewRoom(int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab);
    void performModifyRoom(int id_room, int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab);



    /**
     * RESERVATION MANAGEMENT
     */

    void performSearchListOfReservations();
    void performDeleteReservationAdmin(int idReservationMngt);
    ArrayList<Reservation> getReservationList();
}
