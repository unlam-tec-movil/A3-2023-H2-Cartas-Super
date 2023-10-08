package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizScreenKtTest {

    // compose rule is required to get access to the composable component
    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    @Test
    fun verifyIfAllViewsExists() {
        composeTestRule.onNodeWithTag("background image").assertExists()
        // composeTestRule.onNodeWithTag("loading composable").assertExists()
        composeTestRule.onNodeWithTag("title").assertExists()
        composeTestRule.onNodeWithTag("hero image").assertExists()
        composeTestRule.onNodeWithTag("answer panel").assertExists()
        // composeTestRule.onNodeWithTag("Result popup").assertExists()
    }
}
