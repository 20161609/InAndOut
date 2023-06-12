package com.example.inandout

import AsthmaFragment
import ColdFragment
import DermatitisFragment
import EyeInfectionFragment
import FoodPoisoningFragment
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import org.json.JSONObject

var diseaseIndex : Int = 0
val healthInfo: String = """
Health Map : 질병알림 <출처 : 국민건강보험공단>

<감기>
위험도 : 관심
예상진료수 : 421
지침사항 : 환기를 자주 시켜 깨끗한 환경을 유지하고, 외출 후에는 반드시 손을 씻는 등 평소 손 씻기를 생활화합니다.

<눈병>
위험도 : 관심
예상진료수 : 30
지침사항 : 비누를 사용하여 흐르는 수돗물에 손을 자주 씻고, 손으로 얼굴, 특히 눈 주위를 만지지 않도록 합니다.

<식중독>
위험도 : 위험
예상진료수 : 3832
지침사항 : 식중독 발생가능성이 매우 높으므로 식중독예방에 각별한 경계가 요망됩니다. 설사, 구토 등 식중독 의심 증상이 있으면 의료기관을 방문하여 의사 지시를 따릅니다. 식중독 의심환자는 식품 조리 참여에 즉시 중단하여야 합니다.

<천식>
위험도 : 관심
예상진료수 : 22
지침사항 : 기관지 자극 요인인 흡연, 찬바람, 찬음식 등과 급격한 온도변화는 피합니다.

<피부염>
위험도 : 관심
예상진료수 : 27
지침사항 : 지나친 목욕, 과다한 비누 사용, 습도가 낮은 환경에 노출되지 않도록 유의하며 세탁 후 옷에 세제가 남지 않도록 잘 헹굽니다.
""".trimIndent()

fun fox12(activity: Activity, context: Context, fragment: FragmentActivity){

    val jsonString1: String =
        activity.application.assets.open("localAddress1.json").bufferedReader().use { it.readText() }
    val jsonString2: String =
        activity.application.assets.open("localAddress2.json").bufferedReader().use { it.readText() }

    var items1 = arrayOf<String>()
    for (key in JSONObject(jsonString1).keys())
        items1 = items1.plus(key.toString())

    val changeLocalView = LayoutInflater.from(context).inflate(R.layout.change_local,null)
    val inLocal1: AutoCompleteTextView = changeLocalView.findViewById(R.id.inLocal1)
    val inLocal2: AutoCompleteTextView = changeLocalView.findViewById(R.id.inLocal2)
    val adapter1 = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items1)
    Log.e("Begin","RRR")
    for (i in 0 until adapter1.count) {
        println(adapter1.getItem(i).toString())
    }
    Log.e("End","RRR")

    tempLocalAddress1 = ""
    tempLocalAddress2 = ""
    tempCodeAddress1 = ""
    tempCodeAddress2 = ""

    inLocal1.setAdapter(adapter1)
    inLocal1.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not used in this example
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Notify that the text has changed programmatically
            tempLocalAddress1=s.toString()
            tempCodeAddress1=JSONObject(jsonString1).getString(tempLocalAddress1)
            tempLocalAddress2=""

            Log.e(tempLocalAddress1, tempCodeAddress1)

            var items2 = arrayOf<String>()
            for (key in JSONObject(jsonString2).getJSONObject(tempCodeAddress1).keys())
                items2 = items2.plus(key.toString())
            val adapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items2)
            inLocal2.setAdapter(adapter2)
            inLocal2.clearListSelection()
            inLocal2.showDropDown()
        }

        override fun afterTextChanged(s: Editable?) {
            // Not used in this example
        }
    })

    inLocal2.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not used in this example
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Notify that the text has changed programmatically
            tempLocalAddress2=s.toString()
            tempCodeAddress2=JSONObject(jsonString2).getJSONObject(tempCodeAddress1).getString(tempLocalAddress2)
        }

        override fun afterTextChanged(s: Editable?) {
            // Not used in this example
        }
    })

    val builder = AlertDialog.Builder(context).setView(changeLocalView)
        .setPositiveButton("확인") { dialogInterface, i ->
            // TODO: Handle the OK button
            if (tempLocalAddress1!="" && tempLocalAddress2!=""){
                localAddress1=tempLocalAddress1
                localAddress2=tempLocalAddress2
                codeAddress1=tempCodeAddress1
                codeAddress2=tempCodeAddress2
                val tv_location : TextView = activity.findViewById(R.id.tv_location)
                tv_location.text="$localAddress1 $localAddress2"
                getLocalInfo()
                fox14(activity, context, fragment)
                fox15(activity, context)
            }
        }
        .setNegativeButton("취소") { dialogInterface, i ->
            // User cancelled the dialog
        }

    builder.show()
}

