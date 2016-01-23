package com.app.ashmawy.soprasearch.Interfaces;

import java.sql.SQLException;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public interface DB_Output {

    /**
     * AUTHENTICATION
     */

    void inClientList(String nickname, boolean userOrAdmin) throws SQLException;



    /**
     * ROOM BOOKING
     */

    void searchAvailableRooms(int id_site, String desc, String begin, String end, int num_collab, int particul) throws SQLException;
    void searchAndBookRoom(int id_room, int id_site, String desc, String begin, String end, int num_collab, int id_client) throws SQLException;



    /**
     * PROFIL MANAGEMENT
     */

    void updateProfile(int id_user, int id_site) throws SQLException;
    String getCurrentSite(int id_site) throws SQLException;
    void deleteReservationFromDatabaseProfile(int id_reservation) throws SQLException;



    /**
     * GENERAL INFO
     */

    int getSitesNb() throws SQLException;
    int getRoomsNb() throws SQLException;
    int getReservationsNb() throws SQLException;
    void searchReservations(int id_client) throws SQLException;



    /**
     * SITE MANAGEMENT
     */

    void searchSites() throws SQLException;
    void deleteSiteFromDatabase(String name_site) throws SQLException;
    void infoSite(String name_site) throws SQLException;
    void addNewSite(String name_site, int nb_info_site, String address) throws SQLException;
    void modifySite(String modifySite, String name_site, int nb_info_site, String address) throws SQLException;



    /**
     * ROOM MANAGEMENT
     */

    void searchRoom(int id_site) throws SQLException;
    void deleteRoomFromDatabase(int id_room) throws SQLException;
    void infoRoom(int id_room) throws SQLException;
    void addNewRoom(String name_room, int floor, int capacity, int particularities, int id_site) throws SQLException;
    void modifyRoom(int id_room, String name_room, int floor, int capacity, int particularities) throws SQLException;


    /**
     * RESERVATION MANAGEMENT
     */

    void searchReservations() throws SQLException;
    void deleteReservationFromDatabaseAdmin(int idReservationMngt) throws SQLException;
}