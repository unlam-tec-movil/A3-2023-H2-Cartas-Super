package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test

class HeroDetailScreenKtTest {

    @get: Rule
    val composeRule = createComposeRule()

    @Test
    fun whenProvidingDefaultStateToHeroDetailUi_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroDetailUi()
            }
        }
        composeRule.onNodeWithTag("heroDetailUi background").assertExists()
        composeRule.onNodeWithTag("heroDetailUi Loading").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi profile image").assertExists()
        composeRule.onNodeWithTag("heroDetailUi character id and name").assertExists()
        composeRule.onNodeWithTag("heroDetailUi navigate to qr button").assertExists()
        composeRule.onNodeWithTag("heroDetailUi hero data").assertExists()
    }

    @Test
    fun whenProvidingIsLoadingTrueToHeroDetailUi_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroDetailUi(isLoading = true)
            }
        }
        composeRule.onNodeWithTag("heroDetailUi background").assertExists()
        composeRule.onNodeWithTag("heroDetailUi Loading").assertExists()
        composeRule.onNodeWithTag("heroDetailUi profile image").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi character id and name").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi navigate to qr button").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi hero data").assertDoesNotExist()
    }

    @Test
    fun whenProvidingDefaultStateToHeroData_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroData()
            }
        }
        composeRule.onNodeWithTag("title stats").assertExists()
        composeRule.onNodeWithTag("stat text").assertExists()
        composeRule.onNodeWithTag("title biography").assertExists()
        composeRule.onNodeWithTag("biography text").assertExists()
        composeRule.onNodeWithTag("title appearance").assertExists()
        composeRule.onNodeWithTag("heroData appearance text").assertExists()
        composeRule.onNodeWithTag("title profession").assertExists()
        composeRule.onNodeWithTag("heroData profession text").assertExists()
        composeRule.onNodeWithTag("title connections").assertExists()
        composeRule.onNodeWithTag("heroData text connections").assertExists()
    }
}
