package ar.edu.unlam.mobile.scaffold.ui.components.hero

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class BiographyComposablesKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToHeroBiography_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                HeroBiography()
            }
        }
        composeTestRule.onNodeWithTag("biography text").assertExists()
    }
}
