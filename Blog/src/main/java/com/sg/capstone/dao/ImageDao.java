/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface ImageDao {

    public void addImage(Image image);
    
    public Long insertImage(byte[] imageBytes, String fileName);

    public void deleteImage(Long imageID);

    public void updateImage(Image image);

    public Image getImageByID(Long ImageID);

    public List<Image> getAllImages();

    public List<Image> getAllImagesByBlogPost(Long blogPostID);

    public Image getImageByUserID(Long userID);

    public Image getImageByBlogPostID(Long blogPostID);

    public void uploadImage(String image, MultipartFile graphic);
}
