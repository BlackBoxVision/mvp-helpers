package io.blackbox_vision.helpers.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.ui.fragment.ListFragment;


public final class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_list, new ListFragment())
                .commit();
    }
}
