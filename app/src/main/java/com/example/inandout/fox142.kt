package com.example.inandout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import net.daum.android.map.MapView
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class fox142 : AppCompatActivity() {
    var mapView1: MapView? = null
    var mapViewContainer1: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fox142)

        mapView1 = MapView(this)

        mapViewContainer1 = findViewById(R.id.map_view)
        mapViewContainer1?.addView(mapView1)
        //마커 찍기 (세종대학교)
        val MARKER_POINT1 = MapPoint.mapPointWithGeoCoord(37.5518018, 127.0736343)

        // 마커 아이콘 추가하는 함수
        val marker1 = MapPOIItem()

        // 클릭 했을 때 나오는 호출 값
        marker1.itemName = "세종대 쓰레기통 여기 있음!"

        // 왜 있는지 잘 모르겠음
        marker1.tag = 0

        // 좌표를 입력받아 현 위치로 출력
        marker1.mapPoint = MARKER_POINT1

        // (클릭 전)기본으로 제공하는 BluePin 마커 모양의 색.
        marker1.markerType = MapPOIItem.MarkerType.BluePin

        // (클릭 후) 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker1.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        // 지도화면 위에 추가되는 아이콘을 추가하기 위한 호출(말풍선 모양)
//        mapView1?.addPOIItems(marker1)

    }
}

