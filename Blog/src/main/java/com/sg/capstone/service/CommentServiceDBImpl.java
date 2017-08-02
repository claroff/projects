/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.CommentDao;
import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.User;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
public class CommentServiceDBImpl implements CommentService {

    CommentDao commentDao;

    @Inject
    public CommentServiceDBImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }

    @Override
    public void deleteComment(Long commentID) {
        commentDao.deleteComment(commentID);
    }

    @Override
    public void updateComment(Comment comment) {
        commentDao.updateComment(comment);
    }

    @Override
    public Comment getCommentById(Long CommentID) {
        return commentDao.getCommentById(CommentID);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDao.getAllComments();
    }

    @Override
    public List<Comment> getAllCommentsByBlogPost(Long blogPostID) {
        return commentDao.getAllCommentsByBlogPost(blogPostID);
    }

    @Override
    public List<Comment> getAllCommentsByUserID(Long userID) {
        return commentDao.getAllCommentsByUserID(userID);
    }

}
