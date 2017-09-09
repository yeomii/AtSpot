package org.bluehack.atspotproto

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Yeomi on 2017-09-08.
 */
class InquiryAdapter : BaseAdapter() {
    private var list : ArrayList<InquiryListViewItem> = ArrayList<InquiryListViewItem>()

    fun InquiryAdapter() {

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var cv = convertView
        if (cv == null) {
            var inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.inquiry_listview_item, parent, false)
        }
        cv = cv as View

        var iconView = cv.findViewById(R.id.inquiry_list_icon) as ImageView?
        var questionView = cv.findViewById(R.id.inquiry_list_question_textview) as TextView?
        var rewardView = cv.findViewById(R.id.inquiry_list_reward_textview) as TextView?

        var lv = list[position]
        if (lv.icon_drawable != null)
            iconView?.setImageDrawable(lv.icon_drawable)
        questionView?.setText(lv.question_string)
        rewardView?.setText(lv.reward_string)
        return cv
    }

    override fun getItemId(position: Int): Long {
        return position as Long
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    fun addItem(icon : Drawable?, question_string : String, reward_string : String) {
        var item = InquiryListViewItem()
        item.icon_drawable = icon
        item.question_string = question_string
        item.reward_string = reward_string
        list.add(item)
    }
}
