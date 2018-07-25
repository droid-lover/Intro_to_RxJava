package com.jarvis.rxjavaexamples.views.rx_operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.jarvis.rxjavaexamples.R;
import com.jarvis.rxjavaexamples.model.Note;
import com.jarvis.rxjavaexamples.model.NoteUnique;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

public class DistinctCustomDataTypeOperatorActivity extends AppCompatActivity {

    private final String TAG = DistinctCustomDataTypeOperatorActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distinct_custom_data_type_operator);

        Observable<NoteUnique> notesObservable = getNotesObservable();

        notesObservable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                distinct().
                subscribe(new Observer<NoteUnique>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(NoteUnique noteUnique) {
                        Log.d(TAG, "onNext: " + noteUnique.getNote());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });


    }

    private Observable<NoteUnique> getNotesObservable() {
        final List<NoteUnique> noteList = getNotesList();

        return Observable.create(new ObservableOnSubscribe<NoteUnique>() {
            @Override
            public void subscribe(ObservableEmitter<NoteUnique> emitter) throws Exception {
                for (int i = 0; i < noteList.size(); i++) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(noteList.get(i));
                    }
                }
            }
        });
    }

    private List<NoteUnique> getNotesList() {
        List<NoteUnique> notes = new ArrayList<>();
        notes.add(new NoteUnique(1, "buy tooth paste!"));
        notes.add(new NoteUnique(1, "buy tooth paste!"));
        notes.add(new NoteUnique(1, "buy tooth paste!"));
        notes.add(new NoteUnique(1, "buy tooth paste!"));
        notes.add(new NoteUnique(2, "call brother!"));
        notes.add(new NoteUnique(2, "call brother!"));
        notes.add(new NoteUnique(2, "call brother!"));
        notes.add(new NoteUnique(2, "call brother!"));
        notes.add(new NoteUnique(3, "watch IPL"));
        notes.add(new NoteUnique(3, "watch IPL"));
        notes.add(new NoteUnique(3, "watch IPL"));
        notes.add(new NoteUnique(3, "watch IPL"));
        notes.add(new NoteUnique(4, "pay power bill!"));
        notes.add(new NoteUnique(4, "pay power bill!"));
        notes.add(new NoteUnique(4, "pay power bill!"));
        notes.add(new NoteUnique(4, "pay power bill!"));
        return notes;
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
