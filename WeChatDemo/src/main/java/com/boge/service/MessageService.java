package com.boge.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by boge on 2018/1/30.
 */
public interface MessageService {
    String receiveTextMessage(HttpServletRequest request) throws Exception;

    String analyMessage(HttpServletRequest request) throws Exception;
}
