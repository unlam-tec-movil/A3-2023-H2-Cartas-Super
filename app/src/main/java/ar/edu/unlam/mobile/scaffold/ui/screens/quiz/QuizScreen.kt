package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffold.R
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxBackgroundImage
import ar.edu.unlam.mobile.scaffold.ui.components.ParallaxHeroImage
import ar.edu.unlam.mobile.scaffold.ui.theme.shaka_pow

@Preview(showBackground = true)
@Composable
fun QuizResultPopup(
    modifier: Modifier = Modifier,
    isCorrectAnswer: Boolean = false,
    show: Boolean = false,
    correctHeroName: String = "Correct Hero",
    chosenHero: String = "Chosen Hero",
    onClickPlayAgain: () -> Unit = {},
    onClickMainMenu: () -> Unit = {}
) {
    val textToShow = if (isCorrectAnswer) {
        "¡Felicidades! $correctHeroName es la respuesta correcta."
    } else {
        "Lamentablemente, $chosenHero es incorrecto. Respuesta correcta: $correctHeroName."
    }
    if (show) {
        AlertDialog(
            modifier = modifier.testTag("alert dialog"),
            onDismissRequest = {},
            title = { Text("Resultado") },
            text = {
                Text(
                    modifier = Modifier.testTag("body"),
                    text = textToShow
                )
            },
            confirmButton = {
                TextButton(
                    modifier = Modifier.testTag("play again button"),
                    onClick = onClickPlayAgain
                ) {
                    Text("Jugar de nuevo".uppercase())
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            dismissButton = {
                TextButton(
                    modifier = Modifier.testTag("return to main menu button"),
                    onClick = onClickMainMenu
                ) {
                    Text("Menú principal".uppercase())
                }
            }
        )
    }
}

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val imageUrl by viewModel.heroPortraitUrl.collectAsStateWithLifecycle()
    val option1Text by viewModel.option1.collectAsStateWithLifecycle()
    val option2Text by viewModel.option2.collectAsStateWithLifecycle()
    val option3Text by viewModel.option3.collectAsStateWithLifecycle()
    val option4Text by viewModel.option4.collectAsStateWithLifecycle()
    val onClickOption1 = viewModel::selectOption1
    val onClickOption2 = viewModel::selectOption2
    val onClickOption3 = viewModel::selectOption3
    val onClickOption4 = viewModel::selectOption4
    val isCorrectAnswer by viewModel.isCorrectAnswer.collectAsStateWithLifecycle()
    val showPopup by viewModel.showResult.collectAsStateWithLifecycle()
    val correctAnswer by viewModel.correctAnswer.collectAsStateWithLifecycle()
    val chosenHero by viewModel.chosenHero.collectAsStateWithLifecycle()

    val onNewGame = viewModel::newGame
    val onClickMainMenu = {
        controller.navigate(route = "home")
    }
    val sensorData by viewModel.sensorData
        .collectAsStateWithLifecycle(initialValue = SensorData(0f, 0f))

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelSensorDataFlow()
        }
    }

    QuizUi(
        modifier = modifier,
        isLoading = isLoading,
        imageUrl = imageUrl,
        option1Text = option1Text,
        option2Text = option2Text,
        option3Text = option3Text,
        option4Text = option4Text,
        onClickOption1 = onClickOption1,
        onClickOption2 = onClickOption2,
        onClickOption3 = onClickOption3,
        onClickOption4 = onClickOption4,
        isCorrectAnswer = isCorrectAnswer,
        showPopup = showPopup,
        correctHeroName = correctAnswer,
        chosenHero = chosenHero,
        onClickPlayAgain = onNewGame,
        onClickMainMenu = onClickMainMenu,
        sensorData = sensorData
    )
}

