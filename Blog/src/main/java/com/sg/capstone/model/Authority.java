/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.model;

/**
 *
 * @author chandler
 */
public class Authority {

    private Long authorityID;
    private User user;
    private String authorityName;

    public Long getAuthorityID() {
        return authorityID;
    }

    public void setAuthorityID(Long authorityID) {
        this.authorityID = authorityID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

}
