package com.jarvis.rxjavaexamples.views.intro_to_rxjava_rxandroid;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class ObserverWithDisposableExampleActivity extends AppCompatActivity {

    //tag name we can use as Activity name
    private static final String TAG = ObserverWithDisposableExampleActivity.class.getSimpleName();

    /**
     * Creating an Observable ≈An Observable emits the data stream≈
     * Creating an Observer ≈An Observer receives the data emitted by Observable≈
     * Creating/Using a Disposable ≈Adding a Disposable to un-subscribe the Subscribed Observer≈
     * Disposable: Disposable is used to dispose the subscription when an Observer no longer wants to listen to Observable.
     * In android disposable are very useful in avoiding memory leaks.
     */


    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_rx_java_example);

        //Animal Observable
        Observable<String> animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");

        //ANimal Observer
        Observer<String> animalObserver = getANimalObserver();

        //Observer subscribing to observable so that it can start receiving the data whatever is emitted
        animalsObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
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
                disposable = d;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // don't send events once the activity is destroyed
        disposable.dispose();
    }
}
