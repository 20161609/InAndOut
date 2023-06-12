package com.android.everydaybyble_dingdong

object ApiExplorer {
    @Throws(java.io.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val urlBuilder: java.lang.StringBuilder =
            java.lang.StringBuilder("http://apis.data.go.kr/B550928/dissForecastInfoSvc/getDissForecastInfo") /*URL*/
        urlBuilder.append(
            "?" + java.net.URLEncoder.encode(
                "serviceKey",
                "UTF-8"
            ) + "=서비스키"
        ) /*Service Key*/
        urlBuilder.append(
            "&" + java.net.URLEncoder.encode(
                "numOfRows",
                "UTF-8"
            ) + "=" + java.net.URLEncoder.encode("10", "UTF-8")
        ) /*한 페이지 결과 수*/
        urlBuilder.append(
            "&" + java.net.URLEncoder.encode(
                "pageNo",
                "UTF-8"
            ) + "=" + java.net.URLEncoder.encode("1", "UTF-8")
        ) /*페이지 번호, 미입력 시 기본값 1*/
        urlBuilder.append(
            "&" + java.net.URLEncoder.encode(
                "type",
                "UTF-8"
            ) + "=" + java.net.URLEncoder.encode("xml", "UTF-8")
        ) /*응답결과의 출력 방식(xml, json), 미입력시 기본값:xml*/
        urlBuilder.append(
            "&" + java.net.URLEncoder.encode(
                "dissCd",
                "UTF-8"
            ) + "=" + java.net.URLEncoder.encode("1", "UTF-8")
        ) /*질병코드*/
        urlBuilder.append(
            "&" + java.net.URLEncoder.encode(
                "znCd",
                "UTF-8"
            ) + "=" + java.net.URLEncoder.encode("11", "UTF-8")
        ) /*시도별 지역코드, 지역코드 입력시 시군구별 데이터 응답/미입력시, 전국 및 시도별 데이터 응답*/
        val url: java.net.URL = java.net.URL(urlBuilder.toString())
        val conn: java.net.HttpURLConnection = url.openConnection() as java.net.HttpURLConnection
        conn.setRequestMethod("GET")
        conn.setRequestProperty("Content-type", "application/json")
        println("Response code: " + conn.getResponseCode())
        val rd: java.io.BufferedReader
        rd = if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            java.io.BufferedReader(java.io.InputStreamReader(conn.getInputStream()))
        } else {
            java.io.BufferedReader(java.io.InputStreamReader(conn.getErrorStream()))
        }
        val sb: java.lang.StringBuilder = java.lang.StringBuilder()
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            sb.append(line)
        }
        rd.close()
        conn.disconnect()
        println(sb.toString())
    }
}

public fun fox(){
    val urlBuilder: java.lang.StringBuilder =
        java.lang.StringBuilder("http://apis.data.go.kr/B550928/dissForecastInfoSvc/getDissForecastInfo") /*URL*/
    urlBuilder.append(
        "?" + java.net.URLEncoder.encode(
            "serviceKey",
            "UTF-8"
        ) + "=서비스키"
    ) /*Service Key*/
    urlBuilder.append(
        "&" + java.net.URLEncoder.encode(
            "numOfRows",
            "UTF-8"
        ) + "=" + java.net.URLEncoder.encode("10", "UTF-8")
    ) /*한 페이지 결과 수*/
    urlBuilder.append(
        "&" + java.net.URLEncoder.encode(
            "pageNo",
            "UTF-8"
        ) + "=" + java.net.URLEncoder.encode("1", "UTF-8")
    ) /*페이지 번호, 미입력 시 기본값 1*/
    urlBuilder.append(
        "&" + java.net.URLEncoder.encode(
            "type",
            "UTF-8"
        ) + "=" + java.net.URLEncoder.encode("xml", "UTF-8")
    ) /*응답결과의 출력 방식(xml, json), 미입력시 기본값:xml*/
    urlBuilder.append(
        "&" + java.net.URLEncoder.encode(
            "dissCd",
            "UTF-8"
        ) + "=" + java.net.URLEncoder.encode("1", "UTF-8")
    ) /*질병코드*/
    urlBuilder.append(
        "&" + java.net.URLEncoder.encode(
            "znCd",
            "UTF-8"
        ) + "=" + java.net.URLEncoder.encode("11", "UTF-8")
    ) /*시도별 지역코드, 지역코드 입력시 시군구별 데이터 응답/미입력시, 전국 및 시도별 데이터 응답*/
    val url: java.net.URL = java.net.URL(urlBuilder.toString())
    val conn: java.net.HttpURLConnection = url.openConnection() as java.net.HttpURLConnection
    conn.setRequestMethod("GET")
    conn.setRequestProperty("Content-type", "application/json")
    println("Response code: " + conn.getResponseCode())
    val rd: java.io.BufferedReader
    rd = if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        java.io.BufferedReader(java.io.InputStreamReader(conn.getInputStream()))
    } else {
        java.io.BufferedReader(java.io.InputStreamReader(conn.getErrorStream()))
    }
    val sb: java.lang.StringBuilder = java.lang.StringBuilder()
    var line: String?
    while (rd.readLine().also { line = it } != null) {
        sb.append(line)
    }
    rd.close()
    conn.disconnect()
    println(sb.toString())
}