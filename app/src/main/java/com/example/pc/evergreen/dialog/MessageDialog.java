package com.example.pc.evergreen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.evergreen.R;


/**
 * Created by huangyuansong on 2017/12/29.
 * 消息对话框（只有确定按钮）
 */
public class MessageDialog{

    private TextView textView_title;//对话框标题
    private TextView textView_message;//消息
    public Button button_ok;//确定按钮
    private Dialog dialog;
    /**
     *
     * @param context
     */

    public MessageDialog(Context context,String message){

        dialog = new Dialog(context, R.style.NormalDialogStyle);
        View view = View.inflate(context,R.layout.alerts_message_1,null);
        textView_title = (TextView) view.findViewById(R.id.alerts_title_one);
        textView_message = (TextView) view.findViewById(R.id.alerts_massage_1);
        button_ok = (Button) view.findViewById(R.id.alerts_button_ok);
        textView_message.setText(message);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void finish(){
        dialog.dismiss();
    }



}
