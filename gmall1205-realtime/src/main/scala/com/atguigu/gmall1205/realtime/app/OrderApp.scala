package com.atguigu.gmall1205.realtime.app

import com.alibaba.fastjson.JSON
import com.atguigu.gmall1205.common.constant.GmallConstant
import com.atguigu.gmall1205.common.util.MyEsUtil
import com.atguigu.gmall1205.realtime.bean.OrderInfo
import com.atguigu.gmall1205.realtime.util.MyKafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object OrderApp {

  def main(args: Array[String]): Unit = {
      val sparkConf: SparkConf = new SparkConf().setAppName("order_app").setMaster("local[*]")

     val ssc = new StreamingContext(sparkConf,Seconds(5))
    // 保存到ES
    //数据脱敏  补充时间戳
    val inputDstream: InputDStream[ConsumerRecord[String, String]] = MyKafkaUtil.getKafkaStream(GmallConstant.KAFKA_TOPIC_ORDER,ssc)

    val orderInfoDstream: DStream[OrderInfo] = inputDstream.map { record =>
      val jsonstr: String = record.value()
      val orderInfo: OrderInfo = JSON.parseObject(jsonstr, classOf[OrderInfo])

      val telSplit: (String, String) = orderInfo.consigneeTel.splitAt(4)
      orderInfo.consigneeTel = telSplit._1 + "*******"

      val datetimeArr: Array[String] = orderInfo.createTime.split(" ")
      orderInfo.createDate = datetimeArr(0)
      val timeArr: Array[String] = datetimeArr(1).split(":")
      orderInfo.createHour = timeArr(0)
      orderInfo.createHourMinute = timeArr(0) + ":" + timeArr(1)
      orderInfo
    }
    orderInfoDstream

    // 增加一个字段 0或者1  标识该订单是否是该用户首次下单

    orderInfoDstream.foreachRDD{rdd=>

      rdd.foreachPartition{orderItr=>
        MyEsUtil.indexBulk(GmallConstant.ES_INDEX_ORDER,orderItr.toList)
      }
    }


     ssc.start()
    ssc.awaitTermination()


  }

}
