package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : LocationViewModel = viewModel()
            LocationAppTheme {
                MyApp(viewModel)
            }
            }
        }
    }

@Composable
fun MyApp(viewModel: LocationViewModel){
    val context = LocalContext.current
    val permissionChecker = PermissionChecker(context)
    FirstDisplay(context, viewModel ,permissionChecker)
}

@Composable
fun FirstDisplay(
    context : Context,
    viewModel: LocationViewModel,
    permissionChecker: PermissionChecker) {


    val requestpermissionlauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
                    if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true)
                    {
                        //as permission is granted fetch the location coordinates
                    }
                    else
                    {
                        // one or both not granted hence show toast for asking permission and showing rationale
                        val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)

                        if (rationaleRequired)
                        {
                            Toast.makeText(context,"location permission needed for the app to be fully functionally",Toast.LENGTH_LONG).show()
                        }
                        else
                        {
                            Toast.makeText(context,"location permission nedded for app to be fully functional , go to android settings",Toast.LENGTH_LONG).show()
                        }
            }
        }
    )

    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        Text("location not avaliable")

        Button(onClick = {
            if(permissionChecker.Questionlocationpermission(context))
            {
                //permission avaliable , excute action to display location cordinates  on the ui

            }else{
                    //permission not avaliable,ask for permission launcher

                    requestpermissionlauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION ,
                            Manifest.permission.ACCESS_FINE_LOCATION )
                    )

                    //as now the permission is available , excute the commands in the following lines to display it on the UI

                }
        })
            {
            Text("get location")
            }


    }
}

































