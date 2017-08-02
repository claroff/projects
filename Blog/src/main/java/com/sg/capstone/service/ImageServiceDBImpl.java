/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.ImageDao;
import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public class ImageServiceDBImpl implements ImageService {

    ImageDao imageDao;

    @Inject
    public ImageServiceDBImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public void addImage(Image image) {
        imageDao.addImage(image);
    }

    @Override
    public void deleteImage(Long imageID) {
        imageDao.deleteImage(imageID);
    }

    @Override
    public void updateImage(Image image) {
        imageDao.updateImage(image);
    }

    @Override
    public Image getImageByID(Long ImageID) {
        return imageDao.getImageByID(ImageID);
    }

    @Override
    public List<Image> getAllImages() {
        return imageDao.getAllImages();
    }

    @Override
    public List<Image> getAllImagesByBlogPost(Long blogPostID) {
        return imageDao.getAllImagesByBlogPost(blogPostID);
    }

    @Override
    public Image getImageByUserID(Long userID) {
        return imageDao.getImageByUserID(userID);
    }

    @Override
    public Image getImageByBlogPostID(Long blogPostID) {
        return imageDao.getImageByBlogPostID(blogPostID);
    }

    @Override
    public void uploadImage(String image, MultipartFile graphic) {
        imageDao.uploadImage(image, graphic);
    }

    @Override
    public Long insertImage(byte[] imageBytes, String fileName) {
        return imageDao.insertImage(imageBytes, fileName);
    }

}
