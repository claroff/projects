/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.PostTagBridge;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface PostTagBridgeDao {

    public void addPostTagBridge(PostTagBridge postTagBridge);

    public void deletePostTagBridge(Long postTagBridgeID);

    public void editPostTagBridge(PostTagBridge postTagBridgeID);

    public PostTagBridge getPostTagBridgeByID(Long postTagBridgeID);

    public List<PostTagBridge> getAllPostTagBridges();

    public PostTagBridge getPostTagBridgeByTagAndPostIDs(Long TagID, Long PostID);

    public List<PostTagBridge> getAllPostTagBridgesByPostID(Long postID);

    public List<PostTagBridge> getAllPostTagBridgesByTagID(Long tagID);
}
