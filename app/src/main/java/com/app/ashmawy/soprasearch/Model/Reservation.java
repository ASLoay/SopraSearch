package com.app.ashmawy.soprasearch.Model;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public class Reservation {

    /**
     * Attributes
     */

    private int id_reservation;
    private String site;
    private String room;
    private String user;
    private String description;
    private int nbCollaborators;
    private String dateBegin;
    private String dateEnd;



    /**
     * Constructeur
     */

    public Reservation(int id_reservation, String site, String room, String user, String description, int nbCollaborators, String dateBegin, String dateEnd) {
        this.id_reservation = id_reservation;
        this.site = site;
        this.room = room;
        this.user = user;
        this.description = description;
        this.nbCollaborators = nbCollaborators;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    /**
     * Getter et setter
     */

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbCollaborators() {
        return nbCollaborators;
    }

    public void setNbCollaborators(int nbCollaborators) {
        this.nbCollaborators = nbCollaborators;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", site='" + site + '\'' +
                ", room='" + room + '\'' +
                ", user='" + user + '\'' +
                ", description='" + description + '\'' +
                ", nbCollaborators=" + nbCollaborators +
                ", dateBegin='" + dateBegin + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                '}';
    }
}
