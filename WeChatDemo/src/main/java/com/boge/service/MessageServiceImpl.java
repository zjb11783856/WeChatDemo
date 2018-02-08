package com.boge.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boge.pojo.Article;
import com.boge.pojo.EventMessage;
import com.boge.pojo.NewsMessage;
import com.boge.pojo.TextMessage;
import com.boge.util.MessageUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by boge on 2018/1/30.
 */
@Service
public class MessageServiceImpl implements MessageService {

    /**
     * 接收文本信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String receiveTextMessage(HttpServletRequest request) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
        BufferedReader br = new BufferedReader(inputStreamReader);
        String message = br.readLine();
        TextMessage textMessage = JSON.parseObject(message, TextMessage.class);
        System.out.println(textMessage.getContent());
        return null;
    }

    @Override
    public String analyMessage(HttpServletRequest request) throws Exception {
        Map<String, String> messagegMap = MessageUtil.parseXml(request);
        String msgType = messagegMap.get("MsgType");
        String json = JSON.toJSONString(messagegMap, SerializerFeature.WriteMapNullValue);
        String respMessage = "";
        switch (msgType) {
            case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
                TextMessage textMessage = JSON.parseObject(json, TextMessage.class);
                TextMessage textMessageTemp = new TextMessage();
                switch (textMessage.getContent()) {
                    case "1":
                        textMessageTemp.setContent("一帆风顺");
                        break;
                    case "2":
                        textMessageTemp.setContent("二人同心");
                        break;
                    case "3":
                        textMessageTemp.setContent("三羊开泰");
                        break;
                    case "4":
                        textMessageTemp.setContent("四季平安");
                        break;
                    case "5":
                        textMessageTemp.setContent("五福临门");
                        break;
                    case "6":
                        textMessageTemp.setContent("六六大顺");
                        break;
                    case "7":
                        textMessageTemp.setContent("七窍玲珑");
                        break;
                    case "8":
                        textMessageTemp.setContent("八方来朝");
                        break;
                    case "9":
                        textMessageTemp.setContent("九九归一");
                        break;
                    case "林卓佳":
                        textMessageTemp.setContent("大傻逼");
                        break;
                    default:
                        textMessageTemp.setContent("我不知道你在说什么");
                }
                textMessageTemp.setToUserName(textMessage.getFromUserName());
                textMessageTemp.setFromUserName(textMessage.getToUserName());
                textMessageTemp.setCreateTime(new Date().getTime());
                textMessageTemp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textMessageTemp.setFuncFlag(0);
                respMessage = MessageUtil.textMessageToXml(textMessageTemp);
                break;
            case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
                EventMessage eventMessage = JSON.parseObject(json, EventMessage.class);
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setToUserName(eventMessage.getFromUserName());
                newsMessage.setFromUserName(eventMessage.getToUserName());
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setFuncFlag(0);
                List<Article> articleList = new ArrayList<Article>();
                Article article = new Article();
                switch (eventMessage.getEventKey()) {
                    case "V1001_SAY_HELLO":
                        article.setTitle("你好");
                        article.setDescription("你好我也好");
                        article.setPicUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3920457104,704571544&fm=27&gp=0.jpg");
                        article.setUrl("http://www.baidu.com");
                        articleList.add(article);
                        break;
                    case "V1001_ABOUT_US":
                        article.setTitle("我们");
                        article.setDescription("我们是最棒的");
                        article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517485660596&di=1a17257ab7c236eac62e03d0f013a30d&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F86%2F42%2F55X58PICdz6_1024.jpg");
                        article.setUrl("http://www.baidu.com");
                        articleList.add(article);
                        break;
                    case "V1001_HAPPY_ME":
                        article.setTitle("你在逗我");
                        article.setDescription("既然如此，那我就给你讲个笑话吧");
                        article.setPicUrl("http://img4q.duitang.com/uploads/item/201412/12/20141212104416_v453v.thumb.700_0.jpeg");
                        article.setUrl("http://www.jokeji.cn/jokehtml/ert/2018013122004498.htm");
                        articleList.add(article);
                        break;
                    default:
                }
                newsMessage.setArticles(articleList);
                newsMessage.setArticleCount(articleList.size());
                respMessage = MessageUtil.newsMessageToXml(newsMessage);
                break;
            default:
        }
        return respMessage;
    }
}
