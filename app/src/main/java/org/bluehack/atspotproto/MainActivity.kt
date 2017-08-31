package org.bluehack.atspotproto

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickMainButton(view: View) {
        when (view.id) {
            R.id.to_inquiry_button -> startActivity(Intent(this, InquiryActivity::class.java))
            R.id.to_answer_button -> startActivity(Intent(this, AnswerActivity::class.java))
            R.id.to_nearby_button -> startActivity(Intent(this, NearbyActivity::class.java))
        }
    }
}
