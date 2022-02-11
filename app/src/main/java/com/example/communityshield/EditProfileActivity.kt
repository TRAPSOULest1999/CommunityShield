package com.example.communityshield

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.communityshield.databinding.ActivityEditProfileBinding
import com.example.communityshield.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener




class EditProfileActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityEditProfileBinding
    //Firbase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var uid: String
    private lateinit var userData: UserInfo
    private lateinit var Reporter: String
    private lateinit var user: DatabaseReference
    private lateinit var reporter: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //get user firebase uid
        uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo")
        binding.btnSave.setOnClickListener {

            //Declare values and attach to type userInfo data class value
            val userName = binding.etName.text.toString()
            val userSurname = binding.etSurname.text.toString()
            val cell = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()
            val userInfo = UserInfo(/*uid,*/userName,userSurname,cell,address)

            //Check if uid is not empty
            if (uid.isNotEmpty())
            {
                //Check user has entered all required fields
                if(userName.isNotEmpty() && userSurname.isNotEmpty() && cell.isNotEmpty() && address.isNotEmpty())
                {
                    //Point databaseReference child at uid to set values to userInfo values
                databaseReference.child(uid).setValue(userInfo).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@EditProfileActivity,"Successful upload",Toast.LENGTH_SHORT).show()
                        //Clear all editText of information entered
                        binding.etName.text.clear()
                        binding.etSurname.text.clear()
                        binding.etPhone.text.clear()
                        binding.etAddress.text.clear()

                        uploadProfilePic()
                        //else send message of failure
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Failed to Update Profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                }
                //Send toast message if all fields are not completed
                else
                {Toast.makeText(this@EditProfileActivity,"All fields must be Completed",Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource//$packageName/${R.drawable.profile}")
        storageReference = FirebaseStorage.getInstance().getReference("UserInfo/"+firebaseAuth.currentUser?.uid+".jpg")
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this@EditProfileActivity, "Profile Image Successfully Loaded",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this@EditProfileActivity, "Failed to Upload profile image",Toast.LENGTH_SHORT).show()
        }
    }
}