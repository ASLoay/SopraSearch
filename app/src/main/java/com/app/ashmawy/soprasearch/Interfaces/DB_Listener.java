package com.app.ashmawy.soprasearch.Interfaces;

import com.app.ashmawy.soprasearch.Model.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface DB_Listener {

    /**
     * AUTHENTICATION
     */

    void processIdSite(int id_site);
    void processResponseAuthentication (boolean accessGranted);



    /**
     * ROOM BOOKING
     * @param id
     * @param rooms
     */

    void processAvailableRooms(ArrayList<Integer> id, ArrayList<String> rooms);
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
