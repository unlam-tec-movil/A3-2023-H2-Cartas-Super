package ar.edu.unlam.mobile.scaffold.ui.screens.deck


import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCardDeck

import ar.edu.unlam.mobile.scaffold.ui.screens.collection.CollectionViewModelImp
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow
@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    deckViewModel: DeckViewModel = hiltViewModel()
) {
    val isLoading by deckViewModel.isLoading.collectAsStateWithLifecycle()
    val cards by deckViewModel.cards.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Image(
                painter = painterResource(id = R.drawable.fondo_mazo_azul),
                contentDescription = "Pantalla detalles del héroe",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("TestDeckScreen pantalla fondo")
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(4.dp)
        ) {
            item {
                Text(
                    text = "Deck de Cartas",
                    fontFamily = shaka_pow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("DeckScreen titulo pantalla")
                        .offset(x = 2.dp, y = 2.dp)
                )
            }

            items(cards.chunked(2)) { rowCards ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (card in rowCards) {

                        HeroCardDeck(hero = card,
                            modifier = Modifier.testTag("DeckScreen Hero Card"))
                    }
                }

            }
        }
    }
}