package com.app.ashmawy.soprasearch;

import java.util.List;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface GUI_Output {

    /**
     * AUTHENTICATION
     */

    void showSearchScreen();



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

    void generalInfoPage(int nbSites, int nbRooms, int nbReservations, int reservationRate);



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

    void error(String message);
    void testUserInput();
}