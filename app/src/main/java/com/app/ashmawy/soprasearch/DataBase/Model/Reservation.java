package com.app.ashmawy.soprasearch.DataBase.Model;

import java.sql.Date;

/**
 * Created by Joris on 05/01/16.
 */
public class Reservation {

    /**
     * Attributes
     */

    private Room room;
    private Client user;
    private Date dateBegin;
    private Date dateEnd;
    private int nbCollaborators;
    private String description;



    /**
     * Constructeur
     */

    public Reservation(Room room, Client user, Date dateBegin, Date dateEnd, int nbCollaborators, String description) {
        this.room = room;
        this.user = user;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.nbCollaborators = nbCollaborators;
        this.description = description;
    }



    /**
     * Getter et setter
     */

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNbCollaborators() {
        return nbCollaborators;
    }

    public void setNbCollaborators(int nbCollaborators) {
        this.nbCollaborators = nbCollaborators;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
