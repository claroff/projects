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
public class BlogPostCategoryBridge {
    
    private long blogPostCategoryID;
    private BlogPost blogPost;
    private Category category;

    public long getBlogPostCategoryID() {
        return blogPostCategoryID;
    }

    public void setBlogPostCategoryID(long blogPostCategoryID) {
        this.blogPostCategoryID = blogPostCategoryID;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.blogPostCategoryID ^ (this.blogPostCategoryID >>> 32));
        hash = 17 * hash + Objects.hashCode(this.blogPost);
        hash = 17 * hash + Objects.hashCode(this.category);
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
        final BlogPostCategoryBridge other = (BlogPostCategoryBridge) obj;
        if (this.blogPostCategoryID != other.blogPostCategoryID) {
            return false;
        }
        if (!Objects.equals(this.blogPost, other.blogPost)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    
}
