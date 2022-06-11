package com.raul311.employeedirectory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul311.employeedirectory.network.ApiState
import com.raul311.employeedirectory.network.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DirectoryViewModel(private var repository: EmployeeRepository) : ViewModel() {

    private val _employeeListState = MutableStateFlow<ApiState>(ApiState.Empty)
    val employeeListState: StateFlow<ApiState> = _employeeListState

//    private val employeeList = MutableStateFlow(ApiState.Empty)

    fun getEmployeeData() = viewModelScope.launch {
        _employeeListState.value = ApiState.Loading
        repository.getEmployeeList()
            .catch { e ->
                println("raul - failure ${e.message}")
                _employeeListState.value = ApiState.Failure(e)
            }
            .collect { data ->
                println("raul - success")
                _employeeListState.value = ApiState.Success(data)
            }
    }
}