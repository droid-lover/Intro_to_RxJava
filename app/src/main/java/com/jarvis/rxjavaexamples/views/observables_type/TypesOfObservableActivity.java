package com.jarvis.rxjavaexamples.views.observables_type;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jarvis.rxjavaexamples.R;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Sachin
 */

/**
 * We all know that Observable emits data / event and an Observer can receive it by subscribing on to it.
 * There are multiple types of Observables, Observers and there are number of ways to create an Observable.
 * <p>
 * ≈Observables differs from one another in the way they produce data and the number of emissions each
 * Observable makes≈
 */
public class TypesOfObservableActivity extends AppCompatActivity {

    private final String TAG = TypesOfObservableActivity.class.getSimpleName();
    private String[] typesOfObservables = {"Observable", "Single", "Maybe", "Flowable", "Completable"};
    private String[] respectiveObservers = {"Observer", "Single Observer", "Maybe Observer", "Observer", "CompletableObserver"};
    private String[] respectiveEmissions = {"Multiple or None", "One", "One or None", "Multiple or None", "None"};

    @BindView(R.id.listview_types_of_observables)
    ListView typesOfObservableListView;

    ArrayAdapter<String> typeOfObservableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_observable);
        ButterKnife.bind(this);
        typeOfObservableAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typesOfObservables);
        typesOfObservableListView.setAdapter(typeOfObservableAdapter);

        typesOfObservableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals(typesOfObservables[0])) {
                    Intent intent = new Intent(TypesOfObservableActivity.this, ObservableAndObserverExampleActivity.class);
                    startActivity(intent);
                } else if (parent.getItemAtPosition(position).equals(typesOfObservables[1])) {
                    Intent intent = new Intent(TypesOfObservableActivity.this, SingleObserverActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
