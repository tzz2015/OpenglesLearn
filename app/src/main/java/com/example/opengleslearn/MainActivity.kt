package com.example.opengleslearn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.activity.FirstActivity
import com.example.opengleslearn.activity.SecondActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        Log.e(TAG, item.title.toString())
        when (itemId) {
            R.id.action_shader0 -> startActivity(Intent(this, FirstActivity::class.java))
            R.id.action_shader1 -> startActivity(Intent(this, SecondActivity::class.java))
        }
        return true
    }


}
