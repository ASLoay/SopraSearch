package com.app.ashmawy.soprasearch.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.app.ashmawy.soprasearch.DataBase.Model.Room;
import com.app.ashmawy.soprasearch.DataBase.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    /*************************
     * METHODS FROM INTERFACE
     *************************/



    /**
     * AUTHENTICATION
     */

    @Override
    public void inClientList(String nickname, boolean userOrAdmin) {

        boolean result = false;
        Cursor c;

        if (userOrAdmin) {
            c = SopraDB.rawQuery("SELECT " + ID_USER + " FROM " + TABLE_USERS + " WHERE " + NICKNAME_USER + " = ?", new String[]{nickname});
        }
        else {
            c = SopraDB.rawQuery("SELECT " + ID_ADMIN + " FROM " + TABLE_ADMINS + " WHERE " + NICKNAME_ADMIN + " = ?", new String[]{nickname});
        }

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

        String query = "SELECT " + ID_ROOM + " ," + NAME_ROOM + " FROM " + TABLE_ROOMS + " WHERE " + ID_SITE + " = " + id_site +
                " AND " + CAPACITY + " <= " + num_collab + " AND " + PARTICULARITIES + " = " + particul + " AND " + ID_ROOM + " NOT IN (" +
                "SELECT " + ROOM_RES + " FROM " + TABLE_RESERVATIONS + " WHERE (" + DATE_BEGIN + " >= " + begin + " AND " + DATE_END + " <= " + end +
                ") OR (" + DATE_BEGIN + " <= " + begin + " AND " + DATE_END + " >= " + end + ");";
        Cursor c = SopraDB.rawQuery(query, null);
        size = c.getCount();
        id = new int[size];
        room_name = new String[size];

        for (int i = 0; i < size; i++) {
            id[i] = c.getInt(0);
            room_name[i] = c.getString(1);
        }
        c.close();
        DBListener.processAvailableRooms(id, room_name);
    }

    @Override
    public void searchAndBookRoom(int id_room, int id_site, String desc, Date begin, Date end, int num_collab, int particul, int id_client) {
        String query = "INSERT INTO " + TABLE_RESERVATIONS + "(" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + ","  + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES (" + begin + "," + end + "," + num_collab + "," + desc + "," + id_client + "," + id_room + ");";
        SopraDB.execSQL(query);
        query = "SELECT " + NB_RESERVATION_SITE + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        int nb_reservation = c.getInt(0);
        nb_reservation++;
        query = "UPDATE "+ TABLE_SITES + " SET " + NB_RESERVATION_SITE + " = " + nb_reservation + "," + "WHERE " + ID_SITE + " = " + id_site + ";";
        SopraDB.execSQL(query);
        DBListener.processRoomBooked();
    }



    /**
     * PROFIL MANAGEMENT
     */


    @Override
    public void updateProfile(int id_user, int id_site) {
        String query = "UPDATE "+ TABLE_USERS + " SET " + SITE_REF + " = " + id_site + "," + "WHERE " + ID_USER + " = " + id_user + ";";
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
        ArrayList<Site> sites = new ArrayList<Site>();
        String query = "SELECT * FROM " + TABLE_SITES + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        while(c.moveToNext()){
            Site site = new Site(c.getInt(0),c.getString(1),c.getInt(2),c.getString(3),c.getInt(4));
            sites.add(site);
        }
        c.close();
        DBListener.processListOfSites(sites);

    }

    @Override
    public void deleteSiteFromDatabase(int id_site) {
        String query = "DELETE FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        SopraDB.execSQL(query);
        DBListener.processSiteDeleted();
    }

    @Override
    public void infoSite(int id_site) {
        String name_site;
        int nb_rooms;
        String address;
        String query = "SELECT " + NAME_SITE + "," + NB_ROOMS + "," + ADDRESS + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        name_site = c.getString(0);
        nb_rooms = c.getInt(1);
        address = c.getString(2);
        c.close();
        DBListener.processInfoSite(id_site, name_site, nb_rooms, address);
    }



    /**
     * ADD/MODIFY SITE
     */

    @Override
    public void addNewSite(String name_site, int nb_rooms, String address) {
        String query = "INSERT INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES(" + name_site + "," + address + "," + nb_rooms + ",'0');";
        SopraDB.execSQL(query);
        DBListener.processSiteAddedOrModified();
    }

    @Override
    public void modifySite(int id_site, String name_site, int nb_rooms, String address) {
        String query = "UPDATE " + TABLE_SITES + " SET " + NAME_SITE + " = " + name_site + "," + ADDRESS + " = " + address + "," + NB_ROOMS + " = " + nb_rooms + " WHERE " + ID_SITE + " = " + id_site + ";";
        SopraDB.execSQL(query);
        DBListener.processSiteAddedOrModified();
    }



    /**
     * ROOM MANAGEMENT
     */

    @Override
    public void searchRoom(int id_site) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        boolean visio = false;
        boolean phone = false;
        boolean secu = false;
        boolean digilab = false;
        String query = "SELECT * FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        Site site = new Site(c.getInt(1),c.getString(2),c.getInt(3),c.getString(4),c.getInt(5));
        query = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + SITE_OF_ROOM + " = " + id_site + ";";
        c = SopraDB.rawQuery(query, null);
        int size = c.getCount();
        while(c.moveToNext()){
            int p = c.getInt(4);
            switch(p) {
                case (0):
                    visio = false;
                    phone = false;
                    secu = false;
                    digilab = false;
                    break;
                case (1):
                    visio = false;
                    phone = false;
                    secu = false;
                    digilab = true;
                    break;
                case (2):
                    visio = false;
                    phone = false;
                    secu = true;
                    digilab = false;
                    break;
                case (3):
                    visio = false;
                    phone = false;
                    secu = true;
                    digilab = true;
                    break;
                case (4):
                    visio = false;
                    phone = true;
                    secu = false;
                    digilab = false;
                    break;
                case (5):
                    visio = false;
                    phone = true;
                    secu = false;
                    digilab = true;
                    break;
                case (6):
                    visio = false;
                    phone = true;
                    secu = true;
                    digilab = false;
                    break;
                case (7):
                    visio = false;
                    phone = true;
                    secu = true;
                    digilab = true;
                    break;
                case (8):
                    visio = true;
                    phone = false;
                    secu = false;
                    digilab = false;
                    break;
                case (9):
                    visio = true;
                    phone = false;
                    secu = false;
                    digilab = true;
                    break;
                case (10):
                    visio = true;
                    phone = false;
                    secu = true;
                    digilab = false;
                    break;
                case (11):
                    visio = true;
                    phone = false;
                    secu = true;
                    digilab = true;
                    break;
                case (12):
                    visio = true;
                    phone = true;
                    secu = false;
                    digilab = false;
                    break;
                case (13):
                    visio = true;
                    phone = true;
                    secu = false;
                    digilab = true;
                    break;
                case (14):
                    visio = true;
                    phone = true;
                    secu = true;
                    digilab = false;
                    break;
                case (15):
                    visio = true;
                    phone = true;
                    secu = true;
                    digilab = true;
                    break;
            }
            Room room = new Room(site,c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),visio,phone,secu,digilab,c.getInt(5));
            rooms.add(room);
        }
        c.close();
        DBListener.processListOfRoom(rooms);
    }

    @Override
    public void deleteRoomFromDatabase(int id_room) {
        String query = "DELETE FROM " + TABLE_ROOMS + " WHERE " + ID_ROOM + " = " + id_room + ";";
        SopraDB.execSQL(query);
        DBListener.processRoomDeleted();
    }

    @Override
    public void infoRoom(int id_room) {
        String name_site;
        int nb_rooms;
        String address;
//        String query = "SELECT " + NAME_SITE + "," + NB_ROOMS + "," + ADDRESS + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
//        Cursor c = SopraDB.rawQuery(query, null);
//        name_site = c.getString(0);
//        nb_rooms = c.getInt(1);
//        address = c.getString(2);
//        c.close();
//        DBListener.processInfoRoom(id_site, name_site, nb_rooms, address);
    }



    /**
     * ADD/MODIFY ROOM
     */

    @Override
    public void addNewRoom(String name_room, int floor, int capacity, int particularities) {
        String query = "INSERT INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + FLOOR + "," + CAPACITY + "," + PARTICULARITIES + ") VALUES(" + name_room + "," + floor + "," + particularities + ",'0');";
        SopraDB.execSQL(query);
        DBListener.processSiteAddedOrModified();
    }

    @Override
    public void modifyRoom(int id_room, String name_room, int floor, int capacity, int particularities) {
        String query = "UPDATE " + TABLE_ROOMS + " SET " + NAME_ROOM + " = " + name_room + "," + FLOOR + " = " + floor + "," + CAPACITY + " = " + capacity + "," + PARTICULARITIES + " = " + particularities + " WHERE " + ID_ROOM + " = " + id_room + ";";
        SopraDB.execSQL(query);
        DBListener.processRoomAddedOrModified();
    }
}
