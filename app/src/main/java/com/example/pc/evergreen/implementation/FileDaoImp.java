package com.example.pc.evergreen.implementation;

import android.content.Context;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.interfaces.FileDAO;
import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by huangyuansong on 2018/1/17.
 * 文件操作实现类：上传、查询、删除、修改
 */
public class FileDaoImp implements FileDAO {
    String fileUrl = null;
    public List<String> filesUrlsList;//多个文件上传后的url

    public FileDaoImp() {
    }





    //删除单个文件
    @Override
    public void fileDelete(Context context,String fileUrl) {
        BmobFile file = new BmobFile();
        file.setUrl(fileUrl);
        file.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    //删除多个文件
    @Override
    public void filesDelete() {

    }


}
