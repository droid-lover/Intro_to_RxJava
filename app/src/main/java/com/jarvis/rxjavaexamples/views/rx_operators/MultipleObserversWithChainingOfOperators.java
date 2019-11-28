package com.jarvis.rxjavaexamples.views.rx_operators;

import androidx.appcompat.app.AppCompatActivity;
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

public class MultipleObserversWithChainingOfOperators extends AppCompatActivity {

    private final String TAG = MultipleObserversWithChainingOfOperators.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_observers_with_chaining_of_operators);

        Observable<Integer> numbersObservable = getNumbersObservable();

        DisposableObserver<String> evenNumberObserver = getEvenNumberObserver();
        DisposableObserver<String> oddNumberObserver = getOddNumberObserver();

        compositeDisposable.add(getNumbersObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                }).
                map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "Number is:-" + integer;
                    }
                }).
                subscribeWith(evenNumberObserver));

        compositeDisposable.add(numbersObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 != 0;
                    }
                }).
                map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "Number is:-" + integer;
                    }
                })
                .subscribeWith(oddNumberObserver));

    }

    private DisposableObserver<String> getEvenNumberObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, " getEvenNumberObserver " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " getEvenNumberObserver onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " getEvenNumberObserver onComplete");
            }
        };
    }


    private DisposableObserver<String> getOddNumberObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, " getOddNumberObserver " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "getOddNumberObserver onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " getOddNumberObserver onComplete");
            }
        };
    }


    private Observable<Integer> getNumbersObservable() {
        return Observable.range(1, 100);
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
        if (compositeDisposable != null) {
            compositeDisposable.dispose();//Stop receiving / dont send events when activity is destroyed.
        }
    }
}
