package com.app.ashmawy.soprasearch.Interfaces;

import com.app.ashmawy.soprasearch.DataBase.Model.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public interface DB_Listener {

    /**
     * AUTHENTICATION
     */

    void processResponseAuthentication (boolean accessGranted, int id_site);



    /**
     * ROOM BOOKING
     */

    void processAvailableRooms(int id[], String[] rooms);
    void processRoomNotAvailable();
    void processRoomBooked();
    void processRoomNotBooked();



    /**
     * PROFIL MANAGEMENT
     */
    // pour changer le site de reference, utilisé quand updateProfile dans DB_Output est appelé
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

    void processListOfSites(ArrayList<Site> sites);
    void processSiteDeleted();
    void processSiteNotDeleted();
    void processInfoSite(String name_site, int nb_salles_site, String address_site);



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
    void processInfoRoom(String name_room, int capacity, int floor,  int particularities, int nb_reservations, String name_site);



    /**
     * ADD/MODIFY ROOM
     */

    void processRoomAddedOrModified();
    void processRoomNotAddedOrModified();
}
