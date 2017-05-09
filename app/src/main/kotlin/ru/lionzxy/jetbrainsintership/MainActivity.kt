package ru.lionzxy.jetbrainsintership

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.lionzxy.jetbrainsintership.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(SearchFragment());
    }

    private fun showFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(SearchFragment.TAG) == null)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, SearchFragment.TAG).commit()
    }
}
