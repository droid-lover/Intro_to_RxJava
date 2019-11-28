package com.jarvis.rxjavaexamples.views.intro_to_rxjava_rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

public class FilterOperatorExampleActivity extends AppCompatActivity {


    /**
     * filter() operator filters the data by applying a conditional statement.
     * ≈ The data which meets the condition will be emitted and the remaining will be ignored. ≈
     */

    private final String TAG = FilterOperatorExampleActivity.class.getSimpleName();

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_operator_example);

        //Observable
        Observable<String> animalObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
        //Observer
        Observer<String> animalObserver = getAnimalObserver();

        //subscribing the Observer with Observable , so it can receives the data emitted by Observable

        animalObservable.
                subscribeOn(Schedulers.io()).
                filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("d");  //We are adding Filter Here.
                    }
                }).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(animalObserver);
    }

    private Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext=" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();  // don't send events once the activity is destroyed.
    }
}
