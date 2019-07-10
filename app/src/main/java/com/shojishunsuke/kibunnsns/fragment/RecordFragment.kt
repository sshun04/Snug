package com.shojishunsuke.kibunnsns.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import at.markushi.ui.CircleButton
import com.bumptech.glide.Glide
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.activity.SettingActivity
import com.shojishunsuke.kibunnsns.clean_arc.presentation.RecordFragmentViewModel
import de.hdodenhof.circleimageview.CircleImageView

class RecordFragment : Fragment() {

    private val REQUEST_CODE_VIEW = 1
    private val RESULT_OK = -1
    lateinit var viewModel: RecordFragmentViewModel
    lateinit var iconView: CircleImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RecordFragmentViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        iconView = view.findViewById<CircleImageView>(R.id.acIcon)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val editNameIcon = view.findViewById<ImageView>(R.id.editNameIcon)
        val editImageIcon = view.findViewById<CircleButton>(R.id.editImageButton)
        val settingIcon = view.findViewById<ImageView>(R.id.settingIcon)

        settingIcon.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        editImageIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().path), "image/*")
            startActivityForResult(intent, REQUEST_CODE_VIEW)

        }

        viewModel.userName.observe(this, Observer { userName ->
            nameTextView.text = userName
        })

        editNameIcon.setOnClickListener {
            setUpEditNmameDialog(inflater)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_VIEW) {
            val uri = data?.data ?: throw IllegalArgumentException()
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            viewModel.saveUserIcon(bitmap)
        } else {
            Toast.makeText(requireContext(), "アイコン画像の選択に失敗しました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.currentBitmap != null) {
            Glide.with(requireContext())
                .load(viewModel.currentBitmap)
                .into(iconView)
        } else {
            GlideApp.with(requireContext())
                .load(viewModel.getIconRef())
                .into(iconView)
        }

    }

    private fun setUpEditNmameDialog(inflater: LayoutInflater) {
        val parentView = inflater.inflate(R.layout.fragment_dialog_edit_name, null)
        val editText = parentView.findViewById<EditText>(R.id.editNickNameEditText)

        val editDialog = AlertDialog.Builder(requireContext())
            .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                viewModel.saveUserName(editText.text.toString())
            })
            .setNegativeButton("キャンセル", null)
            .create()

        editDialog.setView(parentView)
        editDialog.show()
    }
}