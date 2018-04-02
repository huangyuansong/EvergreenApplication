package com.example.pc.evergreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pc.evergreen.activitys.DetailsActivity;
import com.example.pc.evergreen.activitys.LoginActivity;
import com.example.pc.evergreen.activitys.MallActivity;
import com.example.pc.evergreen.activitys.ManagerActivity;
import com.example.pc.evergreen.activitys.ModifyPersonalDataActivity;
import com.example.pc.evergreen.activitys.NoticeActivity;
import com.example.pc.evergreen.activitys.PersonalDataActivity;
import com.example.pc.evergreen.activitys.RegisterActivity;
import com.example.pc.evergreen.activitys.StudyActivity;
import com.example.pc.evergreen.adapters.TabAdapter;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.PromptMessageDialog;
import com.example.pc.evergreen.fragments.PageFragment;
import com.example.pc.evergreen.utils.DummyContent;
import com.example.pc.evergreen.utils.PictureUtils;
import com.example.pc.evergreen.utils.SaveUserInformation;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, PageFragment.OnListFragmentInteractionListener {

    private static final String BMOB_APP_KEY="a56887b54d529e249d33bd1f96b444af";
    private ImageView imageView_icon;//侧滑菜单头像
    private ImageView buttonimage_login;//登录头像侧滑菜单
    private DrawerLayout drawer_layout;//侧滑菜单
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;
    private DrawerLayout drawer;
    private View linear_date;
    private TextView et_nickName;
    private String account;
    private String password;
    public static boolean checkBox;
    private SharedPreferences sharedPre;
    public static String Tag;
    private Intent intent;
    private View linear_registration;
    private View linear_login;
    private View linear_logout;
    private View linear_inform;
    private View integral_mall;
    private View administrator_login;
    public  UserData newUserData;
    public static  Uri tempUri;





    public static final String[] tabTitle = new String[]{"活动阵地", "学习阵地"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bmob.initialize(this, BMOB_APP_KEY);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        //当sdk大于等于5.0时，执行以下代码,以便状态栏透明的兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        checkBox = true;
        imageView_icon = (ImageView) findViewById(R.id.imageView_icon);
        imageView_icon.setOnClickListener(this);
        buttonimage_login = findViewById(R.id.icon_login);
        buttonimage_login.setOnClickListener(this);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        linear_date = findViewById(R.id.linear_date);
        linear_date.setOnClickListener(this);
        linear_logout = findViewById(R.id.linear_logout);
        linear_logout.setOnClickListener(this);
        et_nickName = (TextView) findViewById(R.id.nav_nickName);
        linear_registration = findViewById(R.id.linear_registration);
        linear_registration.setOnClickListener(this);
        linear_login = findViewById(R.id.linear_login);
        linear_login.setOnClickListener(this);
        linear_inform = findViewById(R.id.linear_inform);
        linear_inform.setOnClickListener(this);
        integral_mall = findViewById(R.id.integral_mall);
        integral_mall.setOnClickListener(this);
        administrator_login = findViewById(R.id.administrator_login);
        administrator_login.setOnClickListener(this);


        //tabLayout 与viewPager配合使用
        tabLayout = (TabLayout) findViewById(R.id.table_layout);
        List<PageFragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitle[i]));
            fragments.add(PageFragment.newInstance(i + 1));
        }
        //设置tabLayouot可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置TabLayout监听，便于知道用户选择的阵地类型。将类型标签传递到详情Activity，动态改变详情菜单内容。
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
               if (position == 0){
                   Tag = "DETAILS";
               }else if (position == 1){
                   Tag = "STUDY";
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setUseCompatPadding(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //ViewPager适配器赋值
        adapter = new TabAdapter(getSupportFragmentManager(), fragments, tabTitle);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //给ViewPager设置适配器
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_registration:
                Intent intentRegist = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegist);
                drawer_layout.closeDrawer(GravityCompat.START);
                return;
            case R.id.linear_date:
                drawer_layout.closeDrawer(GravityCompat.START);
                isLogin(PersonalDataActivity.class);
                return;
            case R.id.linear_login:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                drawer_layout.closeDrawer(GravityCompat.START);
                return;
            case R.id.linear_logout:
                //先判断有没有登录，如果没有登录，则提示用户还没登录
                if (TextUtils.isEmpty(account)) {
                    final MessageDialog dialog = new MessageDialog(this, "没有可以注销的信息！");
                    dialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.finish();
                        }
                    });
                } else {
                    //注销登录
                    final PromptMessageDialog promptMessageDialog = new PromptMessageDialog(this, "注销后下次登录需要重新输入登录信息！");
                    promptMessageDialog.button_no.setText("取消");
                    promptMessageDialog.button_yes.setText("确定");
                    promptMessageDialog.button_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            promptMessageDialog.close();
                        }
                    });
                    promptMessageDialog.button_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUserInformation.clearLoginInfo(MainActivity.this);
                            promptMessageDialog.close();
                            drawer.closeDrawer(GravityCompat.START);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    return;
                }
            case R.id.icon_login:
                drawer_layout.openDrawer(GravityCompat.START);
                return;
            case R.id.linear_inform:
                Intent intentNotice = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intentNotice);
                drawer.closeDrawer(GravityCompat.START);
                return;
            case R.id.integral_mall:
                Intent intentMall = new Intent(MainActivity.this, MallActivity.class);
                intentMall.putExtra("userData",newUserData);
                startActivity(intentMall);
                drawer.closeDrawer(GravityCompat.START);
                return;
            case R.id.administrator_login:

                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    final MessageDialog dialog = new MessageDialog(this, "请先登录！");
                    dialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            dialog.finish();
                            drawer_layout.closeDrawer(Gravity.START);
                        }
                    });
                } else {
                    //这里可以加一个短信验证功能，防止其它人用管理员账号密码登录（待添加代码）如果验证成功，12小时内不用验证，可直接进入管理界面(待添加代码)

                    String permissions = newUserData.getPermissions();
                    if (permissions.equals("manager")) {
                        Intent intentManagerLogin = new Intent(MainActivity.this, ManagerActivity.class);
                        startActivity(intentManagerLogin);
                        drawer.closeDrawer(GravityCompat.START);
                        return;
                    } else if (!("manager".equals(newUserData.getPermissions()))) {
                        MessageDialog dialog = new MessageDialog(this, "对不起，您不是管理员！");
                    }

                        return;
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPre = getSharedPreferences("config", MODE_PRIVATE);
        account = sharedPre.getString("user", "");
        password = sharedPre.getString("pass", "");

        //将昵称、信誉、诚信、积分、头像信息更新到ui
        if (TextUtils.isEmpty(account)) {
            //如果为空，默认设置相关信息
            et_nickName.setText("游客");
        } else {
            //如果不为空，将从数据库获取信息并更新ui
            tempUri = Uri.fromFile(new File(String.valueOf(Environment.getExternalStorageDirectory()), "head_phonto_" + account + ".png"));
                BmobQuery<UserData> query = new BmobQuery<UserData>();
                query.addWhereEqualTo("account", account);
                query.findObjects(MainActivity.this, new FindListener<UserData>() {
                    @Override
                    public void onSuccess(List<UserData> list) {
                        for (UserData userData1 : list) {
                            userData1 = list.get(0);

                            newUserData = userData1;
                            et_nickName.setText(newUserData.getName());

                            // 通过Volley加载图片到头像控件
                            VolleyLoadPicture vlp1 = new VolleyLoadPicture(MainActivity.this, imageView_icon);
                            VolleyLoadPicture vlp2 = new VolleyLoadPicture(MainActivity.this, buttonimage_login);

                            if (userData1.getPhoto() != null) {
                                vlp1.getmImageLoader().get(userData1.getPhoto(), vlp1.getOne_listener());
                                vlp2.getmImageLoader().get(userData1.getPhoto(), vlp2.getOne_listener());

                            }else {
                                imageView_icon.setImageResource(R.mipmap.icon_head);
                                buttonimage_login.setImageResource(R.mipmap.icon_head);
                            }

                        }
                    }
                    @Override
                    public void onError(int i, String s) {

                    }
                });

        }

    }

    //点击item的监听
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        String itemName = item.service_title;
        if ("DETAILS".equals(Tag)){
            intent = new Intent(this, DetailsActivity.class);
        }else if ("STUDY".equals(Tag)){
            intent = new Intent(this, StudyActivity.class);
        }

        intent.putExtra("ITEM_NAME",itemName);
        intent.putExtra("TAG",Tag);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checkBox==false){
            SaveUserInformation.clearLoginInfo(MainActivity.this);
        }
    }

    /**
     *  判断是否登录的方法，若未登录，弹出提示，转到登录界面，若已登录，跳转到相应界面。
     * @param classes 根据用户的选择，需要跳转的界面
     */
    private void isLogin(final Class classes){
        //如果用户名为空，提示用户先登录,否则将用户名更新到ui
        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            final MessageDialog dialog = new MessageDialog(this,"请先登录！");
            dialog.button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    dialog.finish();
                    drawer_layout.closeDrawer(Gravity.START);
                }
            });
            return;
            //"permissions", "manager"
        }else {
            BmobQuery<UserData> query = new BmobQuery<UserData>();
            query.addWhereEqualTo("account", account);
            query.findObjects(this, new FindListener<UserData>() {
                        @Override
                        public void onSuccess(List<UserData> list) {
                            UserData userData = list.get(0);
                            String permissions = userData.getPermissions();
                            if ("manager".equals(permissions)){
                                final MessageDialog dialog = new MessageDialog(MainActivity.this,"您是管理用户，无须填写个人信息！");
                                return;
                            }else if ("members".equals(permissions)){
                                Intent intent = new Intent(MainActivity.this, classes);
                                intent.putExtra("userData",userData);
                                startActivity(intent);
                                drawer.closeDrawer(GravityCompat.START);
                                return;
                            }else {
                                final MessageDialog dialog = new MessageDialog(MainActivity.this,"请您先登录！");
                                return;
                            }

                        }

                        @Override
                        public void onError(int i, String s) {
                            final MessageDialog dialog = new MessageDialog(MainActivity.this,"非法操作！");

                        }
                    });
        }
    }
}
