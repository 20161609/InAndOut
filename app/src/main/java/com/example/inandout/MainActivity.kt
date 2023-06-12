package com.example.inandout

import RetrofitBuilder
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Base64
import android.util.Log
import android.widget.TextView
import com.kakao.sdk.common.util.Utility
//import extractReview
import fuga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Signature
import java.util.Base64.getEncoder


var jsonObject1 : JSONObject = JSONObject()
var jsonObject2 : JSONObject = JSONObject()

var localAddress1 : String = ""
var localAddress2 : String = ""
var codeAddress1 : String="00"
var codeAddress2 : String="00000"

var tempLocalAddress1 : String = ""
var tempLocalAddress2 : String = ""
var tempCodeAddress1 : String = ""
var tempCodeAddress2 : String = ""



var infoObject : JSONObject = JSONObject()

var mainActivity : MainActivity = MainActivity()
var pageNum : Int = 1
class LocalInfo(){
    var dissCd : String = "nothing"
    var disease : String = "nothing"
    var cnt : String = "nothing"
    var risk : String = "nothing"
    var instruction : String = "nothing"
}

var arrLocalInfo : Array<LocalInfo> = arrayOf()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_InAndOut)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivity = this

        /* json file 초기화 */
        this.initJson()

        /* gps 기반 주소 시군구 단위까지 저장 */
        var add = getCurrentAddress(this)
        Log.e("add",add.toString())
        add = "서울시 마포구"
        getParsingAddress(add)

        /* 국민건강보험공단에 API 콜, gps 기반 예측정보 저장*/
        getLocalInfo()

        /* 지역 예측정보 저장 및 출력 */
        printLocalInfo()

        val intent : Intent = Intent(this, Test1::class.java)
        startActivity(intent)
    }



    private fun initJson(){
        val jsonString1: String =
            application.assets.open("localAddress1.json").bufferedReader().use { it.readText() }
        val jsonString2: String =
            application.assets.open("localAddress2.json").bufferedReader().use { it.readText() }
        jsonObject1 = JSONObject(jsonString1)
        jsonObject2 = JSONObject(jsonString2)
    }
}

fun getParsingAddress(address : String?){
    if(address!=null) {
        val arrAddress = address.split(' ')
        localAddress1 = "서울특별시"//arrAddress[1]//시, 도 단위 주소 text
        localAddress2 = "마포구"//arrAddress[2]//시, 군, 구 단위 주소 text
//        if ("시군구".contains(arrAddress[3].last())) localAddress2 = localAddress2.plus(" " + arrAddress[3])

        /*시, 도 단위 주소 code*/
        codeAddress1 = jsonObject1.getString(localAddress1)
        Log.e(codeAddress1.toString(),"FOXMAN")
        /*시, 군, 구 단위 주소 code*/
        codeAddress2 = jsonObject2.getJSONObject(codeAddress1).getString(localAddress2)
        Log.e(codeAddress1, codeAddress2)
        Log.e(localAddress1, localAddress2)
    }
}



fun getLocalInfo(){
    /* 1. 현위치 시군구 단위로 화면에 출력 */
    val local1 : TextView = mainActivity.findViewById(R.id.local1)
    local1.text= localAddress1.plus(' ').plus(localAddress2)

    /* 2. api콜을 통해 위치 기반 질병예측 정보 저장 */
    val numRow : Int = jsonObject2.getJSONObject(codeAddress1).length()
    arrLocalInfo = arrayOf()

    val cntList : Array<Int> = arrayOf(2782, 284, 18867, 214, 354)
    val riskList : Array<Int> = arrayOf(1,1,1,1,1)
    val instructionList : Array<String> = arrayOf(
        "환기를 자주 시켜 깨끗한 환경을 유지하고, 외출 후에는 반드시 손을 씻는 등 평소 손 씻기를 생활화합니다.",
        "비누를 사용하여 흐르는 수돗물에 손을 자주 씻고, 손으로 얼굴, 특히 눈 주위를 만지지 않도록 합니다",
        "식중독 발생가능성은 낮으나 식중독예방에 지속적인 관심이 요망됩니다. 화장실 사용 후, 귀가 후, 조리 전에 손 씻기를 생활화 합시다.",
        "기관지 자극 요인인 흡연, 찬바람, 찬음식 등과 급격한 온도변화는 피합니다.",
        "지나친 목욕, 과다한 비누 사용, 습도가 낮은 환경에 노출되지 않도록 유의하며 세탁 후 옷에 세제가 남지 않도록 잘 헹굽니다."
    )

    for(i in 1..5){
        //i번째 질병관련 예측정보를 api콜을 통해 json형식으로 저장

        if(true) {
            val callApi = CallAPI(codeAddress1, codeAddress2, i.toString(), numRow.toString())
            callApi.start()
            callApi.join()
            infoObject = callApi.result

            arrLocalInfo= arrLocalInfo.plus(LocalInfo().apply {
                dissCd=i.toString()
                disease=arrayOf("감기", "눈병", "식중독", "천식", "피부염")[i-1]
                cnt=infoObject.getString("cnt")
                risk=infoObject.getString("risk")
                instruction=infoObject.getString("dissRiskXpln")
            })
            continue
        }


        //받아온 json을 통해 localInfo class에 저장
        arrLocalInfo= arrLocalInfo.plus(LocalInfo().apply {
            dissCd=i.toString()
            disease=arrayOf("감기", "눈병", "식중독", "천식", "피부염")[i-1]
            cnt= cntList[i-1].toString()//infoObject.getString("cnt")
            risk=riskList[i-1].toString()//infoObject.getString("risk")
            instruction=instructionList[i-1]//infoObject.getString("dissRiskXpln")
        })
    }
}

fun printLocalInfo(){
    /* 1. 위치기반 질병예측 정보 출력 */
    for(i in 1..5){
        val viewId = arrayOf(R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5)[i-1]
        val viewInfo : TextView = mainActivity.findViewById(viewId)

        val cnt = arrLocalInfo[i-1].cnt
        val risk = arrLocalInfo[i-1].risk
        viewInfo.text= "진료수 : ".plus(cnt).plus(", 위험도 : ").plus(risk)
    }

    /* 2. 지침사항 출력 */
    val viewInstruction : TextView = mainActivity.findViewById(R.id.t6)
    viewInstruction.text= arrLocalInfo[0].instruction

}