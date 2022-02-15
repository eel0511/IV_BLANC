package com.strait.ivblanc.src.history

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.PhotoRecyclerViewAdapter
import com.strait.ivblanc.adapter.StyleSelectRecyclerViewAdapter
import com.strait.ivblanc.databinding.FragmentHistoryPhotoBinding

class HistoryPhotoFragment(val imageSelectedListener: ImageSelectedListener) : DialogFragment() {
    private lateinit var binding: FragmentHistoryPhotoBinding
    private lateinit var photoSelectRecyclerViewAdapter: PhotoRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryPhotoBinding.bind(inflater.inflate(R.layout.fragment_history_photo, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        isCancelable = true
        setRecyclerView()
    }

    private fun setRecyclerView() {
        photoSelectRecyclerViewAdapter = PhotoRecyclerViewAdapter().apply {
            itemClickListener = object: PhotoRecyclerViewAdapter.ItemClickListener {
                override fun onClick(uri: Uri) {
                    imageSelectedListener.getImageUri(uri)
                    dismiss()
                }
            }
            uris = setImageUrisFromCursor(getPhotoCursor())
            notifyDataSetChanged()
        }
        binding.recyclerViewHistoryPhotoF.apply {
            adapter = photoSelectRecyclerViewAdapter
            layoutManager = GridLayoutManager(requireActivity(), 4, RecyclerView.VERTICAL, false)
        }
    }

    fun setImageUrisFromCursor(cursor: Cursor):List<Uri> {
        val list = mutableListOf<Uri>()
        cursor.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while(cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                list.add(uri)
            }
        }
        return list
    }

    // content resolver로 이미지 커서 가져오기
    fun getPhotoCursor(): Cursor {
        val resolver = requireActivity().contentResolver
        var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val img = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATA
        )

        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        queryUri = queryUri.buildUpon().build()
        return resolver.query(queryUri, img, null, null, orderBy)!!
    }

    interface ImageSelectedListener {
        fun getImageUri(uri: Uri)
    }
}