package ar.edu.unlam.mobile.scaffold.ui.screens.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffold.data.map.ILocationService
import ar.edu.unlam.mobile.scaffold.domain.map.GetLocationUseCase
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class ScreenMapTest {

    @get: Rule
    val compose= createComposeRule()


    @Test
    fun whenHavingDefaultStatesMap_VerifyIfAllViewsExists() {
        val fakeLatLng = LatLng(0.0, 0.0)
        val fakeCameraPositionState = CameraPositionState()
       // val viewModel = mockk<MapViewModel>()


                compose.setContent {
            ComicWarTheme {
                MapScreen(currentPosition = fakeLatLng, cameraState = fakeCameraPositionState)
            }
        }
        compose.onNodeWithTag(testTag = "MapScreen title").assertExists()
        compose.onNodeWithTag(testTag = "MapScren googleMap").assertExists()
    }
}