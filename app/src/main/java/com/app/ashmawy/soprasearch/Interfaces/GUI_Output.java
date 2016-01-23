package com.app.ashmawy.soprasearch.Interfaces;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface GUI_Output {

    void setLocalSiteOfRef(int id_site);

    /**
     * AUTHENTICATION
     */

    void showSearchScreenAfterConnect();

    /**
     * ROOM BOOKING
     */

    void listOfAvailableRooms(ArrayList<String> rooms);
    void roomBooked();



    /**
     * PROFIL MANAGEMENT
     */

    void localisationSaved();
    void suppressionReservationSucceedProfile();



    /**
     * GENERAL INFO
     */

    void showGeneralInfoPage(View view);



    /**
     * SITE MANAGEMENT
     */

    void suppressionSiteSucceed();
    void infoSite(int nb_salles_site, String address_sites);
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

    void suppressionReservationSucceedAdmin();



    /**
     * USER INTERACTION
     */

    void showAlert(String message,String Title);
}