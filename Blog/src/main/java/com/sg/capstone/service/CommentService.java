/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.User;
import java.util.List;

/**
 *
 * @author admin
 */
public interface CommentService {

    public void addComment(Comment comment);

    public void deleteComment(Long commentID);

    public void updateComment(Comment comment);

    public Comment getCommentById(Long CommentID);

    public List<Comment> getAllComments();

    public List<Comment> getAllCommentsByBlogPost(Long blogPostID);

    public List<Comment> getAllCommentsByUserID(Long userID);
}
