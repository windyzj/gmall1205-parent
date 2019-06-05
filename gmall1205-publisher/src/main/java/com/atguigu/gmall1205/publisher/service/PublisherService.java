package com.atguigu.gmall1205.publisher.service;

import java.util.Map;

public interface PublisherService {


    public Integer getDauTotal(String date);


    public Map getDauHourMap(String date);

    public Double getOrderAmount(String date);

    public Map getOrderAmontHourMap(String date);


    public Map  getSaleDetailMap(String date ,String keyword,int pageNo,int pageSize, String aggsFieldName,int aggsSize );

}
