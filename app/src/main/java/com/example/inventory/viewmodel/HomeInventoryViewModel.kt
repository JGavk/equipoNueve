package com.example.inventory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.inventory.data.InventoryDB
import com.example.inventory.data.InventoryRepository
import com.example.inventory.model.Inventory

class HomeInventoryViewModel(
    application: Application,
    private val repository: InventoryRepository
) : AndroidViewModel(application) {

    val inventoryItems: LiveData<List<Inventory>> =
        repository.getInventoryItems().asLiveData()

    constructor(application: Application) : this(
        application,
        InventoryRepository(
            InventoryDB.getDatabase(application).inventoryDao()
        )
    )
}
