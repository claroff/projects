/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.Tag;
import java.util.List;

/**
 *
 * @author admin
 */
public interface TagService {

    public void addTag(Tag tag);

    public void deleteTag(Long tagID);

    public void updateTag(Tag tag);

    public Tag getTagByID(Long tagID);

    public List<Tag> getAllTags();

    public Tag getTagByBlogPostID(Long blogPostID);
}
