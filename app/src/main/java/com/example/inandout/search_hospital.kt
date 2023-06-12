package com.example.inandout

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

val arrText : Array<String> = arrayOf(
    "연세이비인후과의원 (5.0km)", "열린성모이비인후과의원 (4.1km)",
    "랩이비인후과의원 (3.0km)", "푸른이비인후과 (4.1km)",
    "닥터윤이비인후과 (2.7km)", "연세봄이비인후과의원 (2.4km)",
    "김성전김시찬이비인후과의원 (1.6km)","센스이비인후과의원 (1.5km)",
    "푸른성모이비인후과의원 (2.9km)", "연세소리이비인후과의원 (1.9km)",
    "코이젠이비인후과의원 (2.2km)", "공덕연세이비인후과의원 (2.1km)"
)

var fuck = 0
class search_hospital : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_hospital)

        fun dpToPx(dp: Float): Int {
            val px= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics())
            return px.toInt()
        }

        val scrollView: ScrollView = findViewById(R.id.scrollView)
        val linearLayout: LinearLayout = scrollView.getChildAt(0) as LinearLayout
        val joo : ImageView = findViewById(R.id.joo)

        var arrS : Array<TextView> = arrayOf()
        for(s in arrText){
            val newTextView = TextView(this)
            newTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {topMargin = dpToPx(15f) }
            newTextView.textSize=18f
            newTextView.text = s
            arrS=arrS.plus(newTextView)
            linearLayout.addView(newTextView)

            if (s == arrText.last()) break
            val newView = View(this)
            newView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                resources.getDimensionPixelSize(R.dimen.view_height)
            ).apply {
                topMargin = dpToPx(15f)
            }
            newTextView.isClickable=true
            newTextView.setOnClickListener(){
                scrollView.removeView(linearLayout)
                scrollView.addView(createLinearLayout(this))
                joo.setImageResource(R.drawable.joob) // 이미지 리소스 변경
            }
            newView.setPadding(dpToPx(10f), 0,dpToPx(10f), 0)
            newView.setBackgroundColor(Color.parseColor("#66bdbdbd"))
            linearLayout.addView(newView)
        }
    }
}

fun createLinearLayout(context: Context): LinearLayout {
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        topMargin = 10 // px
    }

    val fontFamily = ResourcesCompat.getFont(context, R.font.arita4_0_m)


    val titleView = TextView(context).apply {
        layoutParams = params
        text = "연세봄이비인후과\n"
        textSize = 18f
        typeface = Typeface.create(fontFamily, Typeface.BOLD)
    }

    val addressView = TextView(context).apply {
        layoutParams = params
        text = "주소 : 서울 마포구 양화로 152 대화빌딩 4층 (우)04050 지번동교동 162-13\n"
        textSize = 16f
        typeface = fontFamily
    }

    val hoursView = TextView(context).apply {
        layoutParams = params
        text = "진료시간\n월~금 09:00 ~ 19:00\n토 09:00 ~ 15:00 (점심시간없이 진료)\n월~금 휴게시간 13:00 ~ 14:00"
        textSize = 16f
        typeface = fontFamily
    }

    val linkView = TextView(context).apply {
        layoutParams = params
        text = "https://place.map.kakao.com/13514241"
        textSize = 16f
        typeface = fontFamily
        Linkify.addLinks(this, Linkify.WEB_URLS)
//        autoLinkMask = android.text.util.Linkify.WEB_URLS
    }

    return LinearLayout(context).apply {
        layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL
        setPadding(16,16,16,16)
        addView(titleView)
        addView(addressView)
        addView(hoursView)
        addView(linkView)
    }
}

