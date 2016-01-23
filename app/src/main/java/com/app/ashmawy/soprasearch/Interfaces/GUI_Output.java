package com.app.ashmawy.soprasearch.Interfaces;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface GUI_Output {

    /**
     * AUTHENTICATION
     */

    void showSearchScreenAfterConnect();
    void setLocalSiteOfRef(int id_site);

    /**
     * ROOM BOOKING
     */

    void listOfAvailableRooms(ArrayList<String> rooms);
    void roomBooked();



    /**
     * PROFIL MANAGEMENT
     */

    void localisationSaved();



    /**
     * GENERAL INFO
     */

    void showGeneralInfoPage(View view);



    /**
     * SITE MANAGEMENT
     */

    void suppressionSiteSucceed();
    void infoSite(int nb_salles_site, String address_sites);



    /**
     * ADD/MODIFY SITE
     */

    void siteAddedOrModified();



    /**
     * ROOM MANAGEMENT
     */

    void suppressionRoomSucceed();
    void infoRoom(int nb_reservation, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab);



    /**
     * ADD/MODIFY ROOM
     */

    void roomAddedOrModified();



    /**
     * RESERVATION MANAGEMENT
     */

    void suppressionReservationSucceed();



    /**
     * INFO RESERVATION
     */

    void infoReservation(String site, String room, String collab, String description, int nbCollab, String dateBegin, String dateEnd);



    /**
     * USER INTERACTION
     */

    void showAlert(String message,String Title);
}