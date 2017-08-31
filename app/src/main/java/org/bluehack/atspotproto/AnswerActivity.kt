package org.bluehack.atspotproto

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import java.io.File


class AnswerActivity : AppCompatActivity() {

    val TAKE_PICTURE_REQUEST = 1

    val INTENT_EXTRA_FILE_NAME = "FILE_NAME"
    val RESULT_OK = 0
    val RESULT_FAIL = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

    }

    fun onClickCameraButton(view: View) {
        startActivityForResult(Intent(this, CameraActivity::class.java), TAKE_PICTURE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                val view = findViewById(R.id.answer_image_view) as ImageView
                if (view === null)
                    return

                val imgFile = File(data!!.getStringExtra(INTENT_EXTRA_FILE_NAME))
                if (imgFile.exists()) {
                    val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
                    view.setImageBitmap(myBitmap)
                }
            } else {

            }
        }
    }
}
