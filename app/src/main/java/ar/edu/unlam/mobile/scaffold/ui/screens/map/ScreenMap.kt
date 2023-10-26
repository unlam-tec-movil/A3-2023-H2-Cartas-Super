package ar.edu.unlam.mobile.scaffold.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.maps.android.compose.GoogleMap

@Composable
fun ScreenMap(modifier: Modifier = Modifier,
              controller: NavHostController
){
    GoogleMap(modifier = Modifier.fillMaxSize()) {

    }
}