package com.app.ashmawy.soprasearch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

import static android.database.sqlite.SQLiteDatabase.*;

/**
 * Created by cyriljantingstell on 04/12/15.
 */
public class DataBase implements DB_Output {
    public int oldVersion;
    public int newVersion;
    SQLiteDatabase SopraDB;
    SQLiteDatabase.CursorFactory factory;
    DB_Listener db_listener;

    public DataBase(DB_Listener list){
        this.db_listener=list;
    }

    public void initDB() {
        SopraDB = openOrCreateDatabase("DATABASE", factory, null);
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS CLIENTS(" +
                "id_client INTEGER PRIMARY KEY UNIQUE," +
                "nickname TEXT NOT NULL);"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS USERS(" +
                        "user INTEGER PRIMARY KEY UNIQUE," +
                        "FOREIGN KEY(user) REFERENCES CLIENTS(id_client)," +
                        "site INTEGER" +
                        "FOREIGN KEY(site) REFERENCES SITES(id_site));"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS ADMINS(" +
                        "admin INTEGER UNIQUE," +
                        "FOREIGN KEY(admin) REFERENCES CLIENTS(id_client));"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS SITES(" +
                        "id_site INTEGER PRIMARY KEY UNIQUE," +
                        "name_site TEXT NOT NULL" +
                        "address TEXT NOT NULL" +
                        "nb_rooms INTEGER NOT NULL" +
                        "nb_reservation INTEGER NOT NULL);"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS ROOMS(" +
                        "id_room INTEGER PRIMARY KEY UNIQUE," +
                        "name_room TEXT NOT NULL" +
                        "capacity INTEGER NOT NULL" +
                        "floor INTEGER NOT NULL" +
                        "particularities INTEGER NOT NULL" +
                        "nb_reservation INTEGER NOT NULL" +
                        "site INTEGER NOT NULL" +
                        "FOREIGN KEY(site) REFERENCES SITES(id_site));"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS RESERVATIONS(" +
                        "id_reservation INTEGER PRIMARY KEY UNIQUE," +
                        "date_begin DATETIME NOT NULL" +
                        "date_end DATETIME NOT NULL" +
                        "nb_collaborateurs INTEGER NOT NULL" +
                        "description TEXT NOT NULL" +
                        "user INTEGER NOT NULL" +
                        "room INTEGER PRIMARY KEY NOT NULL" +
                        "FOREIGN KEY(user) REFERENCES USERS(user)" +
                        "FOREIGN KEY(room) REFERENCES ROOMS(id_room));"
        );
        SopraDB.execSQL(
                "INSERT INTO CLIENTS (nickname) " +
                        "VALUES ('toto');"
        );
        SopraDB.execSQL(
                "INSERT INTO CLIENTS (nickname) " +
                        "VALUES ('titi');"
        );
        SopraDB.execSQL(
                "INSERT INTO SITES (name_site, address, nb_rooms, nb_reservations) " +
                        "VALUES ('insa', 'insa toulouse', '33', '5');"
        );
        SopraDB.execSQL(
                "INSERT INTO SITES (name_site, address, nb_rooms, nb_reservations) " +
                        "VALUES ('rangueil', 'paris', '60', '4');"
        );
        SopraDB.execSQL(
                "INSERT INTO ROOMS (name_room, capacity, floor, particularities, nb_resevation, site) " +
                        "VALUES ('107', '100', '3', '2', '21', '5');"
        );
        SopraDB.execSQL(
                "INSERT INTO ROOMS (name_room, capacity, floor, particularities, nb_resevation, site) " +
                        "VALUES ('203', '10', '1', '2', '21', '1');"
        );



    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void setDbListener(DB_Listener dbListener) {
        this.db_listener = db_listener;
    }








    /**
     * AUTHENTICATION
     */

    @Override
    public void inClientList(String nickname, boolean userOrAdmin) {
        boolean result = false;
        int id;
        String query;
        Cursor cursor;

        query = "SELECT id_client " +
                "FROM CLIENTS " +
                "WHERE nickname = " + nickname + ";";
        cursor = SopraDB.rawQuery(query, null);
        id = cursor.getInt(1);
        cursor.close();
        if (userOrAdmin) {
            query = "SELECT user " +
                    "FROM USERS " +
                    "WHERE user = " + id + ";";
            cursor = SopraDB.rawQuery(query, null);
            if (!cursor.isNull(1)) result = true;
            cursor.close();
        }
        else {
            query = "SELECT admin " +
                    "FROM ADMINS " +
                    "WHERE admin = " + id + ";";
            cursor = SopraDB.rawQuery(query, null);
            if (!cursor.isNull(1)) result = true;
            cursor.close();
        }
        db_listener.processResponseAuthentication(result);
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
        db_listener.processAvailableRooms(id, room_name);
    }

    @Override
    public void searchAndBookRoom(int id_room) {

    }



    /**
     * PROFIL MANAGEMENT
     */

    @Override
    public void updateProfile(int id_user, int id_site) {
        db_listener.processUpdateProfile();
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
