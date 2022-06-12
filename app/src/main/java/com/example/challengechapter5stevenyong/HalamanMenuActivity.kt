package com.example.challengechapter5stevenyong

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess


class HalamanMenuActivity: AppCompatActivity() {

    lateinit var etMenuVsPemain : TextView
    lateinit var etMenuVsCom : TextView
    lateinit var ivVersusCom: ImageView
    lateinit var ivVersusPemain: ImageView

    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val namaUser = intent.getStringExtra("DATA_USER_NAME")
        val layoutMenu = findViewById<ConstraintLayout>(R.id.layout_menu)
        etMenuVsPemain = findViewById<TextView>(R.id.et_menu_vs_pemain)
        ivVersusPemain = findViewById<ImageView>(R.id.imageView3)
        etMenuVsCom = findViewById<TextView>(R.id.et_menu_vs_com)
        ivVersusCom = findViewById<ImageView>(R.id.imageView4)


        etMenuVsPemain.setText("${namaUser} vs Pemain")
        etMenuVsCom.setText("${namaUser} vs CPU")

        var snackbar: Snackbar = Snackbar.make(layoutMenu, "Selamat Datang ${namaUser}", Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.parseColor("#FF7900"))
        snackbar.setAction("Tutup", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()

        etMenuVsCom.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.putExtra("DATA_USER_NAME", namaUser)
            intentToMain.putExtra("MODE_PERMAINAN", "CPU")
            startActivity(intentToMain)
        }

        ivVersusCom.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.putExtra("DATA_USER_NAME", namaUser)
            intentToMain.putExtra("MODE_PERMAINAN", "CPU")
            startActivity(intentToMain)
        }

        etMenuVsPemain.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.putExtra("DATA_USER_NAME", namaUser)
            intentToMain.putExtra("MODE_PERMAINAN", "PLAYER")
            startActivity(intentToMain)
        }

        ivVersusPemain.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.putExtra("DATA_USER_NAME", namaUser)
            intentToMain.putExtra("MODE_PERMAINAN", "PLAYER")
            startActivity(intentToMain)
        }



    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            this@HalamanMenuActivity.finish()
            moveTaskToBack(true);
            finishAffinity()
            exitProcess(-1)
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }

}