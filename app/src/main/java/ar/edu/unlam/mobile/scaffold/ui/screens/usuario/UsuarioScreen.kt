package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import android.Manifest
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.CustomTitle
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor



@Composable
fun UsuarioScreen(modifier: Modifier,
                  viewModel: UsuarioViewModel = hiltViewModel()){

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


            CustomTitle(text = {"Usuarios"})

            Spacer(Modifier.size(16.dp))
        Box (contentAlignment = Alignment.Center){
           Row {
               AsyncImage(
                   model = ImageRequest.Builder(LocalContext.current)
                       .data("file:///data/user/0/ar.edu.unlam.mobile.scaffold/files/photoPic/My_photo.jpg")
                       .transformations(CircleCropTransformation())
                       .build(),
                   contentDescription = "Imagen de usuario"
               )
           }
        }
            Spacer(Modifier.size(16.dp))
            Row {
                
                TextField(value = name, onValueChange = {
                    name = it
                    nameError = false
                }, 
                    label = { Text(text = "Ingrese usuario")},
                    isError = nameError, modifier = Modifier,
                    placeholder = { Text(text = "Nombre de usuario")},
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)})

            }

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

                Spacer(Modifier.size(16.dp))
            
            Text(text = "Usuario actual: $name")
            
            Row {
                CustomButton(
                    modifier = Modifier.testTag("Camara"),
                    label = {"Camara"},
                    onClick = { camara =! camara})

                Spacer(Modifier.size(16.dp))
            }

            if (camara){
                PermisosDeLaCamara()
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
        FloatingActionButton(
            onClick = {
            val executor = ContextCompat.getMainExecutor(context)
            takePicture(camaraController,executor, context.filesDir)
        },
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            shape = CircleShape,

        ) {
            Icon(Icons.Default.Add, "Icono de mas")
        }
    },
        floatingActionButtonPosition = FabPosition.Center){

        if (permissionState.status.isGranted){
            CamaraComposable(camaraController, lifecycle, modifier = Modifier.padding(it))
        }else{
            Text(text = "Permiso denegado", modifier = Modifier.padding(it))
        }
    }
}

fun makeProfilePicFile(filesDir: File):File{
    val baseDirectory = File(filesDir,"photoPic")

    if (!baseDirectory.exists()){
        baseDirectory.mkdir()
    }

    val file = File(baseDirectory, "My_photo.jpg")

    if(file.exists()){
        file.delete()
    }

    file.createNewFile()
    return file
}


private fun takePicture(camaraController: LifecycleCameraController, executor : Executor, filesDir: File){

    val file = makeProfilePicFile(filesDir)

    //val file = File.createTempFile("imagenTest", ".jpg")

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
