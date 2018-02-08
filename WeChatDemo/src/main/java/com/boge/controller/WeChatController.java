package com.boge.controller;

import com.boge.service.MessageService;
import com.boge.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by boge on 2018/1/12.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/wechat")
public class WeChatController {
    private static Logger log = LoggerFactory.getLogger(WeChatController.class);
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/checkSignature")
    public String checkSignature(@RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "echostr", required = false) String echostr, HttpServletRequest request, HttpServletResponse response) {
        //设置中文编码格式
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.info("接入成功");
                String result = messageService.analyMessage(request);
                response.getWriter().write(result);
                return echostr;
            }
        } catch (Exception e) {
            log.error("接入失败");
        }

        return "";
    }

    @RequestMapping(value = "/receiveTextMessage")
    public String receiveTextMessage(HttpServletRequest request) {
        String result = "";
        try {
            result = messageService.receiveTextMessage(request);
        } catch (Exception e) {
            log.error("receiveTxtMessage()", e);
        }
        return result;
    }
}
