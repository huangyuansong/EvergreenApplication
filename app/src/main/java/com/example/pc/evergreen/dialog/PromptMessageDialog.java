package com.example.pc.evergreen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.evergreen.R;


/**
 * Created by huangyuansong on 2017/12/29.
 * 消息对话框（有取消、确定按钮）
 */
public class PromptMessageDialog{

    private TextView textView_title;//对话框标题
    private TextView textView_message;//消息
    public Button button_yes;//确定按钮
    public Button button_no;
    private Dialog dialog;



    public PromptMessageDialog(Context context, String message){

        dialog = new Dialog(context, R.style.NormalDialogStyle);
        View view = View.inflate(context,R.layout.alerts_message_2,null);
        textView_title = (TextView) view.findViewById(R.id.alerts_title_one);
        textView_message = (TextView) view.findViewById(R.id.alerts_massage_2);
        button_yes = (Button) view.findViewById(R.id.alerts_button_yes);
        button_no = (Button) view.findViewById(R.id.alerts_button_no);

        textView_message.setText(message);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(false);
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void close(){
        dialog.dismiss();
    }


}
