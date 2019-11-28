package com.jarvis.rxjavaexamples.views.intro_to_rxjava_rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.jarvis.rxjavaexamples.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sachin
 */

public class SecondRxJavaActivity extends AppCompatActivity {

    //tag name we can use as Activity name
    private static final String TAG = SecondRxJavaActivity.class.getSimpleName();

    private String[] fruitArray = {"Apple", "Banana", "Cherry", "Dates", "Mango", "Orange", "Litchi", "Papaya", "Muskmelon", "Chiku", "Watermelon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_rx_java);

        //Our Second Observable
        Observable<String[]> fruitsObservable = Observable.just(fruitArray);

        //Our Second Observer
        Observer<String[]> fruitsObserver = new Observer<String[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String[] strings) {
                if (strings != null && strings.length > 0) {
                    for (int i = 0; i < strings.length; i++) {
                        Log.d(TAG, "Name of Fruits" + strings[i]);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };


        //subscribing the Observer to our Observable

        fruitsObservable.
                subscribeOn(Schedulers.io()).   //This tells the Observable to run the task on a background thread
                observeOn(AndroidSchedulers.mainThread()).    // This tells the observer to receive the data on Android's UI thread so can perform the action on Android UI
                subscribe(fruitsObserver);
    }
}
