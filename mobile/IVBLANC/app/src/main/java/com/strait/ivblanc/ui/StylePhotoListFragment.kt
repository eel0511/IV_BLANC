package com.strait.ivblanc.ui

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.HorizontalRVAdapter
import com.strait.ivblanc.adapter.PhotoListRVAdapter
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.model.viewmodel.FriendViewModel
import com.strait.ivblanc.data.model.viewmodel.MainViewModel
import com.strait.ivblanc.data.model.viewmodel.StyleViewModel
import com.strait.ivblanc.databinding.FragmentStylePhotoListBinding
import com.strait.ivblanc.src.main.MainActivity
import com.strait.ivblanc.src.styleMaking.StyleMakingActivity
import com.strait.ivblanc.util.Status

class StylePhotoListFragment : BaseFragment<FragmentStylePhotoListBinding>(FragmentStylePhotoListBinding::bind, R.layout.fragment_style_photo_list) {
    lateinit var horizontalRVAdapter: HorizontalRVAdapter<Style>
    lateinit var photoListRVAdapter: PhotoListRVAdapter<Style>
    private val styleViewModel: StyleViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    lateinit var friendEmail: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(tag)
    }

    private fun init(tag: String?) {
        tag?: return
        setRecyclerView()
        setRecyclerViewClickListener(tag)
        setFloatingButton(tag)
        setObserver(tag)
    }

    private fun setRecyclerView() {
        horizontalRVAdapter = HorizontalRVAdapter()
        binding.recyclerViewStylePhotoListFHorizontal.apply {
            adapter = horizontalRVAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.right = 20
                }
            })
        }

        photoListRVAdapter = PhotoListRVAdapter()
        binding.recyclerViewStylePhotoListF.apply {
            adapter = photoListRVAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setRecyclerViewClickListener(tag: String) {
        when(tag) {
            // 내 스타일
            "style" -> {
                horizontalRVAdapter.itemClickListener = object: HorizontalRVAdapter.ItemClickListener {
                    override fun onClick(position: Int) {
                        if(requireActivity() is MainActivity) {
                            val intent = Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", horizontalRVAdapter.data[position])
                            (requireActivity() as MainActivity).addStyleContract.launch(intent)
                        }
                    }
                }
                photoListRVAdapter.itemClickListener = object: PhotoListRVAdapter.ItemClickListener {
                    override fun onClick(position: Int) {
                        if(requireActivity() is MainActivity) {
                            val intent = Intent(requireActivity(), StyleMakingActivity::class.java).putExtra("style", photoListRVAdapter.data[position])
                            (requireActivity() as MainActivity).addStyleContract.launch(intent)
                        }
                    }
                }
                photoListRVAdapter.itemLongClickListener = object : PhotoListRVAdapter.ItemLongClickListener {
                    override fun onLongClick(position: Int) {
                        val item = photoListRVAdapter.data[position]
                        showDeleteDialog(item)
                    }
                }
            }
            // 친구 스타
            "f1" -> {
                horizontalRVAdapter.itemClickListener = object: HorizontalRVAdapter.ItemClickListener {
                    override fun onClick(position: Int) {
                        showStyleDialog(horizontalRVAdapter.data[position].url)
                    }
                }
                photoListRVAdapter.itemClickListener = object: PhotoListRVAdapter.ItemClickListener {
                    override fun onClick(position: Int) {
                        showStyleDialog(horizontalRVAdapter.data[position].url)
                    }
                }
            }
        }
    }

    private fun setFloatingButton(tag: String) {
        when(tag) {
            "style" -> {
                binding.floatingButtonStylePhotoListF.setOnClickListener {
                    if(requireActivity() is MainActivity) {
                        val intent = Intent(requireActivity(), StyleMakingActivity::class.java)
                        (requireActivity() as MainActivity).addStyleContract.launch(intent)
                    }
                }
            }
            "f1" -> {
                binding.floatingButtonStylePhotoListF.setOnClickListener {
                    if(this::friendEmail.isInitialized) {
                        val intent = Intent(requireActivity(),StyleMakingActivity::class.java)
                        intent.putExtra("friendEmail", friendEmail)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setObserver(tag: String) {
        styleViewModel.styleListLiveData.observe(viewLifecycleOwner) {
            photoListRVAdapter.setDatas(it as List<Style>)
        }
        styleViewModel.recentlyAddedStyleList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                horizontalRVAdapter.setDatas(it as List<Style>)
            }
        }
        styleViewModel.styleDeleteResponseStatus.observe(viewLifecycleOwner) {
            if(it.status == Status.SUCCESS) {
                styleViewModel.getAllStyles()
            }
        }

        if(tag == "f1") {
            friendViewModel.firendEmail.observe(viewLifecycleOwner) {
                friendEmail = it
            }
        }
    }

    fun showDeleteDialog(item: Style) {
        val content: String = "이 룩을 삭제하시겠습니까?"
        DeleteDialog(requireActivity())
            .setContent(content)
            .setOnPositiveClickListener {
                styleViewModel.deleteStyleById(item.styleId)
            }.build().show()
    }

    fun showStyleDialog(url: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_style, null)

        val dialogphoto = dialogView.findViewById<ImageView>(R.id.dialog_image)
        Glide.with(dialogphoto).load(Uri.parse(url))
            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
            .into(dialogphoto)

        val adb =android.app.AlertDialog.Builder(requireContext(), R.style.MyDialogTheme).setView(dialogView)
        val dialog = adb.create()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = 900
        lp.height = 1200

        dialog.show()
        val window = dialog.getWindow();
        if (window != null) {
            window.setAttributes(lp)
        }
    }

}