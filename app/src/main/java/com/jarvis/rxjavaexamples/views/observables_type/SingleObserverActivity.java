package com.jarvis.rxjavaexamples.views.observables_type;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jarvis.rxjavaexamples.R;
import com.jarvis.rxjavaexamples.model.Note;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

/**
 * Single always emits only one value or throws an error.
 * The same job can be done using Observable too with a single emission but Single always makes sure there is an emission.
 * A use case of Single would be making a network call to get response as the response will be fetched at once.
 * Notice here, the SingleObserver doesn’t have onNext() to emit the data,
 * instead the data will be received in onSuccess(Note note) method.
 */
public class SingleObserverActivity extends AppCompatActivity {

    private final String TAG = SingleObserverActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_observer);

        Single<Note> singleObservable = getNotesObservable();
        SingleObserver<Note> singleObserver = getSingleObserver();

        singleObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(singleObserver);
    }

    private SingleObserver<Note> getSingleObserver() {
        return new SingleObserver<Note>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onSuccess(Note note) {
                Log.d(TAG, "onSuccess: " + note.getNote());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e);
            }
        };
    }

    private Single<Note> getNotesObservable() {
        return Single.create(new SingleOnSubscribe<Note>() {
            @Override
            public void subscribe(SingleEmitter<Note> emitter) throws Exception {
                Note note = new Note(1, "Buy Juices");
//                Note note1 = new Note(11, "Buy Juices1");
                emitter.onSuccess(note);
//                emitter.onSuccess(note1);    ---- Not Possible witll only emitt once.
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
