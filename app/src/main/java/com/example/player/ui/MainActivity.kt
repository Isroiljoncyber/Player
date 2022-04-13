package com.example.player.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.player.R
import com.example.player.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100
    private var navController: NavController? = null

    val viewModel by viewModels<MusicViewModel>()
//    lateinit var viewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllPermission(this@MainActivity)
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
                    viewModel.getAllMusic()
                    navController = findNavController(R.id.nav_host_fragment_main)
                    val finalHost = NavHostFragment.create(R.navigation.navigation)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_main, finalHost)
                        .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
                        .commit()

                } else {
                    Toast.makeText(this, "NO PERMISSION", Toast.LENGTH_SHORT).show();
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}