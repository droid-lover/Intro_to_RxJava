package com.jarvis.rxjavaexamples.views.rx_operators;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Just Operator is used to create an Observable from a set of items.
 * Using these operators is pretty simple as they won’t do any complex operations other than creating an Observable.
 * <p>
 * <p>
 * 1. Just()
 * Just() operator takes a list of arguments and converts the items into Observable items.
 * It takes arguments between one to ten (But the official document says one to nine 🙁 ,
 * may be it’s language specific).
 * Let’s consider the below example. Here an Observable is created using just() from a series of integers.
 * The limitation of just() is, you can’t pass more than 10 arguments.
 */
public class JustOperatorActivity extends AppCompatActivity {

    private final String TAG = JustOperatorActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_operator);

        //Our Observable
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //Our Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "e");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: all events are emitted");
            }
        };

        //subscribing the observer to our observable
        observable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);


        /*  ONE LITTLE TRICK IS WE CAN USE RANGE AND THIS WAY WE CAN PASS MORE THAN 10 items in Observable created with JUST OPERATOR
                                                       observable.range(1,50).
         */
    }

    /**
     * Handling Back Press Event
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();//Stop receiving / dont send events when activity is destroyed.
        }
    }

}



/* JUST takes only 1 emission and FROMARRAY takes n emission */