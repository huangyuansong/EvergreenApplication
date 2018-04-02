package com.example.pc.evergreen.utils;

import com.example.pc.evergreen.R;

import java.util.ArrayList;
import java.util.List;


/**
* 用于首页装载数据
*/

public class DummyContent {


    public static final List<DummyItem> ITEMS_1 = new ArrayList<DummyItem>();
    public static final List<DummyItem> ITEMS_2 = new ArrayList<DummyItem>();

    public static String[] acNameStr = {"保龄球","沙弧球","汽旋球","冰壶球","投篮机","飞镖机","指弹球","跑步机","登山机",
            "综合训练器","动感单车","美腰机","划船机","综合按摩仪","健康检测","英式台球","美式台球","乒乓球","羽毛球",
            "网球","影视厅","手工创作","休闲茶歇","书画创作","中国象棋","国际象棋","军棋","跳棋","围棋","电子阅览馆",
            "图书馆","麻将"};
    public static String[] stNameStr = {"讲习所","合唱团","舞蹈队","时装队","小合唱班","花鸟绘画基础","花鸟绘画提高","山水绘画基础","山水绘画提高",
            "书法基础","书法提高","舞蹈二班","舞蹈三班","曲艺班","形体基础一","形体基础二","文学班","太极拳班","交谊舞一",
            "交谊舞二","电子琴一","电子琴二","摄影基础","摄影提高","电脑一班","电脑二班","电脑三班","电脑四班","诗词曲联","布艺一班",
            "布艺二班","音乐基础一班","音乐基础二班","音乐提高班","葫芦丝基础班","葫芦丝提高班","英语基础班","英语提高班","普通话基础班","普通话提高班"};
    public static int[] acImageInt = {R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity};
    public static int[] stImageInt = {R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity,R.mipmap.activity,R.mipmap.activity};
    private static final int COUNT = 10;




    static {
//初始化活动阵地、学习阵地按钮数据
        for (int i = 0;i<acNameStr.length;i++){
            ITEMS_1.add(new DummyItem(R.mipmap.activity,acNameStr[i]));
        }
       for (int u = 0;u<stNameStr.length;u++){
            ITEMS_2.add(new DummyItem(R.mipmap.activity,stNameStr[u]));
       }

    }


    public static class DummyItem{


        public final String service_title;
        public final int service_image;


        public DummyItem(int ac_image,String ac_title)
        {
            this.service_image = ac_image;
            this.service_title = ac_title;

        }

    }
}
