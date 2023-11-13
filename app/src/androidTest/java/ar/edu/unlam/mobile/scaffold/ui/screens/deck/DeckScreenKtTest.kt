package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeckScreenKtTest {

    @Rule
    val compose = createComposeRule()

    @Test
    fun deckScreen() {
        compose.setContent {
            DeckScreen()
        }
        compose.onNodeWithTag("TestDeckScreen pantalla fondo").assertExists()
        compose.onNodeWithTag("DeckScreen titulo pantalla").assertExists()
        //compose.onNodeWithTag("DeckScreen Hero Card").assertExists()
    }

}