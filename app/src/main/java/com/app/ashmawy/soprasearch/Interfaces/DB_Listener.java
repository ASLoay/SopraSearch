package com.app.ashmawy.soprasearch.Interfaces;

import com.app.ashmawy.soprasearch.Model.Room;
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
    void processIdClient(int id_client);
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
     * SITE MANAGEMENT
     */

    void processListOfSites(ArrayList<Site> sites);
    void processSiteDeleted();
    void processInfoSite(int nb_salles_site, String address_site);



    /**
     * ADD/MODIFY SITE
     */

    void processSiteAddedOrModified();
    void processSiteNotAddedOrModified();



    /**
     * ROOM MANAGEMENT
     */
    void processListOfRoom(ArrayList<Room> rooms);
    void processRoomDeleted();
    void processInfoRoom(String name_room, int capacity, int floor,  int particularities, int nb_reservations, String name_site);



    /**
     * ADD/MODIFY ROOM
     */

    void processRoomAddedOrModified();
    void processRoomNotAddedOrModified();
}
