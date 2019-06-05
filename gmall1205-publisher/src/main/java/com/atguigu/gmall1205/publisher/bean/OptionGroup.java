package com.atguigu.gmall1205.publisher.bean;

import java.util.List;

public class OptionGroup {

    //  标题
    String title  ;
    //选项列表
     List<Option> options  ;

    public OptionGroup(String title, List<Option> options) {
        this.title = title;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
