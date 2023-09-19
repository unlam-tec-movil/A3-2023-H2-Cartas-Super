package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroGallery

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: CollectionViewModelImp = hiltViewModel()
) {
    Box(modifier = modifier){
        Image(
            painter = painterResource(id = R.drawable.fondo_coleccion),
            contentDescription = "Pantalla Coleccion",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()

        if(isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val onItemClick: (Int) -> Unit = {heroID -> controller.navigate(route = "herodetail/$heroID")}
            HeroGallery(
                modifier = Modifier.fillMaxSize(),
                heroList = viewModel.heroList.collectAsStateWithLifecycle().value,
                onItemClick = onItemClick
            )
        }
    }
}
