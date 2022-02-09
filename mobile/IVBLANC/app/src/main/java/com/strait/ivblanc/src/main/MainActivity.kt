package com.strait.ivblanc.src.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.databinding.ActivityMainBinding
import com.strait.ivblanc.src.friend.FriendNoti
import com.strait.ivblanc.src.photoSelect.AlbumFragment
import com.strait.ivblanc.src.photoSelect.CameraFragment
import com.strait.ivblanc.src.photoSelect.PhotoSelectActivity
import com.strait.ivblanc.src.process.ProcessActivity
import com.strait.ivblanc.src.styleMaking.StyleMakingActivity
import com.strait.ivblanc.ui.PhotoListFragment
import com.strait.ivblanc.util.CategoryCode
import java.lang.Exception

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val mainViewModel: MainViewModel by viewModels()
    val friendViewModel: FriendViewModel by viewModels()
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 2022/01/26 모든 옷 받기 테스트
        mainViewModel.getAllClothesWithCategory(CategoryCode.TOTAL)
        init()
    }

    // TODO: 2022/02/03 nav fragment 추가 및 style generic 추가
    private fun init() {
        setToolbar()
        // 첫 화면 세팅
        setFragment(PhotoListFragment<Clothes>(), "clothes")

        // nav click 시 fragment 변경
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_clothes -> {
                    setFragment(PhotoListFragment<Clothes>(), "clothes")
                    true
                }
                // Style로 변경
                R.id.nav_style -> {
                    setFragment(PhotoListFragment<Clothes>(), "style")
                    true
                }
                R.id.nav_history -> {
                    setFragment(HistoryFragment(), "history")
                    true
                }
                R.id.nav_friend -> {
                    setFragment(FriendFragment(), "friend")
                    true
                }
                else -> false
            }
        }

        friendViewModel.friendName.observe(this) {
            if (friendViewModel.friendName.value == "error1") {
                friendDialog("없는 이메일입니다.")
            }else if(friendViewModel.friendName.value == "error2") {
                friendDialog("이미 요청보낸 친구입니다.")
            }
            else{
                friendDialog(friendViewModel.friendName.value+"님께 친구요청을 보냈습니다")
            }

        }
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_main_container, fragment, tag).commit()
    }


    // TODO: 2022/02/04 툴바가 있는 액티비티 마다 반복되는 코드
    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mainViewModel.toolbarTitle.observe(this) {
            setToolbarTitle(it)
        }
        mainViewModel.leadingIconDrawable.observe(this) {
            setLeadingIcon(it, getListener(it))
        }

        mainViewModel.trailingIconDrawable.observe(this) {
            setTrailingIcon(it, getListener(it))
        }
    }

    // imageView의 background drawable Id에 따라 버튼 리스너 반환
    private fun getListener(resId: Int): View.OnClickListener {
        return when (resId) {
            R.drawable.ic_close -> object : View.OnClickListener {
                override fun onClick(v: View?) {
                    this@MainActivity.onBackPressed()
                }
            }
            // 같은 add Drawable 일때, 현재 fragment의 tag로 리스너 설정
            R.drawable.ic_add -> {
                when (getCurrentFragmentTag()) {
                    "clothes" -> {
                        View.OnClickListener {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    PhotoSelectActivity::class.java
                                ).apply {
                                    putExtra("intend", PhotoSelectActivity.CLOTHES)
                                })
                        }
                    }
                    "style" -> {
                        View.OnClickListener {
                            startActivity(Intent(this@MainActivity, StyleMakingActivity::class.java))
                        }
                    }
                    "history" -> {
                        View.OnClickListener { toast("히스토리 생성 Activity로 이동", Toast.LENGTH_SHORT) }
                    }
                    else -> View.OnClickListener {}
                }


            }
            R.drawable.ic_baseline_notifications_24 -> {
                View.OnClickListener {
                    startActivity(Intent(this@MainActivity,FriendNoti::class.java))
                }
            }
            R.drawable.ic_baseline_person_add_24 -> {
                View.OnClickListener {
                    initDialog()
                }
            }
            else -> View.OnClickListener { }
        }
    }

    private fun getCurrentFragmentTag(): String? =
        supportFragmentManager.findFragmentById(R.id.frameLayout_main_container)?.tag

    private fun setToolbarTitle(title: String) {
        binding.toolbarMain.textViewToolbar.text = title
    }

    private fun setLeadingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarMain.imageViewToolbarLeadingIcon.background =
                ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
        }
        binding.toolbarMain.imageViewToolbarLeadingIcon.setOnClickListener(clickListener)
    }

    private fun setTrailingIcon(resId: Int, clickListener: View.OnClickListener) {
        try {
            binding.toolbarMain.imageViewToolbarTrailingIcon.background =
                ResourcesCompat.getDrawable(resources, resId, null)
        } catch (e: Exception) {
        }
        binding.toolbarMain.imageViewToolbarTrailingIcon.setOnClickListener(clickListener)
    }

    private fun initDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_friend, null)

        val dialogText = dialogView.findViewById<EditText>(R.id.dialogEt)
        AlertDialog.Builder(this,R.style.MyDialogTheme).setView(dialogView)
            .setPositiveButton("확인"
            ) { dialog, i ->
                Log.d("ssss", "initDialog: "+dialogText.text)

                friendViewModel.requestFriend("aaa@a.com", dialogText.text.toString())
            }

            .setNegativeButton("취소") { dialogInterface, i ->
            }
            .show()
    }
    fun friendDialog(title:String){
        MaterialAlertDialogBuilder(this, R.style.MyDialogTheme)
            .setTitle(title)
            .setPositiveButton("확인") { dialog, which ->
                // Respond to positive button press
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        //applicant 수정 필요 받아오는값으로
                        onBackPressed()
                    }
                }
            }
            .show()
    }
}