package com.example.player.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.player.R
import com.example.player.data.entity.MusicModel
import com.example.player.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100
    private var navController: NavController? = null

    val viewModel by viewModels<MusicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllPermission(this)

//        viewModel.insert(MusicModel(0,"Hello", "Men", "hello", "12:00", "12:00", "//"))

    }

    private fun getAllPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navController = findNavController(R.id.nav_host_fragment_main)
                    loadData()
                } else {
                    Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_SHORT).show();
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun loadData(){
        viewModel.getAllMusic()
    }
}