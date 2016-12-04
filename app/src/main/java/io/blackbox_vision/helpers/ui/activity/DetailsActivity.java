package io.blackbox_vision.helpers.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.ui.fragment.DetailsFragment;


public final class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_details, new DetailsFragment())
                .commit();
    }
}
