package com.jarvis.rxjavaexamples.views.rx_operators;

import androidx.appcompat.app.AppCompatActivity;
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
 * To better understand FlatMap, consider a scenario where we have a network call to fetch Users with name and gender.
 * Then we have another network that gives us address of each user.
 * <p>
 * ≈ Now the requirement is to create an Observable that emits Users with name, gender and address properties.
 * To achieve this, we need to get the users first, then make separate network call for each user to fetch his address.
 * This can be done easily using FlatMap operator. ≈
 */
public class FlatMapOperatorActivity extends AppCompatActivity {

    private final String TAG = FlatMapOperatorActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map_operator);

        getUserObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                flatMap(new Function<User, Observable<User>>() {
                    @Override
                    public Observable<User> apply(User user) throws Exception {
                        // getting each user address by making another network call
                        return getAddressObservable(user);
                    }
                }).
                subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getName() + " " + user.getGender() + " " + user.getAddress());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private Observable<User> getAddressObservable(final User user) {
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                if (!emitter.isDisposed()) {
                    user.setAddress("Address of" + user.getName().toLowerCase());
                    emitter.onNext(user);
                }
            }
        });
    }

    private Observable<User> getUserObservable() {
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
