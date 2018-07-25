package com.jarvis.rxjavaexamples.views.rx_operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FromArrayActivity extends AppCompatActivity {

    //tag name we can use as Activity name
    private static final String TAG = FromArrayActivity.class.getSimpleName();
    //Fruits StringsArray
    private String[] fruitArray = {"Apple", "Banana", "Cherry", "Dates", "Mango", "Orange", "Litchi", "Papaya", "Muskmelon", "Chiku", "Watermelon"};
    //Integer Array
    private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    //Disposable
    Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array);


        //Our Observable - fruit
        Observable<String> fruitsObservable = getFruitsObservable();
        //Our Observer -numbers
        Observable<Integer> numbersObservable = getNumbersObservable();

        //Our Observer - fruit
        Observer<String> fruitsObserver = getFruitsObserver();
        //Our Observer - numbers
        Observer<Integer> numbersObserver = getNumbersObserver();

        //subscribe fruits Observer to fruit Observable and Number Observer to Number Observable
        fruitsObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(fruitsObserver);

        numbersObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(numbersObserver);

    }

    private Observer<String> getFruitsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe getFruitsObserver");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onNext" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All fruits emitted!");
            }
        };
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


    private Observable<String> getFruitsObservable() {
        return Observable.fromArray(fruitArray);
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