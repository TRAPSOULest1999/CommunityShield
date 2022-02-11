package com.example.communityshield

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import com.example.communityshield.databinding.ActivityProfileBinding
import com.example.communityshield.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ProfileActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityProfileBinding
    private lateinit var databaseReference: DatabaseReference
    //Progress Dialog
    private lateinit var builder: AlertDialog.Builder
    //Firbase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var uid: String
    private lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //get user firebase uid
        uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo")

        builder = AlertDialog.Builder(this@ProfileActivity)

       binding.btnDelete.setOnClickListener{
            deleteProfile() }
        /*firebaseAuth.currentUser?.delete()
        databaseReference.child(uid).removeValue()*/

        if(uid.isNotEmpty())
        {
            getUserData()
        }
        checkUser()


        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        val btnBack = findViewById<Button>(R.id.btnBack)


        // set on-click listener
        btnEditProfile.setOnClickListener{
            val intent = Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        btnBack.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener

    }

    //Delete confirmation dialog pop-up
   private fun deleteProfile() {
        builder.setTitle("Delete")
            .setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                databaseReference.child(uid).removeValue()
                firebaseAuth.currentUser?.delete()
                dialog.cancel()
                startActivity(Intent(this, ExitActivity::class.java))
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }.show()    }

    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userInfo = snapshot.getValue(UserInfo::class.java)!!
                binding.nameTv.setText(userInfo.userName + " "+ userInfo.userSurname)
                binding.cellTv.setText(userInfo.cell)
                binding.addressTv.setText(userInfo.address)
                getUserProfile()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity,"Failed to get user profile",Toast.LENGTH_SHORT).show()
            }

        }

        )

        }

    private fun getUserProfile() {
        storageReference = FirebaseStorage.getInstance().reference.child("UserInfo/$uid.jpg")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImg.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this@ProfileActivity,"Failed to retrieve profile image",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUser() {
        //if user is already logged in, then goes to ReportActivity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null)
        {
            //user is already logged in
            val email = firebaseUser.email
            binding.emailTv.text = email
        }
        else
        {
            //user NOT logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}