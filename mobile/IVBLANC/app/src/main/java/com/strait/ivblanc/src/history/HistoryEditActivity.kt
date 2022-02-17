package com.strait.ivblanc.src.history

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.strait.ivblanc.adapter.HistoryDetailRecyclerViewAdapter
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.History
import com.strait.ivblanc.databinding.ActivityHistoryEditBinding
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener

import android.app.Dialog
import android.widget.Button
import android.widget.TextView
import com.strait.ivblanc.util.GpsTracker
import android.location.LocationManager

import android.content.Intent

import android.content.DialogInterface

import android.widget.Toast

import android.location.Geocoder

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.location.Address
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.EditText

import androidx.core.content.ContextCompat
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.*
import android.view.*

import android.view.ContextMenu.ContextMenuInfo
import androidx.activity.viewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.HistoryViewModel
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.WeatherUtil
import java.net.MalformedURLException
import java.text.SimpleDateFormat
import android.os.Looper




private const val TAG = "HistoryEdit"
class HistoryEditActivity : BaseActivity<ActivityHistoryEditBinding>(
    ActivityHistoryEditBinding::inflate) {

    private var gpsTracker: GpsTracker? = null
    private val historyViewModel: HistoryViewModel by viewModels()

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var history: History
    lateinit var location: String
    var styleId: Int = 0

    lateinit var weatherUtil: WeatherUtil

    lateinit var locationDialog: Dialog
    lateinit var temperatureDialog: Dialog

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var address: String

    lateinit var tvWeather: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<History>("history")?.let {
            history = it
        } ?: finish()
        location = intent.getStringExtra("location").toString()
        styleId = intent.getIntExtra("styleId", 0)

        weatherUtil = WeatherUtil()
        gpsTracker = GpsTracker(this@HistoryEditActivity)

        tvWeather = binding.textViewHistoryEditSelectWeather
        registerForContextMenu(tvWeather)

        setClickListeners()
        setHistoryEditInfo()
    }

    private fun setClickListeners() {
        binding.imageViewHistoryEditClose.setOnClickListener {
            // TODO: 정말 나갈건지 다이얼로그 띄워서 물어보기
            finish()
        }
        binding.textViewHistoryEditSave.setOnClickListener {
            history.date = binding.textViewHistoryEditSelectDate.text.toString()
            history.subject = binding.editTextHistoryEditSubject.text.toString()
            history.text = binding.editTextHistoryEditText.text.toString()

            if(binding.textViewHistoryEditSelectWeather.text.toString() == "-"){
                history.weather = "맑음"
            } else {
                history.weather = binding.textViewHistoryEditSelectWeather.text.toString()
            }

            if(history.historyId != 0){
                historyViewModel.updateHistory(history.historyId, history.location, history.field, history.date, history.weather,
                    history.temperature_low, history.temperature_high, history.text, history.subject, history.styleUrl)
                Toast.makeText(this, "수정되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                if(history.date == "날짜 선택 > ") {
                    Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                } else if(styleId == 0) {
                    Toast.makeText(this, "스타일을 선택해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    historyViewModel.addHistory(history.location, history.field, history.date, history.weather,
                        history.temperature_low, history.temperature_high, history.text, history.subject, styleId, emptyList() )
                    Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        binding.imageViewHistoryEditStyle.setOnClickListener {
            val styleSelectFragment = HistoryStyleSelectFragment(object: HistoryStyleSelectFragment.StyleSelectedListener {
                override fun getResult(style: Style) {
                    if(style == null) return
                    history.styleUrl = style.url
                    Glide.with(this@HistoryEditActivity).load(style.url).into(binding.imageViewHistoryEditStyle)
                }
            })
            styleSelectFragment.show(supportFragmentManager, "style")
        }

        binding.textViewHistoryEditSelectDate.setOnClickListener {
            val date: List<String>
            if(history.historyId != 0){
                date = history.date.split("-")
            } else {
                date = getToday("yyyy-MM-dd").split("-")
            }

            val dialog = DatePickerDialog(this, R.style.MySpinnerDatePickerStyle, datePickerListener, date[0].toInt(), date[1].toInt()-1, date[2].toInt())
            dialog.show()
        }

        binding.textViewHistoryEditSelectLocation.setOnClickListener {
            locationDialog = Dialog(this@HistoryEditActivity) // Dialog 초기화
            locationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀 제거
            locationDialog.setContentView(R.layout.dialog_select_location) // xml 레이아웃 파일과 연결

            showLocationDialog()
        }

        binding.textViewHistoryEditTemperature.setOnClickListener {
            temperatureDialog = Dialog(this@HistoryEditActivity)
            temperatureDialog.requestWindowFeature((Window.FEATURE_NO_TITLE))
            temperatureDialog.setContentView(R.layout.dialog_select_temperature)

            showTemperatureDialog()
        }
    }

    private fun setHistoryEditInfo() {
        if (history.styleUrl != null) {
            Glide.with(this).load(history.styleUrl).into(binding.imageViewHistoryEditStyle)
        }

        if(history.historyId == 0){
            binding.textViewHistoryEditTitle.text = "Make History"
        } else {
            binding.textViewHistoryEditSelectDate.text = history.date
            binding.textViewHistoryEditSelectLocation.text = location
            binding.textViewHistoryEditTemperature.text = history.temperature_high.toString() + "°C/" + history.temperature_low.toString() + "°C"
        }

        binding.editTextHistoryEditSubject.setText(history.subject)
        binding.editTextHistoryEditText.setText(history.text)

        latitude = history.field
        longitude = history.location
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(com.strait.ivblanc.R.menu.select_weather_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sunny -> {
                tvWeather.text = "맑음"
                return true
            }
            R.id.rainy -> {
                tvWeather.text = "비"
                return true
            }
            R.id.snowy -> {
                tvWeather.text = "눈"
                return true
            }
            R.id.cloudy -> {
                tvWeather.text = "흐림"
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private val datePickerListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = "$year-${monthOfYear+1}-$dayOfMonth"
            binding.textViewHistoryEditSelectDate.text = newDate
        }

    private fun showLocationDialog(){
        locationDialog.show() // 다이얼로그 띄우기

        val noBtn: TextView = locationDialog.findViewById(R.id.textView_btn_cancel)
        noBtn.setOnClickListener(View.OnClickListener {
            locationDialog.dismiss()
        })

        val saveBtn: TextView = locationDialog.findViewById(R.id.textView_btn_save)
        saveBtn.setOnClickListener(View.OnClickListener {
            history.field = longitude
            history.location = latitude
            binding.textViewHistoryEditSelectLocation.text = address
            locationDialog.dismiss()
        })

        val localBtn : Button = locationDialog.findViewById(R.id.button_location_get)
        val etLocation = locationDialog.findViewById<EditText>(R.id.editText_location)
        localBtn.setOnClickListener {
            if (!checkLocationServicesStatus()) {
                showDialogForLocationServiceSetting();
            }else {
                checkRunTimePermission();
            }

            latitude = gpsTracker!!.getLatitude()
            longitude = gpsTracker!!.getLongitude()
            address = getCurrentAddress(latitude, longitude)

            etLocation.setText(address)
        }

        val searchBtn : Button = locationDialog.findViewById(R.id.button_location_search)
        searchBtn.setOnClickListener {
            var list: List<Address?>? = null
            val geocoder = Geocoder(this, Locale.KOREA)

            val str : String = locationDialog.findViewById<EditText>(R.id.editText_location).text.toString()
            Log.d("EDIT_HISTORY", "str = $str")
            if(str == null || str.isEmpty()){
                Toast.makeText(this, "검색할 위치정보를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            try {
                list = geocoder.getFromLocationName(str, 10)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생")
            }

            if (list != null) {
                if (list.isEmpty()) {
                    Toast.makeText(this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_SHORT).show()
                } else {
                    var cut = list[0].toString().split("\"")
                    etLocation.setText(cut[1])

                    latitude = list[0]!!.latitude
                    longitude = list[0]!!.longitude
                    address = cut[1]
                }
            }

        }

    }

    private fun getToday(pattern: String): String {
        val date = Date(System.currentTimeMillis());
        val sdf = SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    private fun showTemperatureDialog(){
        temperatureDialog.show() // 다이얼로그 띄우기

        var etTempHigh = temperatureDialog.findViewById<EditText>(R.id.editText_temp_high)
        var etTempLow = temperatureDialog.findViewById<EditText>(R.id.editText_temp_low)

        val today = getToday("yyyyMMdd")

        val noBtn: TextView = temperatureDialog.findViewById(R.id.textView_temp_btn_cancel)
        noBtn.setOnClickListener(View.OnClickListener {
            temperatureDialog.dismiss()
        })

        val saveBtn: TextView = temperatureDialog.findViewById(R.id.textView_temp_btn_save)
        saveBtn.setOnClickListener(View.OnClickListener {
            history.temperature_high= etTempHigh.text.toString().toDouble().toInt()
            history.temperature_low = etTempLow.text.toString().toDouble().toInt()
            binding.textViewHistoryEditTemperature.text = etTempHigh.text.toString() + "°C/" + etTempLow.text.toString() + "°C"

            temperatureDialog.dismiss()
        })

        val findLocalBtn: TextView = temperatureDialog.findViewById(R.id.textView_temp_menu1)
        findLocalBtn.setOnClickListener {

            Thread {
                val response = weatherUtil.lookUpWeather(gpsTracker!!.getLatitude().toInt().toString(), gpsTracker!!.getLongitude().toInt().toString(), today)
                if(response == "Error") {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(Runnable {
                        Toast.makeText(this, "현재 위치로 기온 검색에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }, 0)
                } else {
                    var str = response.split("/")
                    Log.d("HISTORYEDIT", "latitude = ${gpsTracker!!.getLatitude()}, longitude = ${gpsTracker!!.getLongitude()}" )
                    etTempLow.setText(str[0])
                    etTempHigh.setText(str[1])
                }
            }.start()

        }

        val findSettingBtn: TextView = temperatureDialog.findViewById(R.id.textView_temp_menu2)
        findSettingBtn.setOnClickListener {
            if(history.location == 0.0 || history.field == 0.0){
                Toast.makeText(this, "위치 정보를 먼저 설정해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Thread {
                    val response = weatherUtil.lookUpWeather(history.location.toInt().toString(), history.field.toInt().toString(), today)
                    if(response == "Error") {
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed(Runnable {
                            Toast.makeText(this, "설정된 위치로 기온 검색에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }, 0)
                    } else {
                        var str = response.split("/")
                        Log.d(
                            "HISTORYEDIT",
                            "latitude = ${gpsTracker!!.getLatitude()}, longitude = ${gpsTracker!!.getLongitude()}"
                        )
                        etTempLow.setText(str[0])
                        etTempHigh.setText(str[1])
                    }
                }.start()
            }
        }

        val writeOwnBtn: TextView = temperatureDialog.findViewById(R.id.textView_temp_menu3)
        writeOwnBtn.setOnClickListener {
            etTempHigh.isEnabled = true
            etTempLow.isEnabled = true
        }

    }

    private fun checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@HistoryEditActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this@HistoryEditActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
        ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@HistoryEditActivity,
                    REQUIRED_PERMISSIONS[0]
                )
            ) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this@HistoryEditActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@HistoryEditActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@HistoryEditActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }


    private fun getCurrentAddress(latitude: Double, longitude: Double): String {

        val geocoder = Geocoder(this, Locale.KOREA)
        val addresses: List<Address>? = try {
            geocoder.getFromLocation(
                latitude,
                longitude,
                7
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        if (addresses == null || addresses.size == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        }
        val address: Address = addresses[0]
        return address.getAddressLine(0).toString().toString() + "\n"
    }


    // GPS 활성화를 위한 메소드들
    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@HistoryEditActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """
            앱을 사용하기 위해서는 위치 서비스가 필요합니다.
            위치 설정을 수정하시겠습니까?
            """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        builder.create().show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

}