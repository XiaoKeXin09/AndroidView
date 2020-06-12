package com.xiao.androidview.countdown;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xiao.androidview.R;


public class TestCountActivity extends AppCompatActivity {
    private CountDownHelper mCountDownHelper;
    private StrokeTextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_count);

        mTvTest = findViewById(R.id.tv_test);

//设置自定义字体
        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "fonts/Alibaba-PuHuiTi-Heavy.ttf");
        mTvTest.setTypeface(fromAsset, Typeface.ITALIC);  //自定义字体 ITALIC斜体

        long aLong = 1787;
        mCountDownHelper = new CountDownHelper(aLong * 1000);
        mCountDownHelper.startCompute();
        mCountDownHelper.setOnCountDownListener(new CountDownHelper.OnCountDownListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void countDown(String day, String hour, String minute, String second) {
                mTvTest.setText(hour + ":" + minute + ":" + second);
            }

            @Override
            public void countDownFinish() {
                Log.d("", "结束倒计时");
                mCountDownHelper.destory();
                //延时跳转
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {

                        Toast.makeText(TestCountActivity.this, "时间到了", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }).sendEmptyMessageDelayed(0, 10000);//表示延迟10秒发送任务

            }
        });


    }
}
