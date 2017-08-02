/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.TagDao;
import com.sg.capstone.model.Tag;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
public class TagServiceDBImpl implements TagService {

    TagDao tagDao;

    @Inject
    public TagServiceDBImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }
    
    @Override
    public void addTag(Tag tag) {
        tagDao.addTag(tag);
    }

    @Override
    public void deleteTag(Long tagID) {
        tagDao.deleteTag(tagID);
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public Tag getTagByID(Long tagID) {
        return tagDao.getTagByID(tagID);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public Tag getTagByBlogPostID(Long blogPostID) {
        return tagDao.getTagByBlogPostID(blogPostID);
    }
    
}
