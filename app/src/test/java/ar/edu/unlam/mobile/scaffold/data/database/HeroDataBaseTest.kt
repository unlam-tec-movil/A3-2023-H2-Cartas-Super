package ar.edu.unlam.mobile.scaffold.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ConnectionsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ImageEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.PowerstatsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.WorkEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [31])
class HeroDataBaseTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var heroDataBase: HeroDataBase
    private lateinit var heroDao: HeroDao

    @Before
    fun setUp() {
        // val context = ApplicationProvider.getApplicationContext<Context>()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        heroDataBase = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = HeroDataBase::class.java
            ).allowMainThreadQueries()
            .build()
        heroDao = heroDataBase.getHeroDao()
    }

    @Test
    fun insertHeroEntity() = runTest {
        val heroEntity = HeroEntity(
            id = 1,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test",
            work = WorkEntity()
        )

        heroDao.insertHero(hero = heroEntity)

        val allHeroes = heroDao.getAll()

        Truth.assertThat(allHeroes).contains(heroEntity)
        Truth.assertThat(allHeroes).containsExactly(heroEntity)
        Truth.assertThat(allHeroes).hasSize(1)
        Truth.assertThat(allHeroes).isNotEmpty()
    }
}
