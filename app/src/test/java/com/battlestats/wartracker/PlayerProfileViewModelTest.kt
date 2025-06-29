package com.battlestats.wartracker

import app.cash.turbine.test
import com.battlestats.wartracker.data.local.repository.LocalPlayerRepository
import com.battlestats.wartracker.data.model.Achievement
import com.battlestats.wartracker.data.model.BuilderBaseLeague
import com.battlestats.wartracker.data.model.Clan
import com.battlestats.wartracker.data.model.Hero
import com.battlestats.wartracker.data.model.HeroEquipment
import com.battlestats.wartracker.data.model.Label
import com.battlestats.wartracker.data.model.LegendStatistics
import com.battlestats.wartracker.data.model.Player
import com.battlestats.wartracker.data.model.PlayerHouse
import com.battlestats.wartracker.data.model.Spell
import com.battlestats.wartracker.data.model.Troop
import com.battlestats.wartracker.data.repository.PlayerRepository
import com.battlestats.wartracker.ui.player_profile.PlayerProfileUiEvent
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PlayerRepository
    private lateinit var localRepository: LocalPlayerRepository
    private lateinit var viewModel: PlayerProfileViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        localRepository = mockk()
        viewModel = PlayerProfileViewModel(repository, localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPlayerData should update uiState and emit events on success`() = runTest {
        // Arrange
        val tag = "#123ABC"
        val fakePlayer = Player(
            tag = tag,
            name = "Danilo",
            expLevel = 100,
            townHallLevel = 15,
            clan = Clan(
                name = "Clashers",
                tag = "#CLAN123",
                clanLevel = null,
                badgeUrls = null,
            ),
            league = null,
            builderBaseLeague = null,
            role = null,
            warPreference = null,
            attackWins = null,
            defenseWins = null,
            townHallWeaponLevel = null,
            legendStatistics = null,
            troops = null,
            heroes = null,
            heroEquipment = null,
            spells = null,
            labels = null,
            trophies = null,
            bestTrophies = null,
            donations = null,
            donationsReceived = null,
            builderHallLevel = null,
            builderBaseTrophies = null,
            bestBuilderBaseTrophies = null,
            warStars = null,
            achievements = null,
            clanCapitalContributions = null,
            playerHouse = null,
        )

        coEvery { repository.getPlayerDetails(tag) } returns fakePlayer

        // Assert: verifica eventos emitidos
        val uiEventJob = launch {
            viewModel.uiEvent.test {
                val event1 = awaitItem()
                val event2 = awaitItem()

                val events = listOf(event1, event2)

                assertTrue(events.contains(PlayerProfileUiEvent.NavigateToNextScreen))
                assertTrue(events.contains(PlayerProfileUiEvent.ShowToast("Player loaded successfully")))

                cancelAndIgnoreRemainingEvents()
            }
        }

        // Act
        viewModel.getPlayerData(tag)
        advanceUntilIdle()


        // Assert: verifica o uiState
        viewModel.uiState.test {

            val loadedState = awaitItem()
            assertEquals(false, loadedState.isLoading)
            assertEquals(fakePlayer, loadedState.player)
            cancelAndIgnoreRemainingEvents()
        }
        uiEventJob.join()

    }
}