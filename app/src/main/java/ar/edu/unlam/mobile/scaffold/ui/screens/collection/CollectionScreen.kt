package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage

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
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        } else {
            Galeria(
                modifier = Modifier.fillMaxSize(),
                heroList = viewModel.heroList.collectAsStateWithLifecycle().value,
                controller = controller
            )
        }
    }
}

@Composable
fun Galeria(
    modifier: Modifier = Modifier,
    heroList: List<DataHero>,
    itemSize: Dp = 100.dp,
    controller: NavHostController
) {
    val onItemClick: (Int) -> Unit = {heroID -> controller.navigate(route = "herodetail/$heroID")}
    LazyVerticalGrid(
        columns = GridCells.Adaptive(itemSize),
        content = {
            items(heroList.size) {i ->
                GaleriaItem(
                    modifier = modifier,
                    dataHero = heroList[i],
                    onItemClick = onItemClick
                )
            }
        }
    )
}

// SOLUCIONAR QUE CADA ITEM MANTENGA EL MISMO TAMAÑO QUE LOS DEMÁS

@Composable
fun GaleriaItem(
    modifier: Modifier = Modifier,
    dataHero: DataHero = DataHero(),
    onItemClick : (Int) -> Unit,
) {
    TextButton(
        modifier = modifier,
        shape = RectangleShape,
        onClick = { onItemClick(dataHero.id.toInt()) }
    ) {
        Box(
            modifier = Modifier
                .background(brush = SolidColor(Color.Black), alpha = 0.25f)
        ) {
            Column {
                HeroImage(
                    url = dataHero.image.url
                )
                Text(dataHero.name,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = dataHero.id,
                modifier = Modifier.padding(8.dp),
                color = Color.Black
            )
        }
    }
}
