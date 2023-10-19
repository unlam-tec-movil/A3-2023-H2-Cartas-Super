package ar.edu.unlam.mobile.scaffold.ui.screens.map.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import com.google.android.gms.location.FusedLocationProviderClient


@Composable
fun ScreenMap(
    fusedLocationProviderClient: FusedLocationProviderClient,
    modifier: Modifier,
    controller: NavHostController
){
    MainScreen(fusedLocationProviderClient = fusedLocationProviderClient)
}




@Composable
fun MainScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    useSystemUIController: Boolean = true) {

    ComicWarTheme(useSystemUIController = useSystemUIController) {
        MapScreen(fusedLocationProviderClient)
    }
}