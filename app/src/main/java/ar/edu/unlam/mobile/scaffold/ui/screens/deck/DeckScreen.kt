package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.ui.components.ListaMazosPersonalizados
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroImage

@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel = hiltViewModel()
){
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val listDeck by viewModel.listDeck.collectAsStateWithLifecycle()
    val randomDeck by viewModel.randomDeck.collectAsStateWithLifecycle()
    val pantallaActual by viewModel.pantallaActual.collectAsStateWithLifecycle()
    val irACrearMazos = viewModel::irAGenerarMazos
    val generarMazo = viewModel::generarMazoRandom
    val regresarAlListado = viewModel::irAListaDeMazos
    val guardarMazo = viewModel::guardarMazoRandom

    if (isLoading){
        CircularProgressIndicator()
    }else{
        when(pantallaActual){
            DeckUI.LISTA_DE_MAZOS -> DeckListUI(
                modifier = modifier,
                listaDeMazos = listDeck,
                irACrearMazos = irACrearMazos
            )
            DeckUI.GENERAR_MAZOS -> GenerarDeckUI(
                modifier = modifier,
                deck = randomDeck,
                generarMazo = generarMazo,
                regresarAlListado = regresarAlListado,
                guardarMazo = guardarMazo
            )
        }
    }
}

@Composable
fun DeckListUI(
    modifier: Modifier = Modifier,
    listaDeMazos: List<DeckModel> = emptyList(),
    irACrearMazos: ()-> Unit = {}
){
    Box(modifier = modifier){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            ListaMazosPersonalizados(
                modifier = Modifier.fillMaxWidth(),
                deckList = listaDeMazos
            )
            Button(onClick = irACrearMazos) {
                Text("Crear Nuevo Mazo")
            }
        }
    }
}

@Composable
fun GenerarDeckUI(
    modifier: Modifier = Modifier,
    deck: DeckModel= DeckModel(id = 0),
    generarMazo: ()->Unit = {},
    regresarAlListado: ()->Unit = {},
    guardarMazo: ()->Unit = {}
){
    Box(modifier = modifier){
        Column(modifier = Modifier.fillMaxSize()) {
            CuadriculaImagen(deck = deck)
            Button(onClick = generarMazo) {
                Text(text = "Generar Mazo Aleatoreo")
            }
            Button(onClick = guardarMazo) {
                Text(text = "Guardar Mazo")
            }
            Button(onClick = regresarAlListado) {
                Text(text = "Regresar al Listado")
            }
        }
    }

}
@Composable
fun CuadriculaImagen(
    modifier: Modifier = Modifier,
    deck: DeckModel = DeckModel(id = 0)
){
    Column(modifier = modifier){
        Row(modifier = Modifier.fillMaxWidth()){
            HeroImage(url = deck.carta1.image.url)
            HeroImage(url = deck.carta2.image.url)

        }
        Row(modifier = Modifier.fillMaxWidth()){
            HeroImage(url = deck.carta3.image.url)
            HeroImage(url = deck.carta4.image.url)
        }
        Row(modifier = Modifier.fillMaxWidth()){
            HeroImage(url = deck.carta5.image.url)
            HeroImage(url = deck.carta6.image.url)
        }
    }
}