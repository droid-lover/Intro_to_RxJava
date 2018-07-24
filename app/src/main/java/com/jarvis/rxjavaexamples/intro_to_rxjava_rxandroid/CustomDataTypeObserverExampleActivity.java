package com.jarvis.rxjavaexamples.intro_to_rxjava_rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jarvis.rxjavaexamples.R;
import com.jarvis.rxjavaexamples.model.Note;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Sachin
 */

public class CustomDataTypeObserverExampleActivity extends AppCompatActivity {


    private static final String TAG = CustomDataTypeObserverExampleActivity.class.getSimpleName();
//    private String[] animalsArray = {"Ant", "Ape", "Bat", "Bee", "Bear", "Butterfly", "Cat", "Crab", "Cod", "Dog", "Dove", "Fox", "Frog"};
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_data_type_observer_example);

        DisposableObserver<Note> disposableObserver = getNotesObserver();

        compositeDisposable.add(getNoteObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        note.setNote(note.getNote().toUpperCase());
                        return note;
                    }
                }).
                subscribeWith(disposableObserver));




    }


    private Observable<Note> getNoteObservable() {
        final List<Note> noteList = getNotesList();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {

                for (int i = 0; i < noteList.size(); i++) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(noteList.get(i));
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }


    private DisposableObserver<Note> getNotesObserver() {
        return new DisposableObserver<Note>() {
            @Override
            public void onNext(Note note) {
                Log.d(TAG, "onNext =" + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }


    private List<Note> getNotesList() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "buy tooth paste!"));
        notes.add(new Note(2, "call brother!"));
        notes.add(new Note(3, "watch IPL"));
        notes.add(new Note(4, "pay power bill!"));
        return notes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}