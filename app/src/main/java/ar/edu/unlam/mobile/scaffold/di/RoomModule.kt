package ar.edu.unlam.mobile.scaffold.di

import android.content.Context
import androidx.room.Room
import ar.edu.unlam.mobile.scaffold.data.database.HeroDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val HERO_DATABASE_NAME = "hero_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = HeroDataBase::class.java,
            name = HERO_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideHeroDao(db: HeroDataBase) = db.getHeroDao()
}
