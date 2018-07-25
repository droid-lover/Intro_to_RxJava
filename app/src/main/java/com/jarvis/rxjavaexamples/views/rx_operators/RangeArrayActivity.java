package com.jarvis.rxjavaexamples.views.rx_operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RangeArrayActivity extends AppCompatActivity {

    //tag name we can use as Activity name
    private static final String TAG = RangeArrayActivity.class.getSimpleName();
    //Integer Array
    private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    //Disposable
    Disposable disposable;


    /**
     *   RANGE WILL ONLY WORK WITH INTEGER KIND OF OBSERVABLE.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array);


        //Our Observer -numbers
        Observable<Integer> numbersObservable = getNumbersObservable();

        //Our Observer - numbers
        Observer<Integer> numbersObserver = getNumbersObserver();

        //subscribe  Number Observer to Number Observable
        numbersObservable.range(1, 10).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(numbersObserver);

    }

    private Observer<Integer> getNumbersObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe getNumbersObserver");
                disposable = d;
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onNext" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All numbers emitted!");
            }
        };
    }


    private Observable<Integer> getNumbersObservable() {
        return Observable.fromArray(numbers);
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