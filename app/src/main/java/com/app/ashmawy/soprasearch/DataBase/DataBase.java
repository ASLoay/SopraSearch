package com.app.ashmawy.soprasearch.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Joris on 06/01/16.
 */
public class DataBase extends DataBaseHandler implements DB_Output {

    /**
     * Attributes
     */

    private DB_Listener DBListener;



    /**
     * Constructor
     */

    public DataBase(Context context) {
        super(context);
    }



    /**
     * Getter, Setter & methods overriden
     */

    public void setDBListener(DB_Listener DBListener) {
        this.DBListener = DBListener;
    }



    /**
     * AUTHENTICATION
     */

    @Override
    public void inClientList(String nickname, boolean userOrAdmin) {

        boolean result = false;

        Cursor c = SopraDB.rawQuery("SELECT " + ID_CLIENT + " FROM " + TABLE_CLIENTS + " WHERE " + NICKNAME + " = ?", new String[]{nickname});

        if(c.getCount() < 1) {
            // UserName Not Exist
            System.out.println("Aucun utilisateur...");
        }
        else {
            result = true;
        }
        c.close();

        DBListener.processResponseAuthentication(result);
    }



    /**
     * ROOM BOOKING
     */

    @Override
    public void searchAvailableRooms(int id_site, String desc, Date begin, Date end, int num_collab, int particul) throws SQLException {
        int[] id;
        String[] room_name;
        int size;
        String query;
        Cursor cursor;

        query = "SELECT id_room,name_room " +
                "FROM ROOMS " +
                "WHERE site = " + id_site + " AND capacity <= " + num_collab + " AND particularities = " + particul +
                " AND id_room NOT IN (" +
                "SELECT room " +
                "FROM RESERVATIONS " +
                "WHERE (date_begin >= " + begin + " AND date_end <= " + end + ") OR " +
                "(date_begin <= " + begin + " AND date_end >= " + end + ");";
        cursor = SopraDB.rawQuery(query, null);
        size = cursor.getCount();
        id = new int[size];
        room_name = new String[size];

        for (int i = 0; i < size; i++) {
            id[i] = cursor.getInt(1);
            room_name[i] = cursor.getString(2);
        }
        cursor.close();
        DBListener.processAvailableRooms(id, room_name);
    }

    @Override
    public void searchAndBookRoom(int id_room) {

    }



    /**
     * PROFIL MANAGEMENT
     */

    @Override
    public void updateProfile(int id_user, int id_site) {
        DBListener.processUpdateProfile();
    }



    /**
     * GENERAL INFO
     */

    @Override
    public void getSitesNb() {

    }

    @Override
    public void getRoomsNb() {

    }

    @Override
    public void getReservationsNb() {

    }



    /**
     * SITE MANAGEMENT
     */

    @Override
    public void searchSites() {

    }

    @Override
    public void deleteSiteFromDatabase(int id_site) {

    }

    @Override
    public void infoSite(int id_site) {

    }



    /**
     * ADD/MODIFY SITE
     */

    @Override
    public void addNewSite(String name_site, int nb_info_site, String address) {

    }

    @Override
    public void modifySite(int id_site, String name_site, int nb_info_site, String address) {

    }



    /**
     * ROOM MANAGEMENT
     */

    @Override
    public void searchRoom(int id_room) {

    }

    @Override
    public void deleteRoomFromDatabase(int id_room) {

    }

    @Override
    public void infoRoom(int id_room) {

    }



    /**
     * ADD/MODIFY ROOM
     */

    @Override
    public void addNewRoom(int num_room, String name_room, int floor, int capacity, int particularities) {

    }

    @Override
    public void modifyRoom(int id_room, int num_room, String name_room, int floor, int capacity, int particularities) {

    }
}
