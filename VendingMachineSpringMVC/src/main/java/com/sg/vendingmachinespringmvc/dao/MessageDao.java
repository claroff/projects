/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Message;

/**
 *
 * @author chandler
 */
public interface MessageDao {

    public Message getMessage();

    public void clearMessage();

}
