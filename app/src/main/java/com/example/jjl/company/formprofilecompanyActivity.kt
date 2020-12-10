package com.example.jjl.company

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jjl.Constant
import com.example.jjl.MainActivity
import com.example.jjl.PreferenceHelper
import com.example.jjl.R
import com.example.jjl.databinding.ActivityFormprofilecompanyBinding
import com.example.jjl.login.ApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class formprofilecompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormprofilecompanyBinding
    private lateinit var viewModel: AddCompanyViewModel
    lateinit var sharedPref: PreferenceHelper

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_formprofilecompany)
        viewModel = ViewModelProvider(this).get(AddCompanyViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(companyapiservice::class.java)
        if (service != null) {
            viewModel.setLoginService(service)
        }


        binding.btnPickImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }
    }


    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_form) && sharedPref.getBoolean(Constant.pref_is_login)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageView.setImageURI(data?.data)
            val filePath = getPath(this, data?.data)
            val file = File(filePath)
            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            val a = sharedPref.getString(Constant.PREF_ID)
            val id_user = createPartFromString("$a")
            val company_name = createPartFromString(binding.etCompanyname.text.toString())
            val scope = createPartFromString(binding.etScope.text.toString())
            val city = createPartFromString(binding.etCity.text.toString())
            val company_description =
                createPartFromString(binding.etCompanydescription.text.toString())
            val instagram = createPartFromString(binding.etInstagram.text.toString())
            val position = createPartFromString(binding.etPosition.text.toString())
            val linkedID = createPartFromString(binding.etLinkedID.text.toString())


            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData(
                    "image", file.name,
                    it1
                )
            }

            binding.btnSubmit.setOnClickListener {
                if (img != null) {
                    viewModel.postCompanyApi(
                        id_user, company_name, scope,
                        city, company_description, instagram, position,
                        linkedID, img
                    )
                    sharedPref.put(Constant.pref_is_form, true)
                    Log.d("tesprofile", sharedPref.getString(Constant.PREF_IDCOMPANY).toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

}



