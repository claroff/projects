/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.Image;
import com.sg.capstone.service.ImageService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chandler
 */
@Controller
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(
            value = "/get-image-with-media-type/{blogID}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable("blogID") String blogID) throws IOException {

        Long longBlog = Long.parseLong(blogID);
        Image image = imageService.getImageByBlogPostID(longBlog);
        return image.getImage();
    }

    @GetMapping(
            value = "/get-user-image-with-media-type/{userID}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getUserImageWithMediaType(@PathVariable("userID") String userID) throws IOException {

        Long longUser = Long.parseLong(userID);
        Image image = imageService.getImageByUserID(longUser);
        return image.getImage();
    }

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    public String saveImageToDatabase(HttpServletRequest request, Model model,
            @RequestParam("file") MultipartFile graphicFile, @RequestParam("name") String name) {
        imageService.uploadImage(name, graphicFile);

        return "adminPage";
    }

    @RequestMapping(value = "/image/{imageID}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageByID(@PathVariable("imageID") Long imageID) {

        Image img = imageService.getImageByID(imageID);
        
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img.getImage());
    }
}
