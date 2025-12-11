package com.example.inventory.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.inventory.data.InventoryRepository
import com.example.inventory.model.Inventory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeInventoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()   // reutiliza la regla que ya tienes

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var repository: InventoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // Valor por defecto
        whenever(repository.getInventoryItems()).thenReturn(MutableStateFlow(emptyList()))
    }

    @Test
    fun `inventoryItems expone los datos del repository`() = runTest {
        // ARRANGE
        val item1: Inventory = mock()
        val item2: Inventory = mock()
        val expectedList = listOf(item1, item2)

        whenever(repository.getInventoryItems()).thenReturn(MutableStateFlow(expectedList))

        // ACT
        val viewModel = HomeInventoryViewModel(application, repository)
        advanceUntilIdle()

        val result = viewModel.inventoryItems.getOrAwaitValue() // usa la extensión del otro archivo

        // ASSERT
        assertEquals(expectedList, result)
    }
}
