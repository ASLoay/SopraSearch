package com.app.ashmawy.soprasearch.Model;

/**
 * Created by Joris on 06/01/16.
 */
public class User {

    /**
     * Attributes
     */

    private String nickname;
    private Site site;


    /**
     * Constructeur
     */

    public User(String nickname, Site site) {
        this.nickname = nickname;
        this.site = site;
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

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
