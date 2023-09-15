package ar.edu.unlam.mobile.scaffold.di

import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.repository.GameRepository
import ar.edu.unlam.mobile.scaffold.data.repository.HeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.IHeroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHeroRepository(api: HeroService, db: HeroDao): IHeroRepository {
        return HeroRepository(api,db)
    }

    @Singleton
    @Provides
    fun provideGameRepository(repo: IHeroRepository): GameRepository {
        return GameRepository(repo)
    }
}