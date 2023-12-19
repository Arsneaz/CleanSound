package com.example.cleansound.ui.auth

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentProfileSetupBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileSetupFragment : Fragment() {

    private val profileSetupViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!

    private var imageUri : Uri? = null

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            showImagePickerOptions()
        } else {
            Toast.makeText(requireContext(), "This app need permission to take and store the picture", Toast.LENGTH_LONG).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            profileSetupViewModel.saveImageUri(imageUri)
            Toast.makeText(requireContext(), "Success taking a picture", Toast.LENGTH_LONG).show()
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            profileSetupViewModel.saveImageUri(imageUri)
            Toast.makeText(requireContext(), "Success picking a picture", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSetupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileSetupViewModel.imageUri.observe(viewLifecycleOwner) {imageUri ->
            Glide.with(binding.root.context)
                .load(imageUri)
                .into(binding.ivProfilePicture)
        }

        profileSetupViewModel.insertUserStatus.observe(viewLifecycleOwner) {insertStatus ->
            if (insertStatus) {
                findNavController().navigate(R.id.action_profileSetupFragment_to_navigation_home)
            }
        }

        // Should I make a init for this every time a user register (make a default username)
        // and then update the content if the user decided to make userProfile???
        binding.proceedBtn.setOnClickListener{
            val userName = binding.userNameProfile.text.toString()
            var userDesc = binding.userDescProfile.text.toString()
            val userEmail = FirebaseAuth.getInstance().currentUser?.email!!
            userDesc = userDesc.takeIf { it.isNotEmpty() } ?: "This person has not have any description"
            val userImageUri = imageUri ?: getDefaultImageUri(requireContext(), R.drawable.ic_user)

            if (userName.isEmpty()) {
                Toast.makeText(requireContext(),"Please fill the username.",Toast.LENGTH_LONG).show()
            } else {
                profileSetupViewModel.addNewUser(userEmail, userName, userDesc, userImageUri.toString())
            }
        }

        binding.ivProfilePicture.setOnClickListener{
            checkAndRequestPermission()
        }
    }

    private fun getDefaultImageUri(context: Context, resId: Int): Uri {
        return Uri.parse("android.resource://${context.packageName}/$resId")
    }

    private fun checkAndRequestPermission() {
        if (!hasCameraPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            showImagePickerOptions()
        }

    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showImagePickerOptions() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()
                    1 -> dispatchPickFromGalleryIntent()
                }
            }
            .show()
    }

    private fun dispatchPickFromGalleryIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile = createImageFile()
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.cleansound.fileprovider",
                    photoFile
                )
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            takePictureLauncher.launch(intent)
        }
    }

    private fun createImageFile() : File? {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_{$timeStamp}_"
        val storageDir : File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return  File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )

    }

}