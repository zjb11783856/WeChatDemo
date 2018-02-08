package com.boge.pojo;

/**
 * Created by boge on 2018/2/1.
 */
public class EventMessage extends BaseMessage {
    private String EventKey;
    private String Event;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}
