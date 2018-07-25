package com.jarvis.rxjavaexamples.views.rx_operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class FilterOperatorActivity extends AppCompatActivity {

    private final String TAG = FilterOperatorActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DisposableObserver disposableObserver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_operator);

        disposableObserver1 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext Even Numbers: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(disposableObserver1);

        compositeDisposable.add(disposableObserver1);
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
            compositeDisposable.clear();//Stop receiving / dont send events when activity is destroyed.
        }
    }
}
