package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.ui.components.ActionMenu
import ar.edu.unlam.mobile.scaffold.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffold.ui.components.GameScore
import ar.edu.unlam.mobile.scaffold.ui.components.PlayerDeck
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroPlayerCard
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview(showBackground = true)
@Composable
fun FinishedDuelUi(
    modifier: Modifier = Modifier,
    winner: Winner = Winner.NONE,
    playerScore: Int = 0,
    adversaryScore: Int = 0
) {
    val offset = Offset(6.0f, 4.0f)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "El ganador es " + if (winner == Winner.PLAYER) "el jugador" else "el adversario",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = shaka_pow,
            style = TextStyle(
                fontSize = 24.sp,
                shadow = Shadow(
                    color = Color.White,
                    offset = offset,
                    blurRadius = 4f
                )
            )
        )
        GameScore(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            playerScore = playerScore,
            adversaryScore = adversaryScore
        )
    }
}

@Composable
fun HeroDuelScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: HeroDuelViewModelv2 = hiltViewModel()
) {
    Image(
        painter = painterResource(id = R.drawable.fondo_pantalla_pelea),
        contentDescription = "Pantalla NewGame",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = true)
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        val showSelectCardScreen by viewModel.showSelectCardScreen.collectAsStateWithLifecycle(
            initialValue = false
        )
        val showHeroDuelScreen by viewModel.showHeroDuelScreen.collectAsStateWithLifecycle(
            initialValue = false
        )
        val showWinnerScreen by viewModel.showWinnerScreen.collectAsStateWithLifecycle(
            initialValue = false
        )
        val playerDeck by viewModel.currentPlayerDeck.collectAsStateWithLifecycle(
            initialValue = listOf(HeroModel())
        )
        val cardSelectedIndex by viewModel.cardSelectedIndex.collectAsStateWithLifecycle(
            initialValue = 0
        )
        val onPlayCardClick = viewModel::onPlayCardClick
        val onPlayerCardClick = viewModel::onPlayerCardClick
        val currentPlayerCard by viewModel.currentPlayerCard.collectAsStateWithLifecycle(
            initialValue = HeroModel()
        )
        val currentAdversaryCard by viewModel.currentAdversaryCard.collectAsStateWithLifecycle(
            initialValue = HeroModel()
        )
        val playerScore by viewModel.playerScore.collectAsStateWithLifecycle(
            initialValue = 0
        )
        val adversaryScore by viewModel.adversaryScore.collectAsStateWithLifecycle(
            initialValue = 0
        )
        val onClickSelectedStat = viewModel::onClickSelectedStat
        val useMultiplierX2 = viewModel::useMultiplierX2
        val onFightClick = viewModel::onFightClick
        val winner by viewModel.winner.collectAsStateWithLifecycle(initialValue = Winner.NONE)
        val canMultix2BeUsed by viewModel.canMultix2BeUsed.collectAsStateWithLifecycle(
            initialValue = true
        )

        if (showSelectCardScreen) {
            SelectCardUi(
                modifier = modifier,
                playerDeck = playerDeck,
                cardSelectedIndex = cardSelectedIndex,
                onPlayCardClick = onPlayCardClick,
                onPlayerCardClick = onPlayerCardClick
            )
        }

        if (showHeroDuelScreen) {
            DuelUi(
                modifier = modifier,
                currentPlayerCard = currentPlayerCard,
                currentAdversaryCard = currentAdversaryCard,
                playerScore = playerScore,
                adversaryScore = adversaryScore,
                onClickSelectedStat = onClickSelectedStat,
                useMultiplierX2 = useMultiplierX2,
                onFightClick = onFightClick,
                canMultix2BeUsed = canMultix2BeUsed
            )
        }

        if (showWinnerScreen) {
            FinishedDuelUi(
                modifier = modifier,
                winner = winner,
                playerScore = playerScore,
                adversaryScore = adversaryScore
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DuelUi(
    modifier: Modifier = Modifier,
    currentPlayerCard: HeroModel = HeroModel(),
    currentAdversaryCard: HeroModel = HeroModel(),
    playerScore: Int = 0,
    adversaryScore: Int = 0,
    onClickSelectedStat: (Stat) -> Unit = {},
    canMultix2BeUsed: Boolean = true,
    useMultiplierX2: (Boolean) -> Unit = {},
    onFightClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameScore(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            playerScore = playerScore,
            adversaryScore = adversaryScore
        )
        val cardModifier = Modifier
            .padding(7.dp)
            .shadow(8.dp)
        HeroCard(
            modifier = cardModifier,
            hero = currentAdversaryCard
        )
        ActionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            onClickSelectedStat = onClickSelectedStat,
            useMultiplierX2 = useMultiplierX2,
            onFightClick = onFightClick,
            canMultix2BeUsed = canMultix2BeUsed
        )
        HeroPlayerCard(
            modifier = cardModifier,
            hero = currentPlayerCard
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCardUi(
    modifier: Modifier = Modifier,
    playerDeck: List<HeroModel> = listOf(
        HeroModel(id = 1, name = "test 1"),
        HeroModel(id = 2, name = "test 2")
    ),
    cardSelectedIndex: Int = 0,
    onPlayCardClick: () -> Unit = {},
    onPlayerCardClick: (Int) -> Unit = {},
) {
    var setDefaults by rememberSaveable {
        mutableStateOf(true)
    }
    if (setDefaults) {
        onPlayerCardClick(0)
        setDefaults = false
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            label = { "Jugar carta!" },
            onClick = onPlayCardClick
        )
        HeroPlayerCard(
            modifier = Modifier
                .padding(8.dp)
                .shadow(9.dp),
            hero = playerDeck[cardSelectedIndex],
            cardColors = CardDefaults.cardColors(Color(0xFF16A0E8)) // hay que guardar el color en ui.theme.Color.kt
        )
        PlayerDeck(
            modifier = Modifier,
            playerDeck = playerDeck,
            onCardClick = onPlayerCardClick
        )
    }
}
