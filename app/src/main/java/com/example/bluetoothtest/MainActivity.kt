package com.example.bluetoothtest

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothtest.ui.theme.BluetoothTestTheme

class MainActivity : ComponentActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, it.data.toString(), Toast.LENGTH_LONG).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
                    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
                    if (bluetoothAdapter?.isEnabled == false) {
                        //val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        when {
                            ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                //val enableBtIntent = Intent(this, BluetoothDevice::class.java)
                                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                resultLauncher.launch(enableBtIntent)
                            }
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this, Manifest.permission.BLUETOOTH
                            ) -> {
                                requestPermissions(
                                    arrayOf(
                                        Manifest.permission.BLUETOOTH,
                                        Manifest.permission.BLUETOOTH_CONNECT,
                                        Manifest.permission.BLUETOOTH_SCAN
                                    ),
                                    0
                                )
                            }
                            else -> {
                                requestPermissions(
                                    arrayOf(
                                        Manifest.permission.BLUETOOTH,
                                        Manifest.permission.BLUETOOTH_CONNECT,
                                        Manifest.permission.BLUETOOTH_SCAN
                                    ),
                                    0
                                )
                            }
                        }
//                        if (ActivityCompat.checkSelfPermission(
//                                this,
//                                Manifest.permission.BLUETOOTH_CONNECT
//                            ) == PackageManager.PERMISSION_GRANTED
//                        ) {
//                            val enableBtIntent = Intent(this, BluetoothDevice::class.java)
//                            resultLauncher.launch(enableBtIntent)
//                        } else {
//                            Toast.makeText(this, "checkSelfPermissions failed!", Toast.LENGTH_LONG).show()
//                            ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.BLUETOOTH
//                            )
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BluetoothTestTheme {
        Greeting("Android")
    }
}