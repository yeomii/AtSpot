package org.bluehack.atspotproto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class InquiryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry_list)

        var listview = findViewById(R.id.inquiry_list) as ListView
        var adapter = InquiryAdapter()

        listview?.adapter = adapter

        adapter.addItem(null, "강남역에 사람 얼마나 있나요?", "500")
        adapter.addItem(null, "이태원 근처 맛집 추천해주세요", "1000")
    }
}
