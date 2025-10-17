package com.battlestats.wartracker

import app.cash.turbine.test
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.remote.model.ClanDto
import com.battlestats.wartracker.data.remote.model.PlayerDto
import com.battlestats.wartracker.domain.repository.PlayerRepository
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
class PlayerDtoProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PlayerRepository
    private lateinit var viewModel: PlayerProfileViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = PlayerProfileViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPlayerData should update uiState and emit events on success`() = runTest {
        // Arrange
        val tag = "#123ABC"
        val fakePlayerDto = PlayerDto(
            tag = tag,
            name = "Danilo",
            expLevel = 100,
            townHallLevel = 15,
            clan = ClanDto(
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

        coEvery { repository.getPlayerDetails(tag) } returns fakePlayerDto
        coEvery { repository.getByTag(tag) } returns fakePlayerDto.toEntity()
        //coEvery { localRepository.getByTag(tag) } returns null

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

        viewModel.uiState.test {
            
            val loadedState = awaitItem()
            assertEquals(false, loadedState.isLoading)
            assertEquals(fakePlayerDto, loadedState.playerDto)
            cancelAndIgnoreRemainingEvents()
        }


        uiEventJob.join()

    }
}