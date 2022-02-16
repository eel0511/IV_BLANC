package com.strait.ivblanc.src.main

import android.os.Bundle
import android.view.View
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng

import com.naver.maps.map.CameraPosition

import com.naver.maps.map.NaverMap

import com.naver.maps.map.overlay.OverlayImage


import com.naver.maps.map.MapView

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels

import com.naver.maps.map.overlay.Overlay

import com.naver.maps.map.OnMapReadyCallback

import com.naver.maps.map.overlay.Marker
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.History

import com.strait.ivblanc.data.model.viewmodel.HistoryViewModel






class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map), OnMapReadyCallback {
    private var isInit = false
    private var mapView: MapView? = null
    private var naverMap: NaverMap? = null
    private var resourceID = R.drawable.ic_baseline_place_24
    val historyViewModel: HistoryViewModel by viewModels()

    //마커 변수 선언 및 초기화
    private val marker1: Marker = Marker()
    private var marker = emptyArray<Marker>()
    private lateinit var historyList: List<History>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.getAllHistory()
        historyViewModel.historyListLiveData.observe(requireActivity()){
            marker = Array(it.size){Marker()}
            historyList = it
        }

        binding.buttonRefresh.setOnClickListener {
            if(historyList != null) {
                for (i: Int in historyList.indices) {
                    marker[i] = Marker()
                    setMarker(marker[i], historyList[i].location, historyList[i].field, resourceID, 0)
                    marker[i].onClickListener = Overlay.OnClickListener {
                        Toast.makeText(requireActivity(), "마커$i 클릭", Toast.LENGTH_SHORT).show()
                        false
                    }
                }
            }
        }

        //네이버 지도
        mapView = binding.mapViewMapF
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    private fun setMarker(marker: Marker, lat: Double, lng: Double, resourceID: Int, zIndex: Int) {
        //원근감 표시
        marker.setIconPerspectiveEnabled(true)
        //아이콘 지정
        marker.setIcon(OverlayImage.fromResource(resourceID))
        //마커의 투명도
        marker.setAlpha(0.8f)
        //마커 위치
        marker.setPosition(LatLng(lat, lng))
        //마커 우선순위
        marker.setZIndex(zIndex)
        //마커 표시
        marker.map = naverMap
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        //배경 지도 선택
        naverMap.mapType = NaverMap.MapType.Navi

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)

        //위치 및 각도 조정
        val cameraPosition = CameraPosition(
            LatLng(33.38, 126.55),  // 위치 지정
            9.0,  // 줌 레벨
            0.0,  // 기울임 각도
            0.0 // 방향
        )
        naverMap.cameraPosition = cameraPosition
    }

    public override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    public override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    public override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    companion object {
        private val naverMap: NaverMap? = null
    }
}
