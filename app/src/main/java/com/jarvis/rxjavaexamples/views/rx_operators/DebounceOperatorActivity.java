package com.jarvis.rxjavaexamples.views.rx_operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.jarvis.rxjavaexamples.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
/**
 * Created by Sachin
 */

/**
 * Debounce operators emits items only when a specified timespan is passed.
 * ≈This operator is very useful when the Observable is rapidly emitting items but we are only interested in receiving them in timely manner.≈
 */
public class DebounceOperatorActivity extends AppCompatActivity {

    private final String TAG = DebounceOperatorActivity.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder unbinder;

    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.text_search_string)
    TextView textSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);
        unbinder = ButterKnife.bind(this);

        disposable.add(RxTextView.textChangeEvents(inputSearch).
                skipInitialValue().
                debounce(500, TimeUnit.MILLISECONDS).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(searchQuery()));

    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, textViewTextChangeEvent.text().toString());
                textSearchString.setText("Query: " + textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.toString());
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
        unbinder.unbind();
        if (disposable != null) {
            disposable.dispose();//Stop receiving / dont send events when activity is destroyed.
        }
    }

}