fun fox15(activity: Activity, context: Context){
    Log.e("BEGIN","BEGIN")
    for(txt in arrLocalInfo){
        println(txt.cnt)
    }
    Log.e("END","END")
    val arrLayout : Array<Int> = arrayOf(
        R.layout.activity_cold_fragment, R.layout.activity_eye_infection_fragment,
        R.layout.activity_food_poisoning_fragment,R.layout.activity_asthma_fragment,
        R.layout.activity_dermatitis_fragment
    )
    val arrRisk : Array<Int> = arrayOf(R.id.risk1,R.id.risk2,R.id.risk3,R.id.risk4,R.id.risk5)
    val arrCnt : Array<Int> = arrayOf(R.id.cnt1,R.id.cnt2,R.id.cnt3,R.id.cnt4,R.id.cnt5)
    val arrIns : Array<Int> = arrayOf(R.id.instruction1,R.id.instruction2,R.id.instruction3,R.id.instruction4,R.id.instruction5)
    val arrLight : Array<Int> = arrayOf(R.id.light1,R.id.light2,R.id.light3,R.id.light4,R.id.light5)

    for(i in 0..4/*arrLayout.size-1*/){
        Log.e(i.toString(),"ID")
        val view = LayoutInflater.from(context).inflate(arrLayout[i],null)
        val risk : TextView = view.findViewById(arrRisk[i])
        val cnt : TextView = view.findViewById(arrCnt[i])
        val instruction : TextView = view.findViewById(arrIns[i])
        val light : AppCompatImageView = view.findViewById(arrLight[i])
        val riskIndex : Int = arrLocalInfo[i].risk.toInt()-1
        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )

        risk.text=arrayOf("관심","주의","경고","위험")[riskIndex]
        light.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        risk.text=arrayOf("관심","주의","경고","위험")[riskIndex]
        cnt.text= "예상 진료 건 수 : ".plus(arrLocalInfo[i].cnt)
        instruction.text= arrLocalInfo[i].instruction
    }
}

class DiseasePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 5
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ColdFragment()
            1 -> EyeInfectionFragment()
            2 -> FoodPoisoningFragment()
            3 -> AsthmaFragment()
            4 -> DermatitisFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}

fun fox14(activity: Activity, context: Context, fragment: FragmentActivity){
    val disease : TextView = activity.findViewById(R.id.disease)
    disease.text = arrLocalInfo[0].disease

    val viewPager = activity.findViewById<ViewPager2>(R.id.viewPager)
    viewPager.offscreenPageLimit = 2 // 옆의 페이지를 미리 로드해 놓을 개수 설정

    val recyclerView = viewPager.getChildAt(0) as RecyclerView
    recyclerView.apply {
        val padding = resources.getDimensionPixelOffset(R.dimen.viewpager_padding)
        setPadding(padding, 0, padding, 0)
        clipToPadding = false
    }

    viewPager.adapter = DiseasePagerAdapter(fragment)
    viewPager.currentItem = pageNum

    // 페이지 전환 이벤트를 감지하는 콜백을 등록합니다.
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            // 새로운 페이지가 선택될 때 호출됩니다. 여기서 position 값을 사용하여 필요한 작업을 수행할 수 있습니다.
            diseaseIndex = position
            pageNum = position
            disease.text = arrLocalInfo[diseaseIndex].disease
        }
    })

}

class Test1 : AppCompatActivity() {
    private lateinit var diseasePagerAdapter: DiseasePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)

        diseaseIndex = 0
        val disease : TextView = findViewById(R.id.disease)
        disease.text = arrLocalInfo[diseaseIndex].disease

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.offscreenPageLimit = 2 // 옆의 페이지를 미리 로드해 놓을 개수 설정

        val recyclerView = viewPager.getChildAt(0) as RecyclerView
        recyclerView.apply {
            val padding = resources.getDimensionPixelOffset(R.dimen.viewpager_padding)
            setPadding(padding, 0, padding, 0)
            clipToPadding = false
        }

        diseasePagerAdapter = DiseasePagerAdapter(this)
        viewPager.adapter = diseasePagerAdapter
        viewPager.currentItem = pageNum

        // 페이지 전환 이벤트를 감지하는 콜백을 등록합니다.
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 새로운 페이지가 선택될 때 호출됩니다. 여기서 position 값을 사용하여 필요한 작업을 수행할 수 있습니다.
                diseaseIndex = position
                pageNum = position
                disease.text = arrLocalInfo[diseaseIndex].disease
            }
        })

        val modifyLocal : LinearLayout= findViewById(R.id.modifyLocal)
        modifyLocal.setOnClickListener(){
            fox12(this, this, this)
            Log.e("fox","Clicked")
        }

        val searchHospital : LinearLayout= findViewById(R.id.searchHospital)
        searchHospital.setOnClickListener(){
            val intent : Intent = Intent(this, search_hospital::class.java)
            startActivity(intent)
        }

        val shareMessage : LinearLayout = findViewById(R.id.share_message)
        shareMessage.setOnClickListener(){
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, healthInfo)
                type = "text/plain"
            }
//seefse
            startActivity(Intent.createChooser(shareIntent, "Share Health Info"))
        }
        Log.e("Begin","FF")
        for(a in arrLocalInfo){
            println(a.cnt)
            println(a.disease)
            println(arrayOf("관심","주의","경고","위험")[a.risk.toInt()-1])
            println(a.instruction)
        }
        Log.e("End","FF")

    }
}