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

        String query = "SELECT " + ID_ROOM + " ," + NAME_ROOM + " FROM " + TABLE_ROOMS + " WHERE " + SITE_ID + " = " + id_site +
                " AND " + CAPACITY + " <= " + num_collab + " AND " + PARTICULARITIES + " = " + particul + " AND " + ID_ROOM + " NOT IN (" +
                "SELECT " + ROOM + " FROM " + TABLE_RESERVATIONS + " WHERE (" + DATE_BEGIN + " >= " + begin + " AND " + DATE_END + " <= " + end +
                ") OR (" + DATE_BEGIN + " <= " + begin + " AND " + DATE_END + " >= " + end + ");";
        Cursor c = SopraDB.rawQuery(query, null);
        size = c.getCount();
        id = new int[size];
        room_name = new String[size];

        for (int i = 0; i < size; i++) {
            id[i] = c.getInt(1);
            room_name[i] = c.getString(2);
        }
        c.close();
        DBListener.processAvailableRooms(id, room_name);
    }

    @Override
    public void searchAndBookRoom(int id_room, String desc, Date begin, Date end, int num_collab, int particul, int id_client) {
        String query = "INSERT INTO " + TABLE_RESERVATIONS + "(" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + ","  + DESCRIPTION + "," +
        USER + "," + ROOM + ") VALUES (" + begin + "," + end + "," + num_collab + "," + desc + "," + id_client + "," + id_room + ");";
        SopraDB.execSQL(query);
        DBListener.processRoomBooked();

    }



    /**
     * PROFIL MANAGEMENT
     */

    @Override
    public void updateProfile(int id_user, int id_site) {
        String query = "UPDATE "+ TABLE_USERS + " SET " + SITE_ID + " = " + id_site + "," + "WHERE " + USER_ID + " = " + id_user + ";";
        SopraDB.execSQL(query);
        DBListener.processUpdateProfile();
    }


    /**
     * GENERAL INFO
     */

    @Override
    public void getSitesNb() {
        String query = "SELECT " + ID_SITE + " FROM " + TABLE_SITES + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        int size = c.getCount();
        c.close();
        DBListener.processSitesNb(size);
    }

    @Override
    public void getRoomsNb() {
        String query = "SELECT " + ID_ROOM + " FROM " + TABLE_ROOMS + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        int size = c.getCount();
        c.close();
        DBListener.processRoomsNb(size);
    }

    @Override
    public void getReservationsNb() {
        String query = "SELECT " + ID_RESERVATION + " FROM " + TABLE_RESERVATIONS + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        int size = c.getCount();
        c.close();
        DBListener.processReservationsNb(size);
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
