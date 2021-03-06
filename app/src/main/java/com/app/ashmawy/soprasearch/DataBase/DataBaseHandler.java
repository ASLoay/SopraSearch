package com.app.ashmawy.soprasearch.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    /**
     * Attributes
     */

    protected final static int VERSION = 2; // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static String NAME_DB = "SopraSearch_RT11";
    protected SQLiteDatabase SopraDB;

    /** SITE */
    public static final String TABLE_SITES = "sites";
    public static final String ID_SITE = "_id";
    public static final String NAME_SITE = "name_site";
    public static final String ADDRESS = "address";
    public static final String NB_ROOMS = "nb_rooms";
    public static final String NB_RESERVATION_SITE = "nb_reservation";
    public static final String SITES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SITES + "(" +
                    ID_SITE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_SITE + " TEXT NOT NULL, " +
                    ADDRESS + " TEXT NOT NULL, " +
                    NB_ROOMS + " INTEGER NOT NULL, " +
                    NB_RESERVATION_SITE + " INTEGER NOT NULL);";
    public static final String SITES_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_SITES + ";";

    /** ROOM */
    public static final String TABLE_ROOMS = "rooms";
    public static final String ID_ROOM = "_id";
    public static final String NAME_ROOM = "name_room";
    public static final String CAPACITY = "capacity";
    public static final String FLOOR = "floor";
    public static final String PARTICULARITIES = "particularities";
    public static final String NB_RESERVATION_ROOM = "nb_reservation";
    public static final String SITE_OF_ROOM = "site";
    public static final String ROOMS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+ TABLE_ROOMS + "(" +
                    ID_ROOM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_ROOM + " TEXT NOT NULL, " +
                    CAPACITY + " INTEGER NOT NULL, " +
                    FLOOR + " INTEGER NOT NULL, " +
                    PARTICULARITIES + " INTEGER NOT NULL, " +
                    NB_RESERVATION_ROOM + " INTEGER NOT NULL, " +
                    SITE_OF_ROOM + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + SITE_OF_ROOM + ") REFERENCES " + TABLE_SITES + "(" + ID_SITE + "));";
    public static final String ROOMS_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_ROOMS + ";";

    /** USER */
    public static final String TABLE_USERS = "users";
    public static final String ID_USER = "_id";
    public static final String NICKNAME_USER = "nickname";
    public static final String SITE_REF = "site";
    public static final String USERS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                    ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NICKNAME_USER + " TEXT NOT NULL UNIQUE," +
                    SITE_REF + " INTEGER," +
                    " FOREIGN KEY (" + SITE_REF + ") REFERENCES " + TABLE_SITES + "(" + ID_SITE + "));";
    public static final String USERS_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_USERS + ";";

    /** ADMIN */
    public static final String TABLE_ADMINS = "admins";
    public static final String ID_ADMIN = "_id";
    public static final String NICKNAME_ADMIN = "nickname";
    public static final String ADMINS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADMINS + "(" +
                    ID_ADMIN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NICKNAME_ADMIN + " TEXT NOT NULL);";
    public static final String ADMINS_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_ADMINS + ";";

    /** RESERVATION */
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String ID_RESERVATION = "_id";
    public static final String DATE_BEGIN = "date_begin";
    public static final String DATE_END = "date_end";
    public static final String NB_COLLABORATORS = "nb_collaborateurs";
    public static final String DESCRIPTION = "description";
    public static final String USER_RES = "user";
    public static final String ROOM_RES = "room";
    public static final String RESERVATIONS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATIONS + "(" +
                    ID_RESERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE_BEGIN + " DATETIME NOT NULL," +
                    DATE_END + " DATETIME NOT NULL," +
                    NB_COLLABORATORS + " INTEGER NOT NULL," +
                    DESCRIPTION + " TEXT NOT NULL," +
                    USER_RES + " INTEGER NOT NULL," +
                    ROOM_RES + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + USER_RES + ") REFERENCES "+ TABLE_USERS +"(" + ID_USER + ")," +
                    "FOREIGN KEY(" + ROOM_RES + ") REFERENCES "+ TABLE_ROOMS +"(" + ID_ROOM + "));";
    public static final String RESERVATIONS_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_RESERVATIONS + ";";



    /**
     * Constructor
     */

    public DataBaseHandler(Context context) {
        super(context, NAME_DB, null, VERSION);
    }



    /**
     * Getter, Setter & methods overriden
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        SopraDB = db;
        SopraDB.execSQL(USERS_TABLE_CREATE);
        SopraDB.execSQL(ADMINS_TABLE_CREATE);
        SopraDB.execSQL(SITES_TABLE_CREATE);
        SopraDB.execSQL(ROOMS_TABLE_CREATE);
        SopraDB.execSQL(RESERVATIONS_TABLE_CREATE);

        // Create values in tables
        this.addValuesOnTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SopraDB = db;
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        SopraDB.execSQL(USERS_TABLE_DROP);
        SopraDB.execSQL(ADMINS_TABLE_DROP);
        SopraDB.execSQL(SITES_TABLE_DROP);
        SopraDB.execSQL(ROOMS_TABLE_DROP);
        SopraDB.execSQL(RESERVATIONS_TABLE_DROP);
        onCreate(SopraDB);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SopraDB = db;
        Log.w(DataBase.class.getName(),
                "Downgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        SopraDB.execSQL(USERS_TABLE_DROP);
        SopraDB.execSQL(ADMINS_TABLE_DROP);
        SopraDB.execSQL(SITES_TABLE_DROP);
        SopraDB.execSQL(ROOMS_TABLE_DROP);
        SopraDB.execSQL(RESERVATIONS_TABLE_DROP);
        onCreate(SopraDB);
    }

    public void open() {
        SopraDB = this.getWritableDatabase();
    }

    public void close() {
        SopraDB.close();
    }

    public void addValuesOnTables() {

        // On ajoute des USERS
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_USERS + "(" + NICKNAME_USER + "," + SITE_REF + ") VALUES('loay', '1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_USERS + "(" + NICKNAME_USER + "," + SITE_REF + ") VALUES('cyril', '2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_USERS + "(" + NICKNAME_USER + "," + SITE_REF + ") VALUES('cedric', '3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_USERS + "(" + NICKNAME_USER + "," + SITE_REF + ") VALUES('joris', '4');");

        // On ajoute des ADMINS
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ADMINS + " (" + NICKNAME_ADMIN + ") VALUES('exposito');");

        // TEST : on ajoute quelques sites
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES('SopraTL','13 avenue new','33','0');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES('SopraPA','14 avenue bie','44','0');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES('SopraLY','15 avenue york','11','0');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES('SopraMR','16 avenue skywalker','93','0');");

        // TEST : on ajoute quelques reservations
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-01-23 11:45:00', '2016-01-23 12:45:00','10','Meet Steve Jobs','1','10');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-02-24 12:00:00', '2016-02-24 13:00:00','20','Take a cofee','1','19');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-03-25 13:15:00', '2016-03-25 14:15:00','30','Have a meeting','2','21');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-04-26 14:30:00', '2016-04-26 15:30:00','40','Play video games','3','45');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-05-27 15:45:00', '2016-05-27 16:45:00','50','Share a meal','4','70');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + "," + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES('2016-06-28 16:00:00', '2016-06-28 17:00:00','60','Get some rest','2','24');");

        // TEST : on ajoute quelques rooms
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_001', '10','0','0','0','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_002', '20','0','1','1','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_003', '30','0','2','2','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_004', '40','0','3','3','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_101', '50','1','4','4','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_102', '60','1','5','5','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_103', '70','1','6','6','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_104', '80','1','7','7','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_201', '90','2','8','8','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_202', '10','2','9','9','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_203', '20','2','10','10','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_204', '30','2','11','11','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_301', '40','3','12','12','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_302', '50','3','13','13','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_303', '60','3','14','14','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_304', '70','3','15','15','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_401', '80','4','10','16','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomTL_402', '90','4','5','17','1');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('amphiTL', '200','4','15','20','1');");

        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_001', '10','0','0','0','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_002', '20','0','1','1','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_003', '30','0','2','2','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_004', '40','0','3','3','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_101', '50','1','4','4','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_102', '60','1','5','5','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_103', '70','1','6','6','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_104', '80','1','7','7','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_201', '90','2','8','8','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_202', '10','2','9','9','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_203', '20','2','10','10','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_204', '30','2','11','11','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_301', '40','3','12','12','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_302', '50','3','13','13','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_303', '60','3','14','14','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_304', '70','3','15','15','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_401', '80','4','10','16','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomPA_402', '90','4','5','17','2');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('amphiPA', '200','4','15','20','2');");

        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_001', '10','0','0','0','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_002', '20','0','1','1','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_003', '30','0','2','2','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_004', '40','0','3','3','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_101', '50','1','4','4','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_102', '60','1','5','5','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_103', '70','1','6','6','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_104', '80','1','7','7','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_201', '90','2','8','8','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_202', '10','2','9','9','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_203', '20','2','10','10','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_204', '30','2','11','11','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_301', '40','3','12','12','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_302', '50','3','13','13','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_303', '60','3','14','14','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_304', '70','3','15','15','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_401', '80','4','10','16','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomLY_402', '90','4','5','17','3');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('amphiLY', '200','4','15','20','3');");

        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_001', '10','0','0','0','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_002', '20','0','1','1','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_003', '30','0','2','2','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_004', '40','0','3','3','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_101', '50','1','4','4','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_102', '60','1','5','5','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_103', '70','1','6','6','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_104', '80','1','7','7','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_201', '90','2','8','8','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_202', '10','2','9','9','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_203', '20','2','10','10','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_204', '30','2','11','11','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_301', '40','3','12','12','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_302', '50','3','13','13','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_303', '60','3','14','14','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_304', '70','3','15','15','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_401', '80','4','10','16','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('roomMR_402', '90','4','5','17','4');");
        SopraDB.execSQL("INSERT or REPLACE INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + ") VALUES('amphi', '200','4','15','20','4');");
    }
}