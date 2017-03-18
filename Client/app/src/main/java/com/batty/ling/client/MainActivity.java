package com.batty.ling.client;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 *
 * @author guolin
 */
public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * 用于展示签到界面的Fragment
     */
    private SigninFragment signinFragment;

    /**
     * 用于展示课程讨论室的Fragment
     */
    private DiscussFragment discussFragment;

    /**
     * 用于展示留言info的Fragment
     */
    private InfoFragment infoFragment;



    /**
     * 登录界面布局
     */
    private View signinLayout;

    /**
     * 讨论室界面布局
     */
    private View discussLayout;

    /**
     * 留言界面布局
     */
    private View infoLayout;


    /**
     * 在Tab布局上显示签到图标的控件
     */
    private ImageView signinImage;

    /**
     * 在Tab布局上显示讨论室图标的控件
     */
    private ImageView discussImage;

    /**
     * 在Tab布局上显示留言图标的控件
     */
    private ImageView infoImage;


    /**
     * 在Tab布局上显示签到标题的控件
     */
    private TextView signinText;

    /**
     * 在Tab布局上显示讨论室标题的控件
     */
    private TextView discussText;

    /**
     * 在Tab布局上显示留言标题的控件
     */
    private TextView infoText;


    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        signinLayout = findViewById(R.id.signin_layout);
        discussLayout = findViewById(R.id.discuss_layout);
        infoLayout = findViewById(R.id.info_layout);

        signinImage = (ImageView) findViewById(R.id.signin_image);
        discussImage = (ImageView) findViewById(R.id.discuss_image);
        infoImage = (ImageView) findViewById(R.id.info_image);

        signinText = (TextView) findViewById(R.id.signin_text);
        discussText = (TextView) findViewById(R.id.discuss_text);
        infoText = (TextView) findViewById(R.id.info_text);

        signinLayout.setOnClickListener(this);
        discussLayout.setOnClickListener(this);
        infoLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_layout:
                // 当点击了签到tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.discuss_layout:
                // 当点击了讨论室tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.info_layout:
                // 当点击了留言tab时，选中第3个tab
                setTabSelection(2);
                break;

            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                signinImage.setImageResource(R.drawable.signin_selected);
                signinText.setTextColor(Color.RED);
                if (signinFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    signinFragment = new SigninFragment();
                    transaction.add(R.id.content, signinFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(signinFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                discussImage.setImageResource(R.drawable.discuss_selected);
                discussText.setTextColor(Color.RED);
                if (discussFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    discussFragment = new DiscussFragment();
                    transaction.add(R.id.content, discussFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(discussFragment);
                }
                break;
            case 2:
            default:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                infoImage.setImageResource(R.drawable.info_selected);
                infoText.setTextColor(Color.RED);
                if (infoFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    infoFragment = new InfoFragment();
                    transaction.add(R.id.content, infoFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(infoFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        signinImage.setImageResource(R.drawable.signin_unselected);
        signinText.setTextColor(Color.parseColor("#82858b"));
        discussImage.setImageResource(R.drawable.discuss_unselected);
        discussText.setTextColor(Color.parseColor("#82858b"));
        infoImage.setImageResource(R.drawable.info_unselected);
        infoText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (signinFragment != null) {
            transaction.hide(signinFragment);
        }
        if (discussFragment != null) {
            transaction.hide(discussFragment);
        }
        if (infoFragment != null) {
            transaction.hide(infoFragment);
        }

    }
}