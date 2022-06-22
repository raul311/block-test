package com.raul311.employeedirectory.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul311.employeedirectory.models.EmployeeResponse
import com.raul311.employeedirectory.network.ApiState
import com.raul311.employeedirectory.network.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class DirectoryViewModel(private var repository: EmployeeRepository) : ViewModel() {

    private val TAG: String = "DirectoryViewModel"
    private val _employeeListState = MutableStateFlow<ApiState>(ApiState.Empty)
    val employeeListState: StateFlow<ApiState> = _employeeListState

    fun getEmployeeData() = viewModelScope.launch(Dispatchers.IO) {
        _employeeListState.value = ApiState.Loading
        repository.getEmployeeList()
            .catch { e ->
                Log.e(TAG, "failure ${e.message}")
                _employeeListState.value = ApiState.Failure(e)
            }
            .collect { data ->
                when {
                    data.employees.isEmpty() -> {
                        Log.w(TAG, "empty response")
                        _employeeListState.value = ApiState.Empty
                    }
                    isResponseValid(data) -> {
                        Log.v(TAG, "success call")
                        data.employees.sortBy { it.fullName }
                        _employeeListState.value = ApiState.Success(data)
                    }
                    else -> {
                        Log.e(TAG, "Malformed data")
                        _employeeListState.value = ApiState.Failure(Exception("Malformed Data"))
                    }
                }
            }
    }

    /**
     * According to the requirements, if one employee object is return as "malformed" we should
     * discard the entire response.
     *
     * @return true if all employee object have the correct data, false otherwise
     */
    fun isResponseValid(data: EmployeeResponse): Boolean {
        if (data.employees.isEmpty()) {
            return false
        }
        data.employees.forEach {
            if (it.fullName.isNullOrEmpty()
                || it.emailAddress.isNullOrEmpty()
                || it.photoUrlLarge.isNullOrEmpty()
                || it.team.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }
}