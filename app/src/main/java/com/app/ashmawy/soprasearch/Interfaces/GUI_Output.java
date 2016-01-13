package com.app.ashmawy.soprasearch.Interfaces;

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

    /**
     * ROOM BOOKING
     */

    void listOfAvailableRooms(List rooms);
    void roomBooked();



    /**
     * PROFIL MANAGEMENT
     */

    void localisationSaved();



    /**
     * GENERAL INFO
     */

    void showGeneralInfoPageAfterCalcul(int nbSites, int nbRooms, int nbReservations, int reservationRate);



    /**
     * SITE MANAGEMENT
     */

    void suppressionSiteSucceed();
    void infoSite(String name_site,  int nb_salles_site, String address_sites);



    /**
     * ADD/MODIFY SITE
     */

    void siteAddedOrModified();



    /**
     * ROOM MANAGEMENT
     */

    void suppressionRoomSucceed();
    void infoRoom(int num_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab);



    /**
     * ADD/MODIFY ROOM
     */

    void roomAddedOrModified();



    /**
     * USER INTERACTION
     */

    void showAlert(String message);
    void testUserInput();
}