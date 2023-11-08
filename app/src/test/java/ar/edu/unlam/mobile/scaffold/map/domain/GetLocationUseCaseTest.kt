package ar.edu.unlam.mobile.scaffold.map.domain

import ar.edu.unlam.mobile.scaffold.ui.screens.map.data.ILocationService
import ar.edu.unlam.mobile.scaffold.ui.screens.map.domain.GetLocationUseCase
import com.google.android.gms.maps.model.LatLng
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetLocationUseCaseTest {

    @Mock
    private lateinit var locationService: ILocationService

    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getLocationUseCase = GetLocationUseCase(locationService)
    }

    @Test
    fun invokeShouldReturnFlowOfLatLng() = runBlockingTest {
        // Given
        val expectedLatLng = LatLng(10.0, 20.0)
        `when`(locationService.requestLocationUpdates()).thenReturn(flowOf(expectedLatLng))

        // When
        val result = getLocationUseCase.invoke()

        // Then
        result.collect {
            assertEquals(expectedLatLng, it)
        }
    }
}