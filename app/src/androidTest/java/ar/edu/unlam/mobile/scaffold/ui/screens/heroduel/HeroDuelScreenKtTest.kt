package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class HeroDuelScreenKtTest {

    // compose rule is required to get access to the composable component
    @get: Rule
    val compose = createComposeRule()

    @Test
    fun whenHavingDefaultStatesToFinishedDuelUi_VerifyAllComposableExists() {
        compose.setContent {
            FinishedDuelUi()
        }
        compose.onNodeWithContentDescription(label = "FinishedDuelUi background").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi result text").assertExists()
        compose.onNodeWithTag(testTag = "FinishedDuelUi final score").assertExists()
    }

    @Test
    fun whenHavingDefaultStatesToDuelUi_VerifyAllComposableExists() {
        compose.setContent {
            DuelUi()
        }
        compose.onNodeWithTag(testTag = "DuelUi game score").assertExists()
        compose.onNodeWithTag(testTag = "DuelUi adversary card").assertExists()
        compose.onNodeWithTag(testTag = "DuelUi action menu").assertExists()
        compose.onNodeWithContentDescription(label = "DuelUi background").assertExists()
    }

    @Test
    fun whenHavingDefaultStatesToSelectCardUi_VerifyAllComposablesExists() {
        compose.setContent {
            SelectCardUi()
        }
        compose.onNode(
            matcher = hasContentDescription(value = "SelectCardUi background")
        ).assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi player card").assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi play card button").assertExists()
        compose.onNodeWithTag(testTag = "SelectCardUi player deck").assertExists()
    }
}
