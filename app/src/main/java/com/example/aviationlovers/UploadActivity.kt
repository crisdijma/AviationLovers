package com.example.aviationlovers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.aviationlovers.databinding.ActivityUploadBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.*


class UploadActivity : AppCompatActivity() {

    private lateinit var uploadImage: ImageView

    private lateinit var binding: ActivityUploadBinding
    var imageURL: String? = null
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)

            }else {
                Toast.makeText(this@UploadActivity, "NO IMAGE SELECTED", Toast.LENGTH_SHORT).show()
            }
        }

        binding.uploadImage.setOnClickListener{
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.saveButton.setOnClickListener{
            saveData()
        }


//      ------------------------------------------------
        uploadImage = findViewById(R.id.uploadImage)

        // Create the ObjectAnimator for blinking
        val blinkAnimator = ObjectAnimator.ofFloat(uploadImage, "alpha", 0f, 1f).apply {
            duration = 2500 // Duration of each animation cycle (in milliseconds)
            repeatMode = ObjectAnimator.REVERSE // Reverse the animation
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        }

        // Start the blinking animation
        blinkAnimator.start()
    }

    private fun saveData(){
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
            .child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        var dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
        }
    }

    private fun uploadData(){
        val route = binding.uploadRoute.text.toString()
        val company = binding.uploadCompany.text.toString()
        val miles = binding.uploadMiles.text.toString()
        val dataClass = DataFile(route, company, miles, imageURL)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("FlightsList").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}