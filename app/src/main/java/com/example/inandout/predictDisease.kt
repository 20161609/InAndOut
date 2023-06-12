package com.example.inandout

import android.util.Log
import org.json.JSONObject

class CallAPI(private val address1 : String,
              private val address2 : String,
              private var codeDisease : String,
              private var numRow : String
) : Thread()
{

    private fun encodeBuild(mark : String, s : String, inputCode : String): String {
        return mark + java.net.URLEncoder.encode(s,"UTF-8") +
                "=" + inputCode
    }
    var result : JSONObject = JSONObject()

    override fun run(){

        /* 1. url 빌더 구성 */
        val urlBuilder: java.lang.StringBuilder =
            java.lang.StringBuilder("http://apis.data.go.kr/B550928/dissForecastInfoSvc/getDissForecastInfo") /*URL*/

        //서비스키
        urlBuilder.append(encodeBuild("?","serviceKey",
            "eRGciEoSyuRc7slQ%2BRHnHcXBh%2FcxGyfcBSFDBMURxF%2BPD63Z9ASLQjdKjFZbw9bhsStG3PgjmWwp3bAdZNzgSQ%3D%3D"))

        //localAddress1에 대한 localAddress2의 멤버수
        urlBuilder.append(encodeBuild("&","numOfRows",java.net.URLEncoder.encode(numRow, "UTF-8")))

        //페이지 번호
        urlBuilder.append(encodeBuild("&","pageNo",java.net.URLEncoder.encode("1", "UTF-8")))

        //출력 유형
        urlBuilder.append(encodeBuild("&","type",java.net.URLEncoder.encode("json", "UTF-8")))

        //질병 코드
        urlBuilder.append(encodeBuild("&","dissCd",java.net.URLEncoder.encode(codeDisease, "UTF-8")))

        //지역 코드 : localAddress1
        urlBuilder.append(encodeBuild("&","znCd",java.net.URLEncoder.encode(address1, "UTF-8")))

        /* 2. 넷상 url 연결*/
        val url: java.net.URL = java.net.URL(urlBuilder.toString())
        Log.e("Url",url.toString())
        val conn: java.net.HttpURLConnection = url.openConnection() as java.net.HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-type", "text/json")

        /* 3. 콜을 통해 가져온 json 파일 리딩 */
        val rd: java.io.BufferedReader = if (conn.responseCode in 200..300) {
            java.io.BufferedReader(java.io.InputStreamReader(conn.inputStream))
        } else {
            java.io.BufferedReader(java.io.InputStreamReader(conn.errorStream))
        }
        val sb : java.lang.StringBuilder = java.lang.StringBuilder()
        var line: String?
        while (rd.readLine().also { line = it } != null)
            sb.append(line.plus("\n"))

        /* 4. json 파일 파싱 후, */
        println(sb)
        val aao = JSONObject(sb.toString())
        val jsonArr = JSONObject(sb.toString()).getJSONObject("response").getJSONObject("body").getJSONArray("items")
        for(i in 0 until jsonArr.length()){
            if(JSONObject(jsonArr[i].toString()).getString("lowrnkZnCd") == address2){
                result=JSONObject(jsonArr[i].toString())
                break
            }
        }
        rd.close()
        conn.disconnect()
    }
}
