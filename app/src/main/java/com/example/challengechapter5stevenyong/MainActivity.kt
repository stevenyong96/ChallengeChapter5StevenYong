package com.example.challengechapter5stevenyong

import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.challengechapter5stevenyong.model.Pertandingan
import com.example.challengechapter5stevenyong.model.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val pemain = Player()
    private val pemain2 = Player()
    private val pertandingan = Pertandingan()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Inisialisasi
        val namaUser = intent.getStringExtra("DATA_USER_NAME")
        val modePermainan = intent.getStringExtra("MODE_PERMAINAN")
        var txtPemain = findViewById<TextView>(R.id.text_view_pemain1)
        var txtPemain2 = findViewById<TextView>(R.id.text_view_computer)
        var txtStatus = findViewById<TextView>(R.id.txt_status)
        val playerBatu = findViewById<ImageView>(R.id.image_batu_player)
        val computerBatu = findViewById<ImageView>(R.id.image_batu_computer)
        val playerKertas = findViewById<ImageView>(R.id.image_kertas_player)
        val computerKertas = findViewById<ImageView>(R.id.image_kertas_computer)
        val playerGunting = findViewById<ImageView>(R.id.image_gunting_player)
        val computerGunting = findViewById<ImageView>(R.id.image_gunting_computer)
        val refresh = findViewById<ImageView>(R.id.image_refresh)
        var statusBattle = findViewById<TextView>(R.id.text_view_result)
        var btnExit = findViewById<ImageView>(R.id.btn_exit)
        val ivJudul = findViewById<ImageView>(R.id.iv_judul)

        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(ivJudul)

        if (namaUser != null) {
            pemain.playerName=namaUser
        }
        pemain.playerPick=0
        pertandingan.phase=0


        txtPemain.setText(namaUser)
        if(modePermainan == "CPU"){
            txtPemain2.setText("CPU")
            //INIT PLAYER 2
            pemain2.playerName=modePermainan
            pemain2.playerPick=0

            //0 = belum memilih , 1 = Batu , 2 = Gunting , 3 = Kertas

            playerBatu.setOnClickListener {
                pemain.playerPick = 1
                pemain.playerPickDesc="Batu"
                bermainBersamaCPU(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,txtStatus)
            }

            playerGunting.setOnClickListener {
                pemain.playerPick=2
                pemain.playerPickDesc="Gunting"
                bermainBersamaCPU(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,txtStatus)
            }

            playerKertas.setOnClickListener {
                pemain.playerPick=3
                pemain.playerPickDesc="Kertas"
                bermainBersamaCPU(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,txtStatus)
            }
        }
        else{
            txtPemain2.setText("Pemain 2")
            //INIT PLAYER 2
            if (modePermainan != null) {
                pemain2.playerName="Pemain 2"
            }else{
                pemain2.playerName="Pemain 2"
            }
            pemain2.playerPick=0
            computerBatu.isClickable = false
            computerGunting.isClickable = false
            computerKertas.isClickable = false

            playerBatu.setOnClickListener {
                pemain.playerPick = 1
                pemain.playerPickDesc="Batu"
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
                pertandingan.phase=1

            }

            playerGunting.setOnClickListener {
                pemain.playerPick=2
                pemain.playerPickDesc="Gunting"
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
                pertandingan.phase = 1

            }

            playerKertas.setOnClickListener {
                pemain.playerPick=3
                pemain.playerPickDesc="Kertas"
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
                pertandingan.phase = 1
            }

            computerBatu.setOnClickListener {
                pemain2.playerPick = 1
                pemain2.playerPickDesc="Batu"
                pertandingan.phase = 2
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
            }

            computerGunting.setOnClickListener {
                pemain2.playerPick=2
                pemain2.playerPickDesc="Gunting"
                pertandingan.phase = 2
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
            }

            computerKertas.setOnClickListener {
                pemain2.playerPick=3
                pemain2.playerPickDesc="Kertas"
                pertandingan.phase = 2
                bermainBersamaPemain(statusBattle,computerBatu, computerGunting , computerKertas , playerBatu , playerKertas, playerGunting,pemain,pemain2,pertandingan.phase,txtStatus)
            }

        }

        btnExit.setOnClickListener {
            finish()
        }

        refresh.setOnClickListener {
            refresher(statusBattle  , computerBatu  , computerGunting , computerKertas , playerBatu , playerKertas, playerGunting  , pemain  , pemain2 )
        }

    }

    private fun bermainBersamaCPU(statusBattle : TextView,computerBatu : ImageView , computerGunting: ImageView , computerKertas: ImageView , playerBatu: ImageView , playerKertas: ImageView, playerGunting : ImageView , pemain : Player , pemain2 : Player,txtStatus : TextView) {
        Log.d(MainActivity::class.java.simpleName,"Player Memilih : ${pemain.playerPickDesc} " + "(" + pemain.playerPick.toString() + ")")
        if(pemain.playerPick == 1){
            playerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
            playerGunting.setBackgroundColor(Color.WHITE)
            playerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
        }
        else if(pemain.playerPick == 2){
            playerBatu.setBackgroundColor(Color.WHITE)
            playerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
            playerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
        }
        else if(pemain.playerPick == 3){
            playerBatu.setBackgroundColor(Color.WHITE)
            playerGunting.setBackgroundColor(Color.WHITE)
            playerKertas.setBackgroundColor(Color.parseColor("#C3DAE9"))
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
        }
        computerAnimation(computerBatu,computerGunting,computerKertas)
        val randomValues = (1..3).shuffled().last()
        pemain2.playerPick = computerPick(randomValues,computerBatu,computerGunting,computerKertas)
        var tempPilihanComputer= ""
        if(pemain2.playerPick == 1) {
            tempPilihanComputer= "Batu"
            pemain2.playerPickDesc=tempPilihanComputer
        }
        else if(pemain2.playerPick == 2) {
            tempPilihanComputer= "Gunting"
            pemain2.playerPickDesc=tempPilihanComputer
        }
        else if(pemain2.playerPick == 3) {
            tempPilihanComputer= "Kertas"
            pemain2.playerPickDesc=tempPilihanComputer
        }
        txtStatus.setVisibility(View.VISIBLE);
        txtStatus.setText("CPU Memilih ${pemain2.playerPickDesc}")
        Log.d(MainActivity::class.java.simpleName, "Computer Memilih : "+ tempPilihanComputer + " (" + pemain2.playerPick.toString() + ")")
        var statusSuit = battleSuit(pemain.playerPick,pemain2.playerPick,statusBattle,pemain,pemain2)
        Log.d(MainActivity::class.java.simpleName, "Hasil Pertandingan : "+ statusSuit)
    }

    private fun bermainBersamaPemain(statusBattle : TextView,computerBatu : ImageView , computerGunting: ImageView , computerKertas: ImageView , playerBatu: ImageView , playerKertas: ImageView, playerGunting : ImageView , pemain : Player , pemain2 : Player, phase : Int ,txtStatus : TextView) {
        Log.d(MainActivity::class.java.simpleName,"Player Memilih : ${pemain.playerPickDesc} " + "(" + pemain.playerPick.toString() + ")")
        if(pemain.playerPick == 1){
            playerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
            playerGunting.setBackgroundColor(Color.WHITE)
            playerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
            Toast.makeText(this,"${pemain.playerName} Memilih ${pemain.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }
        else if(pemain.playerPick == 2){
            playerBatu.setBackgroundColor(Color.WHITE)
            playerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
            playerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
            Toast.makeText(this,"${pemain.playerName} Memilih ${pemain.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }
        else if(pemain.playerPick == 3){
            playerBatu.setBackgroundColor(Color.WHITE)
            playerGunting.setBackgroundColor(Color.WHITE)
            playerKertas.setBackgroundColor(Color.parseColor("#C3DAE9"))
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("${pemain.playerName} Memilih ${pemain.playerPickDesc}")
            Toast.makeText(this,"${pemain.playerName} Memilih ${pemain.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }

        computerBatu.isClickable = true
        computerGunting.isClickable = true
        computerKertas.isClickable = true

        Log.d(MainActivity::class.java.simpleName,"Player 2 Memilih : ${pemain2.playerPickDesc}")
        if(pemain2.playerPick == 1){
            computerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
            computerGunting.setBackgroundColor(Color.WHITE)
            computerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("Pemain 2 Memilih ${pemain2.playerPickDesc}")
            Toast.makeText(this,"Pemain 2 Memilih ${pemain2.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }
        else if(pemain2.playerPick == 2){
            computerBatu.setBackgroundColor(Color.WHITE)
            computerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
            computerKertas.setBackgroundColor(Color.WHITE)
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("Pemain 2 Memilih ${pemain2.playerPickDesc}")
            Toast.makeText(this,"Pemain 2 Memilih ${pemain2.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }
        else if(pemain2.playerPick == 3){
            computerBatu.setBackgroundColor(Color.WHITE)
            computerGunting.setBackgroundColor(Color.WHITE)
            computerKertas.setBackgroundColor(Color.parseColor("#C3DAE9"))
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText("Pemain 2 Memilih ${pemain2.playerPickDesc}")
            Toast.makeText(this,"Pemain 2 Memilih ${pemain2.playerPickDesc}",Toast.LENGTH_SHORT).show()
        }


        Log.d(MainActivity::class.java.simpleName, "Pemain 2 Memilih : ${pemain2.playerPickDesc}")

        if(phase == 2){
            var statusSuit = battleSuit(pemain.playerPick,pemain2.playerPick,statusBattle,pemain,pemain2)
            Log.d(MainActivity::class.java.simpleName, "Hasil Pertandingan : "+ statusSuit)
            refresherPemain(statusBattle , computerBatu  , computerGunting , computerKertas , playerBatu , playerKertas, playerGunting  , pemain , pemain2 , pertandingan)
        }
    }


    private fun computerAnimation(computerBatu : ImageView , computerGunting: ImageView , computerKertas: ImageView) {
        computerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.WHITE)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000L)
        }
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
        computerKertas.setBackgroundColor(Color.WHITE)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000L)
        }
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.parseColor("#C3DAE9"))
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000L)
        }
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
        computerKertas.setBackgroundColor(Color.WHITE)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000L)
        }
        computerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.WHITE)
        computerBatu.setBackgroundColor(Color.WHITE)

    }

    private fun refresher(statusBattle : TextView , computerBatu : ImageView , computerGunting: ImageView , computerKertas: ImageView , playerBatu: ImageView , playerKertas: ImageView, playerGunting : ImageView , pemain1 : Player , pemain2 : Player) {
        Log.d(MainActivity::class.java.simpleName, "Refresh dilakukan")
        statusBattle.setText("VS")
        statusBattle.setBackgroundColor(Color.parseColor("#FFFFFF"))
        statusBattle.setTextColor(Color.parseColor("#d80024"))
        statusBattle.setRotation(-15f)
        statusBattle.setTextSize(48f)
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.WHITE)
        playerBatu.setBackgroundColor(Color.WHITE)
        playerGunting.setBackgroundColor(Color.WHITE)
        playerKertas.setBackgroundColor(Color.WHITE)
        pemain1.playerPick=0
        pemain2.playerPick=0
    }

    private fun refresherPemain(statusBattle : TextView , computerBatu : ImageView , computerGunting: ImageView , computerKertas: ImageView , playerBatu: ImageView , playerKertas: ImageView, playerGunting : ImageView , pemain1 : Player , pemain2 : Player, pertandingan: Pertandingan) {
        Log.d(MainActivity::class.java.simpleName, "Refresh dilakukan")
        statusBattle.setText("VS")
        statusBattle.setBackgroundColor(Color.parseColor("#FFFFFF"))
        statusBattle.setTextColor(Color.parseColor("#d80024"))
        statusBattle.setRotation(-15f)
        statusBattle.setTextSize(48f)
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.WHITE)
        playerBatu.setBackgroundColor(Color.WHITE)
        playerGunting.setBackgroundColor(Color.WHITE)
        playerKertas.setBackgroundColor(Color.WHITE)
        pemain1.playerPick=0
        pemain2.playerPick=0
        pertandingan.phase=0
    }


    private fun computerPick(parameterRandom : Int ,paramComputerBatu : ImageView, paramComputerGunting: ImageView, paramComputerKertas : ImageView): Int {
        var pilihanComputer = 0
        if(parameterRandom == 1) {
            pilihanComputer = 1
            paramComputerBatu.setBackgroundColor(Color.parseColor("#C3DAE9"))
            paramComputerGunting.setBackgroundColor(Color.WHITE)
            paramComputerKertas.setBackgroundColor(Color.WHITE)
        }
        else if(parameterRandom == 2) {
            pilihanComputer = 2
            paramComputerBatu.setBackgroundColor(Color.WHITE)
            paramComputerGunting.setBackgroundColor(Color.parseColor("#C3DAE9"))
            paramComputerKertas.setBackgroundColor(Color.WHITE)
        }
        else if(parameterRandom == 3) {
            pilihanComputer = 3
            paramComputerBatu.setBackgroundColor(Color.WHITE)
            paramComputerGunting.setBackgroundColor(Color.WHITE)
            paramComputerKertas.setBackgroundColor(Color.parseColor("#C3DAE9"))
        }
        else{
            pilihanComputer = 0
            paramComputerBatu.setBackgroundColor(Color.WHITE)
            paramComputerGunting.setBackgroundColor(Color.WHITE)
            paramComputerKertas.setBackgroundColor(Color.WHITE)
        }
        return pilihanComputer
    }

    private fun battleSuit(playerPick : Int , computerPick : Int , statusBattle: TextView , pemain1: Player, pemain2:Player): String {
        var status= ""
        if(playerPick == 1) {
            if(computerPick == 1) {
                status="DRAW"
                resultBattle(0,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 2) {
                status= "Pemain 1 MENANG!"
                resultBattle(1,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 3){
                status= "Pemain 2 MENANG!"
                resultBattle(2,status,statusBattle,pemain1,pemain2)
            }
            else {
                status= "Something Went Wrong , Please Try Again"
            }
        }
        else if(playerPick == 2) {
            if(computerPick == 1) {
                status="Pemain 2 MENANG!"
                resultBattle(2,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 2) {
                status= "DRAW"
                resultBattle(0,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 3){
                status= "Pemain 1 MENANG!"
                resultBattle(1,status,statusBattle,pemain1,pemain2)
            }
            else {
                status= "Something Went Wrong , Please Try Again"
            }
        }
        else if(playerPick == 3) {
            if(computerPick == 1) {
                status="Pemain 1 MENANG!"
                resultBattle(1,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 2) {
                status= "Pemain 2 MENANG!"
                resultBattle(2,status,statusBattle,pemain1,pemain2)
            }
            else if(computerPick == 3){
                status= "DRAW"
                resultBattle(0,status,statusBattle,pemain1,pemain2)
            }
            else {
                status= "Something Went Wrong , Please Try Again"
            }
        }
        else{
            status= "Something Went Wrong , Please Try Again"
        }

        return status
    }

    private fun resultBattle(status : Int , statusDesc : String , statusBattle: TextView, pemain1 : Player , pemain2 : Player) {
        println("Result Battle")
        if(status == 0){
//            statusBattle.setText(statusDesc)
//            statusBattle.setBackgroundColor(Color.parseColor("#92D050"))
//            statusBattle.setTextColor(Color.parseColor("#FFFFFF"))
//            statusBattle.setRotation(-15f)
//            statusBattle.setTextSize(24f)

            val dialogBuilder = AlertDialog.Builder(this)
            val viewCustom = LayoutInflater.from(this).inflate(R.layout.dialog_winner,null,false)
            dialogBuilder.setView(viewCustom)
            val dialog = dialogBuilder.create()
            val btnBackToMenu = viewCustom.findViewById<Button>(R.id.btn_back_to_menu)
            val btnMainLagi = viewCustom.findViewById<Button>(R.id.btn_main_lagi)
            var txtResult = viewCustom.findViewById<TextView>(R.id.text_view_7)
            txtResult.setText("SERI!")

            btnMainLagi.setOnClickListener {
                refreshed()
                dialog.dismiss()
            }

            btnBackToMenu.setOnClickListener {
                dialog.dismiss()
                finish()
            }

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
        else if(status == 1){
//            statusBattle.setText(statusDesc)
//            statusBattle.setBackgroundColor(Color.parseColor("#92D050"))
//            statusBattle.setTextColor(Color.parseColor("#FFFFFF"))
//            statusBattle.setRotation(-15f)
//            statusBattle.setTextSize(24f)

            val dialogBuilder = AlertDialog.Builder(this)
            val viewCustom = LayoutInflater.from(this).inflate(R.layout.dialog_winner,null,false)
            dialogBuilder.setView(viewCustom)
            val dialog = dialogBuilder.create()
            val btnBackToMenu = viewCustom.findViewById<Button>(R.id.btn_back_to_menu)
            val btnMainLagi = viewCustom.findViewById<Button>(R.id.btn_main_lagi)
            var txtResult = viewCustom.findViewById<TextView>(R.id.text_view_7)
            txtResult.setText("${pemain1.playerName} MENANG!")

            btnMainLagi.setOnClickListener {
                refreshed()
                dialog.dismiss()
            }

            btnBackToMenu.setOnClickListener {
                dialog.dismiss()
                finish()
            }

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }
        else if(status == 2) {
//            statusBattle.setText(statusDesc)
//            statusBattle.setBackgroundColor(Color.parseColor("#92D050"))
//            statusBattle.setTextColor(Color.parseColor("#FFFFFF"))
//            statusBattle.setRotation(-15f)
//            statusBattle.setTextSize(24f)

            val dialogBuilder = AlertDialog.Builder(this)
            val viewCustom = LayoutInflater.from(this).inflate(R.layout.dialog_winner,null,false)
            dialogBuilder.setView(viewCustom)
            val dialog = dialogBuilder.create()
            val btnBackToMenu = viewCustom.findViewById<Button>(R.id.btn_back_to_menu)
            val btnMainLagi = viewCustom.findViewById<Button>(R.id.btn_main_lagi)
            var txtResult = viewCustom.findViewById<TextView>(R.id.text_view_7)
            txtResult.setText("${pemain2.playerName} MENANG!")


            btnMainLagi.setOnClickListener {
                refreshed()
                dialog.dismiss()
            }

            btnBackToMenu.setOnClickListener {
                dialog.dismiss()
                finish()
            }

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }



    }

    private fun refreshed() {
        val playerBatu = findViewById<ImageView>(R.id.image_batu_player)
        val computerBatu = findViewById<ImageView>(R.id.image_batu_computer)
        val playerKertas = findViewById<ImageView>(R.id.image_kertas_player)
        val computerKertas = findViewById<ImageView>(R.id.image_kertas_computer)
        val playerGunting = findViewById<ImageView>(R.id.image_gunting_player)
        val computerGunting = findViewById<ImageView>(R.id.image_gunting_computer)
        val refresh = findViewById<ImageView>(R.id.image_refresh)
        var statusBattle = findViewById<TextView>(R.id.text_view_result)
        var txtStatus = findViewById<TextView>(R.id.txt_status)

        Log.d(MainActivity::class.java.simpleName, "Refresh dilakukan")
        txtStatus.setVisibility(View.GONE);
        statusBattle.setText("VS")
        statusBattle.setBackgroundColor(Color.parseColor("#FFFFFF"))
        statusBattle.setTextColor(Color.parseColor("#d80024"))
        statusBattle.setRotation(-15f)
        statusBattle.setTextSize(48f)
        computerBatu.setBackgroundColor(Color.WHITE)
        computerGunting.setBackgroundColor(Color.WHITE)
        computerKertas.setBackgroundColor(Color.WHITE)
        playerBatu.setBackgroundColor(Color.WHITE)
        playerGunting.setBackgroundColor(Color.WHITE)
        playerKertas.setBackgroundColor(Color.WHITE)
        computerBatu.isClickable= false
        computerGunting.isClickable= false
        computerKertas.isClickable= false
    }
}