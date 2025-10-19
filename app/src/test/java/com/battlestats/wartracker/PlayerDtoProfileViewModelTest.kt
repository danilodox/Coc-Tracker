package com.battlestats.wartracker

import com.battlestats.wartracker.domain.util.Result
import com.battlestats.wartracker.domain.model.Clan
import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.usecase.CalculateTownHallProgressUseCase
import com.battlestats.wartracker.domain.usecase.GetPlayerProfileUseCase
import com.battlestats.wartracker.domain.usecase.PlayerProfileData
import com.battlestats.wartracker.domain.usecase.ToggleFavoriteStatusUseCase
import com.battlestats.wartracker.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private lateinit var getPlayerProfileUseCase: GetPlayerProfileUseCase
    private lateinit var toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase
    private lateinit var calculateTownHallProgressUseCase: CalculateTownHallProgressUseCase
    private lateinit var viewModel: HomeViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getPlayerProfileUseCase = mockk()
        toggleFavoriteStatusUseCase = mockk(relaxed = true) //relaxed to not use this test for now
        calculateTownHallProgressUseCase = mockk(relaxed = true)

        viewModel = HomeViewModel(
            getPlayerProfileUseCase,
            toggleFavoriteStatusUseCase,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPlayerData should update uiState and emit events on success`() = runTest {
        // Arrange
        val tag = "#123ABC"

        //create all data domain
        val fakeClan = Clan(tag = "#CLAN123", name = "Clashers", clanLevel = 10)

        val fakePlayer = Player(
            tag = tag,
            name = "Danilo",
            expLevel = 100,
            townHallLevel = 15,
            clan = fakeClan
        )

        val profileData = PlayerProfileData(player = fakePlayer, isFavorite = true)

        //configure the mock of the Usecase to return success
        coEvery { getPlayerProfileUseCase(tag) } returns Result.Success(profileData)

        viewModel.getPlayerData(tag)

        //clear all coroutines after the test is finished
        advanceUntilIdle()


        // Assert
        //verify all final state of UI with domain model
        val finalState = viewModel.uiState.value

        assertEquals(false, finalState.isLoading)
        assertEquals(fakePlayer, finalState.player)
        assertEquals(true, finalState.isFavorite) // Verifica se o status de favorito foi atualizado
        assertEquals(false, finalState.isError)

    }

    @Test
    fun `getPlayerData should update uiState with error on failure`() = runTest {
        // Arrange
        val tag = "#ERROR"
        val errorMessage = "Player not found"

        // Configure the UseCase mock to return an error result
        coEvery { getPlayerProfileUseCase(tag) } returns Result.Error(errorMessage)

        // Act
        viewModel.getPlayerData(tag)
        advanceUntilIdle()

        // Assert
        val finalState = viewModel.uiState.value

        assertEquals(false, finalState.isLoading)
        assertEquals(true, finalState.isError)
        assertEquals(null, finalState.player) //The player should be void in case of error
    }
}