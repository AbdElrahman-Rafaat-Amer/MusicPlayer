package com.abdelrahman.rafaat.musicplayer.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.ActivityPermissionBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "PermissionActivity"

class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding
    private val PERMISSION_REQUEST = 100
    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
            PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionsResult: -------------------->")
        for (grantResult in grantResults) {
            Log.i(TAG, "onRequestPermissionsResult: for -------------------->")
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: if2 -------------------->")
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.RECORD_AUDIO,
                    )
                ) {
                    Log.i(TAG, "onRequestPermissionsResult: if3 -------------------->")
                    binding.waitingAnimation.visibility = View.VISIBLE
                    binding.givePermission.visibility = View.VISIBLE
                    requestPermissions()
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: else if3-------------------->")
                    showSnackBar()
                    isFirstTime = false
                }
            } else {
                Log.i(TAG, "onRequestPermissionsResult: else if2-------------------->")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            R.string.open_setting,
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.action_settings) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",
                this.packageName,
                null
            )
            intent.data = uri
            startActivity(intent)
        }.setActionTextColor(Color.BLACK)
            .setBackgroundTint(Color.GREEN)
            .setTextColor(Color.BLACK).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ------------------->")
        if (hasStoragePermission()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else
            if (!isFirstTime) {
                showSnackBar()
                binding.waitingAnimation.visibility = View.VISIBLE
                binding.givePermission.visibility = View.VISIBLE
            }
    }

    private fun hasStoragePermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

}