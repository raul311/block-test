package com.raul311.employeedirectory.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.raul311.employeedirectory.R
import com.raul311.employeedirectory.network.ApiState
import com.raul311.employeedirectory.network.EmployeeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DirectoryFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: DirectoryAdapter
    private lateinit var swipeContainer: SwipeRefreshLayout

    companion object {
        fun newInstance() = DirectoryFragment()
    }

    private lateinit var viewModel: DirectoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            EmployeeViewModelFactory(EmployeeRepository())
        )[DirectoryViewModel::class.java]
        viewModel.getEmployeeData()

        recyclerview = activity?.findViewById(R.id.recyclerview)!!
        recyclerview.layoutManager = LinearLayoutManager(activity)

        swipeContainer = activity?.findViewById(R.id.swipeContainer)!!
        swipeContainer.setOnRefreshListener { // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            getEmployeeList()
        }
        getEmployeeList()

    }

    private fun getEmployeeList() {
        lifecycleScope.launch {
            viewModel.employeeListState.collect {
                when(it) {
                    is ApiState.Loading -> {

                    }
                    is ApiState.Empty -> {

                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.Success -> {
                        println("raul - size = ${it.data.employees.size}")
                        adapter = DirectoryAdapter(it.data.employees)
                        recyclerview.adapter = adapter
                        swipeContainer.isRefreshing = false
                    }
                }
            }
        }
    }
}