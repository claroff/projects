/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.PostTagBridge;
import java.util.List;
import javax.inject.Inject;
import com.sg.capstone.dao.PostTagBridgeDao;

/**
 *
 * @author apprentice
 */
public class PostTagBridgeServiceDBImpl implements PostTagBridgeService {

    PostTagBridgeDao postTagBridgeDao;

    @Inject
    public PostTagBridgeServiceDBImpl(PostTagBridgeDao postTagBridgeDao) {
        this.postTagBridgeDao = postTagBridgeDao;
    }

    @Override
    public void addPostTagBridge(PostTagBridge postTagBridge) {
        postTagBridgeDao.addPostTagBridge(postTagBridge);
    }

    @Override
    public void deletePostTagBridge(long postTagBridgeID) {
        postTagBridgeDao.deletePostTagBridge(postTagBridgeID);
    }

    @Override
    public void editPostTagBridge(PostTagBridge postTagBridgeID) {
        postTagBridgeDao.editPostTagBridge(postTagBridgeID);
    }

    @Override
    public PostTagBridge getPostTagBridgeByID(long postTagBridgeID) {
        return postTagBridgeDao.getPostTagBridgeByID(postTagBridgeID);
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridges() {
        return postTagBridgeDao.getAllPostTagBridges();
    }

    @Override
    public PostTagBridge getPostTagBridgeByTagAndPostIDs(Long TagID, Long PostID) {
        return postTagBridgeDao.getPostTagBridgeByTagAndPostIDs(TagID, PostID);
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridgesByPostID(Long postID) {
        return postTagBridgeDao.getAllPostTagBridgesByPostID(postID);
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridgesByTagID(Long tagID) {
        return postTagBridgeDao.getAllPostTagBridgesByTagID(tagID);
    }

}
