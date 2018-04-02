package com.example.pc.evergreen.utils;

import com.example.pc.evergreen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于装载编辑界面数据
 * Created by pc on 2018/3/19.
 */

public class DummyContentManager {

    public static final List<DummyItemManager>  EDITOR = new ArrayList<>();//资料编辑界面数据
    public static final List<DummyItemManager> NOTICE = new ArrayList<>();//通知界面数据
    public static final List<DummyItemManager> INTEGRAL = new ArrayList<>();//积分界面数据
    public static final List<DummyItemManager> STATISTICAL = new ArrayList<>();//统计界面数据


    public static String[] editorStr = {"老干部资料","学员资料","活动设施","学习科目","积分商城","积分兑换"};
    public static String[] noticeStr = {"活动通知","会议通知","生日通知","学习通知"};
    public static String[] intergalStr = {"修改积分"};
    public static String[] statisticalStr = {"老干部统计","学员统计","活动统计","学习统计","兴趣统计"};


    public static int[] editorImage = {R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity};
    public static int[] noticeImage= {R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity};
    public static int[] intergalImage = {R.mipmap.activity};
    public static int[] statisticalImage= {R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,R.mipmap.activity,
            R.mipmap.activity};
    private static final int COUNT = 10;




    static {
//初始化活动阵地、学习阵地按钮数据
        for (int i = 0;i<editorStr.length;i++){
            EDITOR.add(new DummyContentManager.DummyItemManager(R.mipmap.activity,editorStr[i]));
        }
        for (int i = 0;i<noticeStr.length;i++){
            NOTICE.add(new DummyContentManager.DummyItemManager(R.mipmap.activity,noticeStr[i]));
        }
        for (int i = 0;i<intergalStr.length;i++){
            INTEGRAL.add(new DummyContentManager.DummyItemManager(R.mipmap.activity,intergalStr[i]));
        }
        for (int i = 0;i<statisticalStr.length;i++){
            STATISTICAL.add(new DummyContentManager.DummyItemManager(R.mipmap.activity,statisticalStr[i]));
        }

    }


    public static class DummyItemManager{


        public final String service_title;
        public final int service_image;


        public DummyItemManager(int ac_image,String ac_title)
        {
            this.service_image = ac_image;
            this.service_title = ac_title;

        }

    }
}
