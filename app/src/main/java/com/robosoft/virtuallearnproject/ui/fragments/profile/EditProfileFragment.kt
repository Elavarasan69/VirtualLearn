package com.robosoft.virtuallearnproject.ui.fragments.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.databinding.FragmentEditProfileBinding
import com.robosoft.virtuallearnproject.dataclass.profile.EditProfileDataX
import com.robosoft.virtuallearnproject.network.myprofile.EditProfileApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope as GlobalScope1

class EditProfileFragment : Fragment() {

    private lateinit  var binding: FragmentEditProfileBinding
    val permissionList = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        binding.editProfileBackBtn?.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
//            beginTransaction()
//                ?.replace(R.id.homescreenfragment_container, MyProfileFragment())
//                ?.addToBackStack(null)
//                ?.commit()
        }


        val editProfilePhoto = arguments?.getString("profilephoto")

        binding.fullNameEt?.setText(arguments?.getString("fullname"))
        binding.userNameEt?.setText(arguments?.getString("usrename"))
        binding.emailEt?.setText(arguments?.getString("email"))
        binding.mblNoEt?.setText(arguments?.getString("mblno"))
        binding.occupationEt?.setText(arguments?.getString("occupation"))
        binding.DOBEt?.setText(arguments?.getString("dob"))
        binding.twitterLinkEt?.setText(arguments?.getString("twitter"))
        binding.facebookLinkEt?.setText(arguments?.getString("facebook"))
        binding.genderEt?.setText(arguments?.getString("gender"))
        Picasso.get().load(editProfilePhoto).into(binding.profileImageIv)
        Picasso.get().load(editProfilePhoto).into(binding.editprofilebackgroundIv)


//        val sharedPreferences = context?.getSharedPreferences("sharedPreference" , Context.MODE_PRIVATE)
//        val editor = sharedPreferences?.edit()

        binding.cameraIv.setOnClickListener {
            if(!checkPermission(context, permissionList[0])){
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(permissionList[0]),1)
            }
            else{
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,1)
            }
        }
        binding.changeimageTv.setOnClickListener {
            if(!checkPermission(context, permissionList[1])){
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(permissionList[1]),1)
            }
            else{
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent,2)
            }
        }


        binding.saveBtn?.setOnClickListener {
            val password = "123"
            val fullName = binding.fullNameEt?.text.toString()
            val userName = binding.userNameEt?.text.toString()
            //val password = binding.p?.text.toString()
            val email = binding.emailEt?.text.toString()
            val mobileNumber = binding.mblNoEt?.text.toString().toLong()
            val occupationEt = binding.occupationEt?.text.toString()
            val gender = binding.genderEt?.text.toString()
            val dateOfBirth = binding.DOBEt?.text.toString()
            val twitterLink = binding.twitterLinkEt?.text.toString()
            val facebookLink = binding.facebookLinkEt?.text.toString()
            val profilePhoto = binding.profileImageIv.toString()

            Log.d("entered", "enter")
            val apiService = EditProfileApiService()
            val accessToken = SharedPreferenceManager(activity?.applicationContext!!).getAccessToken()

            val data = EditProfileDataX(
                dateOfBirth =dateOfBirth,
                email = email,
                facebookLink = facebookLink,
                fullName = fullName,
                gender = gender,
                mobileNumber = mobileNumber,
                occupation = occupationEt,
                password = password,
                twitterLink = twitterLink,
                userName = userName,
                profileImageEdit = profilePhoto
            )
            //val data = EditProfileDataX("2000-10-21","ramya@gmail.com","facebook.com","Ramya B B","Female",8296638548,"Developer","Ramya888","twitter.com","ramyabb")
            Log.d("data", data.toString())


            GlobalScope1.launch {
                apiService.editProfile(accessToken!!, data) {
                    if (it != null) {
                        Log.d("messageresponse", it.message)
                        //email
                    } else {
                        Log.d("message", "something went wrong")
                    }
                }
            }
        }
        return binding.root
    }

    private fun checkPermission(context: Context?, PERMISSION: String): Boolean {
        context?.apply {
            PERMISSION.apply {
                if (ActivityCompat.checkSelfPermission(context, PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissions[0].equals(permissionList[0])) {
                    var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 1)
                }
                else{
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,2)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1 && resultCode== AppCompatActivity.RESULT_OK){
            var bmp = data?.extras?.get("data")
            binding.profileImageIv.setImageBitmap(bmp as Bitmap)
        }
        else if (requestCode == 2){
            binding.profileImageIv.setImageURI(data?.data)
        }
    }
}


