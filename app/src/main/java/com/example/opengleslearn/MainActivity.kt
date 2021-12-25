package com.example.opengleslearn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.opengleslearn.activity.*

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
            R.id.action_shader2 -> startActivity(Intent(this, ThreeActivity::class.java))
            R.id.action_shader3 -> startActivity(Intent(this, FourActivity::class.java))
            R.id.action_shader4 -> startActivity(Intent(this, FiveActivity::class.java))
            R.id.action_shader5 -> startActivity(Intent(this, SixActivity::class.java))
            R.id.action_shader6 -> startActivity(Intent(this, SevenActivity::class.java))
            R.id.action_shader7 -> startActivity(Intent(this, ParticleActivity::class.java))
            R.id.action_shader8 -> startActivity(Intent(this, PictureLoadActivity::class.java))
            R.id.action_shader9 -> startActivity(Intent(this, AnimationActivity::class.java))
        }
        return true
    }


}
