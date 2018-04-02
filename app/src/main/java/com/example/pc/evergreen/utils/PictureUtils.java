package com.example.pc.evergreen.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

import com.example.pc.evergreen.beans.ImageItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PictureUtils {

    public static String photoLocalPath = String.valueOf(Environment.getExternalStorageDirectory());

    /**
     * 保存单个图片到临时文件夹,改名并返回文件路径
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName) {
        FileOutputStream fileOutputStream = null;
        String localPath = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) { // 转换完成
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }

    /**
     * 保存多个服务图片到临时文件夹，改名并返回图片路径数组
     * @param tempSelectBitmap 用户选择的图片数组
     * @param serviceNumber 服务编号
     * @return
     */

    public static String[] savePhotos(ArrayList<ImageItem> tempSelectBitmap, String serviceNumber) {
        FileOutputStream fileOutputStream = null;
        String[] photoPaths = new String[tempSelectBitmap.size()];//用于存放图片路径的字符串数组
        for (int i = 0;i<tempSelectBitmap.size();i++){
            Bitmap bitmap = tempSelectBitmap.get(i).getBitmap();
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File dir = new File(photoLocalPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File photoFile = new File(photoLocalPath, serviceNumber + "_"+i + ".png");
                try {
                    fileOutputStream = new FileOutputStream(photoFile);
                    if ( bitmap!= null) {
                        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                                fileOutputStream)) { // 转换完成
                            //将图片路径添加到图片路径数组
                            photoPaths[i] = photoFile.getPath();
                            fileOutputStream.flush();
                        }
                    }
                } catch (FileNotFoundException e) {
                    photoFile.delete();
                    photoPaths = null;
                    e.printStackTrace();
                } catch (IOException e) {
                    photoFile.delete();
                    photoPaths = null;
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                            fileOutputStream = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return photoPaths;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置相交时的模式
        canvas.drawBitmap(bitmap, src, dst, paint); //
        return output;
    }


}
