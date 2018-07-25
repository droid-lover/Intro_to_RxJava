package com.jarvis.rxjavaexamples.views.rx_operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jarvis.rxjavaexamples.R;
import com.jarvis.rxjavaexamples.model.user.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Map Operator
 * ≈ Map trasforms the items emitted by an Observable by applying a function to each item. ≈
 * ≈ map(x => 10*x)
 * <p>
 * The Map operator applies a function of your choosing to each item emitted by the source Observable,
 * and returns an Observable that emits the results of these function applications.
 * <p>
 * "Map modifies each item emitted by a source Observable and emits the modified item."
 * FlatMap, SwitchMap and ConcatMap also applies a function on each emitted item but instead of
 * returning the modified item, it returns the Observable itself which can emit data again.
 */

public class MapOperatorActivity extends AppCompatActivity {

    private final String TAG = MapOperatorActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_operator);


        //Map Operator transforms each item emitted by an Observable and emit the modified item.

        //Let’s say we have an Observable that makes a network call (assume the network call is made)
        // and emits the User objects with name and gender.
        // But in our requirement we need an email address to be present for each user,
        // which is missing in the network response. Then we can alter each User object by applying Map() operation.
        //------->
        //getUsersObservable() : assume this method is making a network call and fetching user objects.
        // This returns an Observable that emits User objects with name and gender.
        //map() operator applies Function<User, User> on each User object and adds email address and returns the modified User object.

        initializeViews();
    }

    private void initializeViews() {

        getUserObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        //Here we will get the original user object emitted
                        //we can modify this object by applying any operation/function on it.
                        user.setEmail(String.format("%s@jarvis.com", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                }).
                subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getName() + " " + user.getGender() +" "+ user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete : All items are emitted");
                    }
                });
    }

    private Observable<User> getUserObservable() {
//        setUpUserPojo();
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                for (User user : setUpUserPojo()) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(user);
                    }
                }

                if (emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private ArrayList<User> setUpUserPojo() {

        ArrayList<User> userList = new ArrayList<>();
        userList.clear();

        User user;
        String gender, name = "";

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                gender = "Male";
                name = "Name" + i;
            } else {
                gender = "FeMale";
                name = "Name" + i;
            }
            user = new User(name, gender);
            userList.add(user);
        }
        return userList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
