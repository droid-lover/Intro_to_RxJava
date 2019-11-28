package com.jarvis.rxjavaexamples.views.rx_operators;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jarvis.rxjavaexamples.R;

import static com.jarvis.rxjavaexamples.R.id.rx_operators_list_view;

/**
 * RxJava Operators allows us to manipulate the data emitted by Observables.
 * Basically, operators tells Observable, how to modify the data and when to emit the data.
 * Using the operators we can modify, merge, filter or group the data streams.
 * <p>
 * <p>
 * ≈The operators like create, just, fromArray, range creates an Observable.≈
 * ≈Some operators such as debounce, filter, skip, last are used to filter the data emitted by an Observable.≈
 * ≈operators like buffer, map, flatMap, switchMap, compose creates an Observable by transform the data emitted by another Observable≈
 */
public class RxOperatorsActivity extends AppCompatActivity {


    private ListView rxOperatorsListView;
    private String[] rxOperations = {"FromArray Operator", "RangeArray Operator", "Chaining Operator", "Multiple Observer with Chaining Operator", "Just Operator", "Repeat Operator",
            "Buffer Operator", "Buffer Operator2", "Debounce Operator", "Filter Operator", "Skip Operator", "SkipLast Operator", "Take Operator", "TakeLast Operator", "Distinct Operator"
            , "DistinctCustom Operator", "Map Operator", "FlatMap Operator"};

    private ArrayAdapter<String> rxOperatorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_operators);

        initializeViews();
    }

    private void initializeViews() {
        rxOperatorsListView = findViewById(rx_operators_list_view);

        settingRxOperationsList();
    }

    private void settingRxOperationsList() {

        rxOperatorsAdapter = new ArrayAdapter<String>(RxOperatorsActivity.this, android.R.layout.simple_list_item_1, rxOperations);
        rxOperatorsListView.setAdapter(rxOperatorsAdapter);

        rxOperatorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(RxOperatorsActivity.this, FromArrayActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(RxOperatorsActivity.this, RangeArrayActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(RxOperatorsActivity.this, ChainingOfOperatorsActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(RxOperatorsActivity.this, MultipleObserversWithChainingOfOperators.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(RxOperatorsActivity.this, JustOperatorActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(RxOperatorsActivity.this, RepeatOperatorActivity.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(RxOperatorsActivity.this, BufferOperatorActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(RxOperatorsActivity.this, BufferOperatorExample2Activity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(RxOperatorsActivity.this, DebounceOperatorActivity.class);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9 = new Intent(RxOperatorsActivity.this, FilterOperatorActivity.class);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10 = new Intent(RxOperatorsActivity.this, SkipOperatorActivity.class);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11 = new Intent(RxOperatorsActivity.this, SkipLastOperatorActivity.class);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12 = new Intent(RxOperatorsActivity.this, TakeOperatorActivity.class);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13 = new Intent(RxOperatorsActivity.this, TakeLastOperatorActivity.class);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(RxOperatorsActivity.this, DistinctOperatorActivity.class);
                        startActivity(intent14);
                        break;
                    case 15:
                        Intent intent15 = new Intent(RxOperatorsActivity.this, DistinctCustomDataTypeOperatorActivity.class);
                        startActivity(intent15);
                        break;
                    case 16:
                        Intent intent16 = new Intent(RxOperatorsActivity.this, MapOperatorActivity.class);
                        startActivity(intent16);
                        break;
                    case 17:
                        Intent intent17 = new Intent(RxOperatorsActivity.this, FlatMapOperatorActivity.class);
                        startActivity(intent17);
                        break;

                }
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


}