@Preview(showBackground = true)
@Composable
fun QuizUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    imageUrl: String = "https://loremflickr.com/400/400/cat?lock=1",
    option1Text: String = "Option 1",
    option2Text: String = "Option 2",
    option3Text: String = "Option 3",
    option4Text: String = "Option 4",
    onClickOption1: () -> Unit = {},
    onClickOption2: () -> Unit = {},
    onClickOption3: () -> Unit = {},
    onClickOption4: () -> Unit = {},
    isCorrectAnswer: Boolean = false,
    showPopup: Boolean = false,
    correctHeroName: String = "Correct Hero",
    chosenHero: String = "Chosen Hero",
    onClickPlayAgain: () -> Unit = {},
    onClickMainMenu: () -> Unit = {},
    sensorData: SensorData = SensorData(0f, 0f)
) {
    Box(modifier = modifier) {
        ParallaxBackgroundImage(
            modifier = Modifier
                .fillMaxSize()
                .testTag("background image"),
            contentDescription = "Pantalla Coleccion",
            painterResourceId = R.drawable.fondo_coleccion,
            data = sensorData
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier
                    .align(Alignment.Center)
                    .testTag("loading composable")
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                QuizTitle(
                    modifier = Modifier
                        .padding(18.dp)
                        .testTag("title")
                )
                ParallaxHeroImage(
                    modifier = Modifier
                        .testTag("hero image"),
                    imageUrl = imageUrl,
                    data = sensorData
                )
                AnswerPanel(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .testTag("answer panel"),
                    option1Text = option1Text,
                    option2Text = option2Text,
                    option3Text = option3Text,
                    option4Text = option4Text,
                    onOption1Click = onClickOption1,
                    onOption2Click = onClickOption2,
                    onOption3Click = onClickOption3,
                    onOption4Click = onClickOption4
                )
            }
        }
        QuizResultPopup(
            modifier = Modifier.testTag("Result popup"),
            isCorrectAnswer = isCorrectAnswer,
            show = showPopup,
            correctHeroName = correctHeroName,
            chosenHero = chosenHero,
            onClickPlayAgain = onClickPlayAgain,
            onClickMainMenu = onClickMainMenu
        )
    }
}

@Composable
fun QuizTitle(
    modifier: Modifier = Modifier,
    shadowOffset: Offset = Offset(6.0f, 4.0f),
    shadowColor: Color = Color.Black,
    text: String = "¿Quien es este heroe?"
) {
    Text(
        text = text,
        color = Color.White,
        fontFamily = shaka_pow,
        fontSize = 30.sp,
        modifier = modifier,
        style = TextStyle(
            fontSize = 18.sp,
            shadow = Shadow(
                color = shadowColor,
                offset = shadowOffset,
                blurRadius = 4f
            )
        )
    )
}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun AnswerPanel(
    modifier: Modifier = Modifier,
    onOption1Click: () -> Unit = {},
    onOption2Click: () -> Unit = {},
    onOption3Click: () -> Unit = {},
    onOption4Click: () -> Unit = {},
    option1Text: String = "option 1",
    option2Text: String = "option 2",
    option3Text: String = "option 3",
    option4Text: String = "option 4",
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            AnswerButton(
                modifier = Modifier.testTag("option 1 button"),
                text = option1Text,
                onButtonClick = onOption1Click
            )
            Spacer(modifier = Modifier.padding(4.dp))
            AnswerButton(
                modifier = Modifier.testTag("option 3 button"),
                text = option3Text,
                onButtonClick = onOption3Click
            )
        }
        Column {
            AnswerButton(
                modifier = Modifier.testTag("option 2 button"),
                text = option2Text,
                onButtonClick = onOption2Click
            )
            Spacer(modifier = Modifier.padding(4.dp))
            AnswerButton(
                modifier = Modifier.testTag("option 4 button"),
                text = option4Text,
                onButtonClick = onOption4Click
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    text: String = "text",
    onButtonClick: () -> Unit = {}
) {
    ElevatedButton(
        onClick = onButtonClick,
        modifier = modifier
            .width(180.dp)
            .height(80.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = shaka_pow,
            textAlign = TextAlign.Center
        )
    }
}
