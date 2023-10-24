package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffold.MainActivity
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.DefaultDataManager
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.QuizGameRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizScreenKtTest {

    /*
    // compose rule is required to get access to the composable component
    @get: Rule
    val composeTestRule = createComposeRule()

     */

    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var quizRepo: QuizGameRepository

    lateinit var heroRepository: IHeroRepository

    val heroList = listOf(
        HeroModel(
            id = 1,
            name = "Test 1",
            image = ImageModel("https://loremflickr.com/320/240?lock=1")
        ),
        HeroModel(
            id = 2,
            name = "Test 2",
            image = ImageModel("https://loremflickr.com/320/240?lock=2")
        ),
        HeroModel(
            id = 3,
            name = "Test 3",
            image = ImageModel("https://loremflickr.com/320/240?lock=3")
        ),
        HeroModel(
            id = 4,
            name = "Test 4",
            image = ImageModel("https://loremflickr.com/320/240?lock=4")
        )
    )

    @Before
    fun setup() {
        heroRepository = mockk()
        coEvery { heroRepository.getRandomPlayerDeck(4) } returns heroList
        quizRepo = QuizGameRepository(heroRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun whenHavingDefaultStates_VerifyIfAllViewsExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        composeTestRule.onNodeWithTag("loading composable").assertDoesNotExist()
        composeTestRule.onNodeWithTag("background image").assertExists()
        composeTestRule.onNodeWithTag("title").assertExists()
        composeTestRule.onNodeWithTag("hero image").assertExists()
        composeTestRule.onNodeWithTag("answer panel").assertExists()
    }

    @Test
    fun whenItsLoadingVerifyLoadingComposableExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = true
                )
            }
        }
        composeTestRule.onNodeWithTag("loading composable").assertExists()
        composeTestRule.onNodeWithTag("background image").assertDoesNotExist()
        composeTestRule.onNodeWithTag("title").assertDoesNotExist()
        composeTestRule.onNodeWithTag("hero image").assertDoesNotExist()
        composeTestRule.onNodeWithTag("answer panel").assertDoesNotExist()
    }

    @Test
    fun whenShowPopupIsTrueVerifyResultPopupExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize(),
                    showPopup = { true }
                )
            }
        }
        composeTestRule.onNodeWithTag("Result popup").assertExists()
    }

    @Test
    fun whenShowIsTrueInQuizPopupResultVerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizResultPopup(
                    show = { true }
                )
            }
        }
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
        composeTestRule.onNodeWithTag("body").assertExists()
        composeTestRule.onNodeWithTag("play again button").assertExists()
        composeTestRule.onNodeWithTag("return to main menu button").assertExists()
    }

    @Test
    fun verifyAllViewsExistsInAnswerPanel() {
        composeTestRule.setContent {
            ComicWarTheme {
                AnswerPanel()
            }
        }
        composeTestRule.onNodeWithTag("option 1 button").assertExists()
        composeTestRule.onNodeWithTag("option 2 button").assertExists()
        composeTestRule.onNodeWithTag("option 3 button").assertExists()
        composeTestRule.onNodeWithTag("option 4 button").assertExists()
    }

    val option1 = hasText("Test 1") and hasClickAction()
    val option2 = hasText("Test 2") and hasClickAction()
    val option3 = hasText("Test 3") and hasClickAction()
    val option4 = hasText("Test 4") and hasClickAction()

    @Test
    fun whenClickingOption1_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.onNode(option1).performClick()
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
    }

    @Test
    fun whenClickingOption2_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.onNode(option2).performClick()
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
    }

    @Test
    fun whenClickingOption3_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.onNode(option3).performClick()
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
    }

    @Test
    fun whenClickingOption4_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.onNode(option4).performClick()
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
    }
}
