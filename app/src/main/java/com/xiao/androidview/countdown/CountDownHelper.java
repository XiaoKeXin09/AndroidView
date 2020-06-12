package com.xiao.androidview.countdown;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:Created Jmw by HeJingzhou on 2019/7/1 19:36
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect: 倒计时辅助类
 */
public class CountDownHelper {

    private OnCountDownListener onCountDownListener;
    private Disposable disposable;
    private long remainingTime;

    public CountDownHelper(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * 回收倒计时
     */
    public void destory() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 开始倒计时
     */
    public void startCompute() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (onCountDownListener == null) {
                            return;
                        }
                        remainingTime -= 1000;
                        if (remainingTime > 0) {
                            int day = (int) (remainingTime / (1000 * 60 * 60 * 24));
                            int hour = (int) ((remainingTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                            int minute = (int) ((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
                            int second = (int) ((remainingTime % (1000 * 60)) / 1000);
                            String dayStr = day >= 10 ? String.valueOf(day) : "0" + day;
                            String hourStr = hour >= 10 ? String.valueOf(hour) : "0" + hour;
                            String minuteStr = minute >= 10 ? String.valueOf(minute) : "0" + minute;
                            String secondStr = second >= 10 ? String.valueOf(second) : "0" + second;
                            onCountDownListener.countDown(dayStr, hourStr, minuteStr, secondStr);
                            if (remainingTime <= 0) {
                                onCountDownListener.countDownFinish();
                                if (disposable != null && !disposable.isDisposed()) {
                                    disposable.dispose();
                                }
                            }
                        } else {
                            onCountDownListener.countDownFinish();
                            if (disposable != null && !disposable.isDisposed()) {
                                disposable.dispose();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 设置倒计时回调监听
     *
     * @param onCountDownListener 倒计时监听
     */
    public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
    }

    public interface OnCountDownListener {

        /**
         * 倒计时
         *
         * @param day    天
         * @param hour   小时
         * @param minute 分钟
         * @param second 秒
         */
        void countDown(String day, String hour, String minute, String second);

        /**
         * 倒计时完成
         */
        void countDownFinish();
    }
}
