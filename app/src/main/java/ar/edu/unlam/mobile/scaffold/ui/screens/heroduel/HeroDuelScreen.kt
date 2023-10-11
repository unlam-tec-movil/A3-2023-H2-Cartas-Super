package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
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
import ar.edu.unlam.mobile.scaffold.MainActivity
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Stat
import ar.edu.unlam.mobile.scaffold.domain.heroDuel.Winner
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroCard
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroItem
import ar.edu.unlam.mobile.scaffold.ui.components.hero.HeroPlayerCard
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview(showBackground = true)
@Composable
fun WinnerScreen(
    modifier: Modifier = Modifier,
    winner: Winner = Winner.NONE,
    playerScore: Int = 0,
    adversaryScore: Int = 0
) {
    val offset = Offset(6.0f, 4.0f)

    Column(
        modifier = Modifier.fillMaxSize(),
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
            initialValue = listOf(DataHero())
        )
        val cardSelectedIndex by viewModel.cardSelectedIndex.collectAsStateWithLifecycle(
            initialValue = 0
        )
        val onPlayCardClick = viewModel::onPlayCardClick
        val onPlayerCardClick = viewModel::onPlayerCardClick
        val currentPlayerCard by viewModel.currentPlayerCard.collectAsStateWithLifecycle(
            initialValue = DataHero()
        )
        val currentAdversaryCard by viewModel.currentAdversaryCard.collectAsStateWithLifecycle(
            initialValue = DataHero()
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
            SelectCard(
                modifier = modifier,
                playerDeck = playerDeck,
                cardSelectedIndex = cardSelectedIndex,
                onPlayCardClick = onPlayCardClick,
                onPlayerCardClick = onPlayerCardClick
            )
        }

        if (showHeroDuelScreen) {
            HeroDuel(
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
            WinnerScreen(
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
fun HeroDuel(
    modifier: Modifier = Modifier,
    currentPlayerCard: DataHero = DataHero(),
    currentAdversaryCard: DataHero = DataHero(),
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
fun GameScore(
    modifier: Modifier = Modifier,
    playerScore: Int = 0,
    adversaryScore: Int = 0
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IndividualScore(
            modifier = Modifier
                .border(1.dp, color = Color.Black),
            score = playerScore,
            text = "Jugador:",
            backgroundColor = Color(0xFF16A0E8)
        )
        IndividualScore(
            modifier = Modifier
                .border(1.dp, color = Color.Black),
            score = adversaryScore,
            text = "Adversario:",
            backgroundColor = Color(0xFFFA1404),
            textColor = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IndividualScore(
    modifier: Modifier = Modifier,
    score: Int = 0,
    text: String = "Jugador o adversario:",
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black
) {
    Text(
        modifier = modifier
            .background(color = backgroundColor)
            .padding(5.dp),
        text = "$text $score",
        fontWeight = FontWeight.ExtraBold,
        color = textColor
    )
}

@Preview(showBackground = true)
@Composable
fun ActionMenu(
    modifier: Modifier = Modifier,
    onClickSelectedStat: (Stat) -> Unit = {},
    canMultix2BeUsed: Boolean = true,
    useMultiplierX2: (Boolean) -> Unit = {},
    onFightClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectStat(modifier = Modifier.width(160.dp), onClick = onClickSelectedStat)
        Spacer(modifier = Modifier.size(8.dp))
        SelectMultiplier(
            useMultiplierX2 = useMultiplierX2,
            canMultix2BeUsed = canMultix2BeUsed
        )
        Button(
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            shape = ButtonDefaults.outlinedShape,
            onClick = {
                onFightClick()
            }
        ) {
            Text(
                text = "¡Pelear!",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectMultiplier(
    modifier: Modifier = Modifier,
    canMultix2BeUsed: Boolean = true,
    useMultiplierX2: (Boolean) -> Unit = {}
) {
    val offset = Offset(6.0f, 4.0f)
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Multi x2:",
            style = TextStyle(
                fontSize = 16.sp,
                shadow = Shadow(
                    color = Color.White,
                    offset = offset,
                    blurRadius = 4f
                )
            )
        )
        Checkbox(
            checked = if (canMultix2BeUsed) checked else false,
            onCheckedChange = {
                checked = !checked
                useMultiplierX2(checked)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCard(
    modifier: Modifier = Modifier,
    playerDeck: List<DataHero> = listOf(
        DataHero(id = "1", name = "test 1"),
        DataHero(id = "2", name = "test 2")
    ),
    cardSelectedIndex: Int = 0,
    onPlayCardClick: () -> Unit = {},
    onPlayerCardClick: (Int) -> Unit = {},
) {
    if (playerDeck.isEmpty()) {
        InCaseOfError("SelectCard composable.")
    } else {
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
            JugarCartaButton(
                onPlayCardClick = onPlayCardClick
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
                onPlayerCardClick = onPlayerCardClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JugarCartaButton(
    modifier: Modifier = Modifier,
    onPlayCardClick: () -> Unit = {}
) {
    Button(
        colors = ButtonDefaults.buttonColors(Color.DarkGray),
        modifier = modifier,
        onClick = {
            onPlayCardClick()
        }
    ) {
        Text(modifier = Modifier, color = Color.White, text = "Jugar Carta")
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDeck(
    modifier: Modifier = Modifier,
    playerDeck: List<DataHero> = listOf(DataHero(), DataHero(), DataHero()),
    onPlayerCardClick: (Int) -> Unit = {}
) {
    if (playerDeck.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            content = {
                items(playerDeck.size) { i ->
                    HeroItem(
                        modifier = Modifier
                            .clickable {
                                onPlayerCardClick(i)
                            }
                            .padding(8.dp)
                            .shadow(9.dp),
                        hero = playerDeck[i]
                    )
                }
            }
        )
    } else {
        InCaseOfError(place = "PlayerDeck composable")
    }
}

@Composable
fun InCaseOfError(place: String = "En algún lugar") {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hubo un error: Mazo vacío $place.")
        Button(
            onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }
        ) {
            Text(text = "volver al menú principal")
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStat(
    modifier: Modifier = Modifier,
    statList: List<Stat> = listOf(
        Stat.POWER,
        Stat.DURABILITY,
        Stat.STRENGTH,
        Stat.SPEED,
        Stat.COMBAT,
        Stat.INTELLIGENCE
    ),
    onClick: (Stat) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedStat by rememberSaveable { mutableStateOf(Stat.POWER) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedStat.statName,
                onValueChange = { onClick(selectedStat) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.statName) },
                        onClick = {
                            selectedStat = item
                            onClick(selectedStat)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
