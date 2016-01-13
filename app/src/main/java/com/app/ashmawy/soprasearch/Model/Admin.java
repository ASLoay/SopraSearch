package com.app.ashmawy.soprasearch.Model;

/**
 * Created by Joris on 06/01/16.
 */
public class Admin {

    /**
     * Attributes
     */

    private String nickname;


    /**
     * Constructeur
     */

    public Admin(String nickname) {
        this.nickname = nickname;
    }


    /**
     * Getter et setter
     */

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
