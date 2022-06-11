package com.raul311.employeedirectory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul311.employeedirectory.network.EmployeeRepository

class EmployeeViewModelFactory(private val repository: EmployeeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DirectoryViewModel(repository) as T
    }

}

