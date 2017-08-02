/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public class ImageDaoDBImpl implements ImageDao {

    JdbcTemplate jdbcTemplate;

    @Inject
    public ImageDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_IMAGE
            = "insert into Image (ImageName, Image) "
            + "values (?, ?)";

    private static final String SQL_DELETE_IMAGE
            = "delete from Image where ImageID = ?";

    private static final String SQL_UPDATE_IMAGE
            = "update Image set ImageName = ?, Image = ?"
            + "where ImageID = ?";

    private static final String SQL_SELECT_IMAGE
            = "select * from Image where ImageID = ?";

    private static final String SQL_SELECT_ALL_IMAGES
            = "select * from Image";

    private static final String SQL_GET_ALL_IMAGS_BY_BLOGPOSTID
            = "select i.* from Image i "
            + "join BlogPost bp on i.ImageID = bp.ImageID "
            + "join `User` u on i.ImageID = u.ImageID "
            + "where bp.BlogPostID = ?";

    private static final String SQL_GET_ALL_IMAGES_BY_USERID
            = "select * from Image "
            + "join User on User.ImageID = Image.ImageID "
            + "where User.UserID = ?";

    private static final String SQL_GET_IMAGE_BY_BLOGPOSTID
            = "select i.* from Image i "
            + "inner join BlogPost bp on i.ImageID = bp.ImageID "
            + "where bp.BlogPostID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addImage(Image image) {
        jdbcTemplate.update(SQL_INSERT_IMAGE,
                image.getImageName(),
                image.getImage());

        long imageID
                = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()",
                        long.class);
        image.setImageID(imageID);

    }

    @Override
    public void uploadImage(String image, MultipartFile graphic) {
        try {
            byte[] bytes = graphic.getBytes();
            InputStream input = new ByteArrayInputStream(bytes);
            jdbcTemplate.update(SQL_INSERT_IMAGE, image, input);
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteImage(Long imageID) {
        jdbcTemplate.update(SQL_DELETE_IMAGE, imageID);
    }

    @Override
    public void updateImage(Image image) {
        jdbcTemplate.update(SQL_UPDATE_IMAGE,
                image.getImageName(),
                image.getImage(),
                image.getImageID());
    }

    @Override
    public Image getImageByID(Long ImageID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_IMAGE,
                    new ImageMapper(), ImageID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Image getImageByUserID(Long userID) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_ALL_IMAGES_BY_USERID,
                    new ImageMapper(), userID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Image> getAllImages() {
        return jdbcTemplate.query(SQL_SELECT_ALL_IMAGES,
                new ImageMapper());
    }

    @Override
    public List<Image> getAllImagesByBlogPost(Long blogPostID) {
        return jdbcTemplate.query(SQL_GET_ALL_IMAGS_BY_BLOGPOSTID,
                new ImageMapper(), blogPostID);
    }

    @Override
    public Image getImageByBlogPostID(Long blogPostID) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_IMAGE_BY_BLOGPOSTID,
                    new ImageMapper(), blogPostID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    //Burke added this for TINYMCE
    public Long insertImage(byte[] imageBytes, String fileName) {
        jdbcTemplate.update(SQL_INSERT_IMAGE,
                fileName,
                imageBytes);
//        https://stackoverflow.com/questions/10597477/getting-auto-generated-key-from-row-insertion-in-spring-3-postgresql-8-4-9
        long imageID
                = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()",
                        long.class);
        
        return imageID;
    }

    private static final class ImageMapper implements RowMapper<Image> {

        @Override
        public Image mapRow(ResultSet rs, int i) throws SQLException {
            Image image = new Image();
            image.setImageID(rs.getLong("ImageID"));
            image.setImageName(rs.getString("ImageName"));
            image.setImage(rs.getBytes("Image"));
            return image;
        }

    }

}
