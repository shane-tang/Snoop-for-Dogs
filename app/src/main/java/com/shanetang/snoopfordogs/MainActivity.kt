package com.shanetang.snoopfordogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shanetang.snoopfordogs.search.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment, SearchFragment.newInstance())
            .commit()
    }
}
