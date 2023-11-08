package ar.edu.unlam.mobile.scaffold

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffold.ui.screens.collection.CollectionScreen
import ar.edu.unlam.mobile.scaffold.ui.screens.herodetail.HeroDetailScreen
import ar.edu.unlam.mobile.scaffold.ui.screens.heroduel.HeroDuelScreen
import ar.edu.unlam.mobile.scaffold.ui.screens.home.HomeScreen
import ar.edu.unlam.mobile.scaffold.ui.screens.map.ScreenMap
import ar.edu.unlam.mobile.scaffold.ui.screens.map.presentation.MapViewModel
import ar.edu.unlam.mobile.scaffold.ui.screens.qr.QrScreen
import ar.edu.unlam.mobile.scaffold.ui.screens.quiz.QuizScreen
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ComicWarTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()


                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainScreen() {
    val controller = rememberNavController()
    Scaffold(
        floatingActionButton = {
            IconButton(onClick = { controller.navigate("home") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        },
    ) { paddingValue ->
        NavHost(navController = controller, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    modifier = Modifier.padding(paddingValue),
                    navDuel = { controller.navigate(route = "duel") },
                    navQuiz = { controller.navigate(route = "quiz") },
                    navMap = { controller.navigate(route = "Mapa") },
                    navCollection = { controller.navigate(route = "collection") }
                )
            }
            composable("collection") {
                CollectionScreen(
                    modifier = Modifier.padding(paddingValue),
                    navigateToHeroDetail = { heroID -> controller.navigate(route = "herodetail/$heroID") }
                )
            }
            composable(
                route = "herodetail/{heroid}",
                arguments = listOf(navArgument("heroid") { type = NavType.IntType }),
            ) { navBackStackEntry ->
                val heroID = navBackStackEntry.arguments?.getInt("heroid") ?: 1
                HeroDetailScreen(
                    modifier = Modifier.padding(paddingValue),
                    navigateToQR = { controller.navigate(route = "qr") },
                    heroID = heroID
                )
            }
            composable("quiz") {
                QuizScreen(
                    modifier = Modifier.padding(paddingValue),
                    returnHomeScreen = { controller.navigate(route = "home") }
                )
            }
            composable("duel") {
                HeroDuelScreen(
                    modifier = Modifier.padding(paddingValue),
                    controller = controller
                )
            }
            composable("qr") {
                QrScreen(
                    modifier = Modifier.padding(paddingValue),
                    controller = controller
                )
            }

            composable("Mapa") {
                ScreenMap(
                    modifier = Modifier.padding(paddingValue),
                    controller = controller,



                )
            }

        }
    }


}

