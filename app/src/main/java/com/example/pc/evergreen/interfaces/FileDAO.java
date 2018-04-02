package com.example.pc.evergreen.interfaces;

import android.content.Context;

import java.net.URL;
import java.util.List;

/**
 * Created by huangyuansong on 2018/1/17.
 * 文件操作接口：上传、查询、删除、修改接口
 */
public interface FileDAO {


    //删除单个文件
    void fileDelete(Context context, String fileUrl);

    //删除多个文件
    void  filesDelete();
}
