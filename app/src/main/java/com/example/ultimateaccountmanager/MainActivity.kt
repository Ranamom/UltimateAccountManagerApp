package com.example.ultimateaccountmanager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide
            .with(this)
            .load("https://outfits.ferobraglobal.com/animoutfit.php?id=130&addons=3&head=123&body=12&legs=23&feet=31&mount=92&direction=3")
            .into(player_image)
    }


}
