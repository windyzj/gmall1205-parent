package com.atguigu.gmall1205.publisher.bean;



import java.util.List;
import java.util.Map;

public class SaleDetailInfo {

    Integer total;
 // 饼图集合
       List<OptionGroup>  stat;

    //明细
    List<Map> detail;

    public SaleDetailInfo(Integer total, List<OptionGroup> stat, List<Map> detail) {
        this.total = total;
        this.stat = stat;
        this.detail = detail;
    }



    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<OptionGroup> getStat() {
        return stat;
    }

    public void setStat(List<OptionGroup> stat) {
        this.stat = stat;
    }

    public List<Map> getDetail() {
        return detail;
    }

    public void setDetail(List<Map> detail) {
        this.detail = detail;
    }
}
