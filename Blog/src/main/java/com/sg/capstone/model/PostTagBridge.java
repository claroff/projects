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
public class PostTagBridge {
 
    private long postTagID;
    private BlogPost blogPost;
    private Tag tag;

    public long getPostTagID() {
        return postTagID;
    }

    public void setPostTagID(long postTagID) {
        this.postTagID = postTagID;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.postTagID ^ (this.postTagID >>> 32));
        hash = 29 * hash + Objects.hashCode(this.blogPost);
        hash = 29 * hash + Objects.hashCode(this.tag);
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
        final PostTagBridge other = (PostTagBridge) obj;
        if (this.postTagID != other.postTagID) {
            return false;
        }
        if (!Objects.equals(this.blogPost, other.blogPost)) {
            return false;
        }
        if (!Objects.equals(this.tag, other.tag)) {
            return false;
        }
        return true;
    }
    
    
    
        
    
}
