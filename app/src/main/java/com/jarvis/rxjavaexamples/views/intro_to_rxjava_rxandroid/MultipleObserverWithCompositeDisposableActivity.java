package com.jarvis.rxjavaexamples.views.intro_to_rxjava_rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

public class MultipleObserverWithCompositeDisposableActivity extends AppCompatActivity {

    /**
     * Consider when we have multiple Observables and Observers , disposing them in onDestroy one by one can be a tedious task and it can
     * lead to any error.In Such conditions we use CopositeDisposable.
     * ≈CompositeDisposable can maintain the list of subscriptions in a pool and can dispose them all at once.≈
     * <p>
     * ≈In this below Example we can notice there are two Observers subscribed to the same Observable .The both Observers receives the same data
     * but the data changes as the different Operators are applied to the data_stream.
     */

    private static final String TAG = MultipleObserverWithCompositeDisposableActivity.class.getSimpleName();
    private String[] animalsArray = {"Ant", "Ape", "Bat", "Bee", "Bear", "Butterfly", "Cat", "Crab", "Cod", "Dog", "Dove", "Fox", "Frog"};

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_observer_with_composite_disposable);

        Observable<String> animalsObservable = getAnimalsObservable();

        DisposableObserver<String> animalsObserver = getAnimalsObserver();

        DisposableObserver<String> animalsObserverAllCaps = getAnimalsAllCapsObserver();

        /**
         * filter() is used to filter out the animal names starting with `b`
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(animalsObserver));

        /**
         * filter() is used to filter out the animal names starting with 'c'
         * map() is used to transform all the characters to UPPER case
         * */

        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalsObserverAllCaps));
    }

    private DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
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

    private DisposableObserver<String> getAnimalsAllCapsObserver() {
        return new DisposableObserver<String>() {


            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
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

    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(animalsArray);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.clear();
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
}