package io.blackbox_vision.helpers.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import io.blackbox_vision.helpers.R
import io.blackbox_vision.helpers.ui.fragment.DetailsFragment

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_details, DetailsFragment())
                .commit()
    }
}
