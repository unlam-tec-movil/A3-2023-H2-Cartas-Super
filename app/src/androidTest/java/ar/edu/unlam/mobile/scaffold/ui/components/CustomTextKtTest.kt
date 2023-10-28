package ar.edu.unlam.mobile.scaffold.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class CustomTextKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customTitle() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTitle()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun customTextLabelMedium() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextLabelMedium()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun customTextLabelSmall() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextLabelSmall()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }

    @Test
    fun customTextBodyLarge() {
        composeTestRule.setContent {
            ComicWarTheme {
                CustomTextBodyLarge()
            }
        }
        composeTestRule.onNodeWithTag("text").assertExists()
    }
}
