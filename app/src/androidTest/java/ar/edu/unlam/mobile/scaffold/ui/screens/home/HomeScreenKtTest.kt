package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenHavingDefaultStates_VerifyIfAllViewsExists() {
        composeTestRule.setContent {
            ComicWarTheme {
                HomeUi()
            }
        }
        composeTestRule.onNodeWithTag("background").assertExists()
        composeTestRule.onNodeWithTag("nav duel button").assertExists()
        composeTestRule.onNodeWithTag("nav quiz button").assertExists()
        composeTestRule.onNodeWithTag("nav map button").assertExists()
        composeTestRule.onNodeWithTag("nav collection button").assertExists()
        composeTestRule.onNodeWithTag(testTag = "progress bar", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag(testTag = "nav collection button", useUnmergedTree = true).assertExists()
    }
}
