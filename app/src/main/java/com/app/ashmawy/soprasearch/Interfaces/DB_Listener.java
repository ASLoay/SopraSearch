package com.app.ashmawy.soprasearch.Interfaces;

import java.util.List;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface DB_Listener {

    /**
     * AUTHENTICATION
     */

    void processResponseAuthentication (boolean accessGranted);



    /**
     * ROOM BOOKING
     */

    void processAvailableRooms(int[] id, String[] rooms);
    void processRoomNotAvailable();
    void processRoomBooked();
    void processRoomNotBooked();



    /**
     * PROFIL MANAGEMENT
     */

    void processUpdateProfile();



    /**
     * GENERAL INFO
     */

    void processSitesNb(int nbSites);
    void processRoomsNb(int nbRooms);
    void processReservationsNb(int nbReservations);
    void calcReservationRate();



    /**
     * SITE MANAGEMENT
     */

    void processListOfSites(List sites);
    void processSiteDeleted();
    void processSiteNotDeleted();
    void processInfoSite(int num_site, String name_site, int nb_salles_site, String address_site);



    /**
     * ADD/MODIFY SITE
     */

    void processSiteAddedOrModified();
    void processSiteNotAddedOrModified();



    /**
     * ROOM MANAGEMENT
     */

    void processListOfRoom(List rooms);
    void processRoomDeleted();
    void processRoomNotDeleted();
    void processInfoRoom(int num_room, String name_room, int floor, int capacity, int particularities);



    /**
     * ADD/MODIFY ROOM
     */

    void processRoomAddedOrModified();
    void processRoomNotAddedOrModified();
}
