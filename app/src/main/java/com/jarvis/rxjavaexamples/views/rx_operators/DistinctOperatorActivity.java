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

public class DistinctOperatorActivity extends AppCompatActivity {

    private final String TAG = DistinctOperatorActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distinct_custom_data_type_operator);

        Observable.just(1, 1, 1, 3, 3, 3, 2, 4, 2, 4).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                distinct().
                subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe" + d);
                        disposable = d;
                    }
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:All items are emitted");
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
