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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

public class ChainingOfOperatorsActivity extends AppCompatActivity {

    /**
     * Sometimes the desired data stream can’t achieved using a single operator.
     * In that case you can use multiple operators together.When multiple operators are used,
     * the operators takes the result from the previous operator.
     */

    private final String TAG = ChainingOfOperatorsActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaining_of_operators);

        Observable.
                range(1, 100).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                }).
                subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "integer : " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete - all numbers are emitted");
                    }
                });
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
