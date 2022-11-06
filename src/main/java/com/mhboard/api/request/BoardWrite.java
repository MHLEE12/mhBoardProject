package com.mhboard.api.request;

import lombok.ToString;

@ToString
public class BoardWrite {

    public String title;
    public String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //    @Override
//    public String toString() {
//        return "BoardWrite{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
