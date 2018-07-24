package com.jarvis.rxjavaexamples.views.intro_to_rxjava_rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/*RxJava is a Java VM implementation of Reactive Extensions.*/

/*Reactive programming is programming with asynchronous data streams.*/

public class FirstRxJavaExampleActivity extends AppCompatActivity {

    //tag name we can use as Activity name
    private static final String TAG = FirstRxJavaExampleActivity.class.getSimpleName();

    /**
     * Creating an Observable ≈An Observable emits the data stream≈
     * Creating an Observer ≈An Observer receives the data emitted by Observable≈
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_rx_java_example);

        //Our First Observable
        Observable<String> animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");

        //Our First Observer
        Observer<String> animalObserver = getANimalObserver();

        //Observer subscribing to observable
        //{Make Observer subscribe to Observable so that it can start receiving the data whatever is emitted by Observable.}
        animalsObservable.
                subscribeOn(Schedulers.io()).   //This tells the Observable to run the task on a background thread
                observeOn(AndroidSchedulers.mainThread()).    // This tells the observer to receive the data on Android's UI thread so can perform the action on Android UI
                subscribe(animalObserver);
    }

    /**
     * This is the implementation of a new Observer
     */
    private Observer<String> getANimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name=" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }
}
