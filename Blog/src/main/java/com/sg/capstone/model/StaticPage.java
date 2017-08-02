/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.model;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class StaticPage {

    private long staticPageID;
    private int navIndex;
    private String staticPageName;
    private String staticPageContent;

    public long getStaticPageID() {
        return staticPageID;
    }

    public void setStaticPageID(long staticPageID) {
        this.staticPageID = staticPageID;
    }

    public int getNavIndex() {
        return navIndex;
    }

    public void setNavIndex(int navIndex) {
        this.navIndex = navIndex;
    }

    public String getStaticPageName() {
        return staticPageName;
    }

    public void setStaticPageName(String staticPageName) {
        this.staticPageName = staticPageName;
    }

    public String getStaticPageContent() {
        return staticPageContent;
    }

    public void setStaticPageContent(String staticPageContent) {
        this.staticPageContent = staticPageContent;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.staticPageID ^ (this.staticPageID >>> 32));
        hash = 59 * hash + this.navIndex;
        hash = 59 * hash + Objects.hashCode(this.staticPageName);
        hash = 59 * hash + Objects.hashCode(this.staticPageContent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StaticPage other = (StaticPage) obj;
        if (this.staticPageID != other.staticPageID) {
            return false;
        }
        if (this.navIndex != other.navIndex) {
            return false;
        }
        if (!Objects.equals(this.staticPageName, other.staticPageName)) {
            return false;
        }
        if (!Objects.equals(this.staticPageContent, other.staticPageContent)) {
            return false;
        }
        return true;
    }

}
