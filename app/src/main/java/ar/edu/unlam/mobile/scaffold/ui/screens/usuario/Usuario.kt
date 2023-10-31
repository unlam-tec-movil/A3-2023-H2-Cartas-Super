package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import android.Manifest
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor



@Composable
fun Usuario(modifier: Modifier,
            viewModel: GuestViewModel = hiltViewModel()){

    var camara by remember{ mutableStateOf(false)}
    val context = LocalContext.current

    //Cargar Nombre de usuario
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false)}


    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier
                .fillMaxSize()
                .testTag("background image"),
            painterResourceId = R.drawable.fondo_coleccion,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
            }
            
            Row {
                TextField(value = name, onValueChange = {
                    name = it
                    nameError = false
                }, label = { Text(text = "Ingrese usuario")}, isError = nameError)
                Spacer(Modifier.size(16.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()){
                            viewModel.actualizarBase(name)
                        }else{
                            nameError = name.isBlank()
                        }
                    }
                ) {
                    Text("Continuar")
                }

            }
                Spacer(Modifier.size(16.dp))
            Row {
                AnswerButton(
                    modifier = Modifier.testTag("Camara"),
                    text = "Camara",
                    onButtonClick = { camara =! camara})

                if (camara){
                    PermisosDeLaCamara()
                }
                Spacer(Modifier.size(16.dp))
                AnswerButton(
                    modifier = Modifier.testTag("Agregar Usuario"),
                    text = "Usuario"
                )
            }

            Row(modifier = Modifier.fillMaxWidth()){
            }
        }
    }

}



@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun PermisosDeLaCamara(){
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val context = LocalContext.current
    val camaraController = remember {
        LifecycleCameraController(context)
    }

    val lifecycle =  LocalLifecycleOwner.current


    LaunchedEffect(Unit){
        permissionState.launchPermissionRequest()
    }

    Scaffold (modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {
            val executor = ContextCompat.getMainExecutor(context)
            takePicture(camaraController,executor)
        }) {
            Text(text = "Camara")
        }
    }) {
        if (permissionState.status.isGranted){
            CamaraComposable(camaraController, lifecycle, modifier = Modifier.padding(it))
        }else{
            Text(text = "Permiso denegado", modifier = Modifier.padding(it))
        }
    }
}


private fun takePicture(camaraController: LifecycleCameraController, executor : Executor){
    val file = File.createTempFile("imagenTest", ".jpg")
    val outputDirectory = ImageCapture.OutputFileOptions.Builder(file).build()
    camaraController.takePicture(outputDirectory, executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println(outputFileResults.savedUri)
        }
            override fun onError(exception: ImageCaptureException) {
            println("Error")
        }

    })
}

@Composable
fun CamaraComposable(
    camaraController: LifecycleCameraController,
    lifecycle: LifecycleOwner,
    modifier: Modifier = Modifier,
){
    camaraController.bindToLifecycle(lifecycle)

    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView (context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        previewView.controller = camaraController

        previewView
    })

}

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    text: String = "text",
    onButtonClick: () -> Unit = {}
) {
    ElevatedButton(
        onClick = onButtonClick,
        modifier = modifier
            .width(180.dp)
            .height(80.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = shaka_pow,
            textAlign = TextAlign.Center
        )
    }
}