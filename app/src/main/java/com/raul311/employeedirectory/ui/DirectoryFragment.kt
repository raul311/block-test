package com.raul311.employeedirectory.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var noResults: TextView
    private lateinit var failure: TextView

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

        noResults = activity?.findViewById(R.id.noResults)!!
        failure = activity?.findViewById(R.id.failure)!!

        swipeContainer = activity?.findViewById(R.id.swipeContainer)!!
        swipeContainer.setOnRefreshListener { getEmployeeList() }
        getEmployeeList()
    }

    private fun getEmployeeList() {
        lifecycleScope.launch {
            viewModel.employeeListState.collect {
                swipeContainer.isRefreshing = false
                when(it) {
                    is ApiState.Empty -> {
                        setViewsOnEmpty()
                    }
                    is ApiState.Failure -> {
                        setViewsOnFailure()
                    }
                    is ApiState.Success -> {
                        println("raul - size = ${it.data.employees.size}")
                        adapter = DirectoryAdapter(it.data.employees)
                        recyclerview.adapter = adapter
                        setViewsOnSuccess()
                    }
                    else -> { // Loading state
                        swipeContainer.isRefreshing = true
                    }
                }
            }
        }
    }

    private fun setViewsOnFailure() {
        noResults.visibility = View.GONE
        recyclerview.visibility = View.GONE
        failure.visibility = View.VISIBLE
    }

    private fun setViewsOnSuccess() {
        recyclerview.visibility = View.VISIBLE
        noResults.visibility = View.GONE
        failure.visibility = View.GONE
    }

    private fun setViewsOnEmpty() {
        recyclerview.visibility = View.GONE
        noResults.visibility = View.VISIBLE
        failure.visibility = View.GONE
    }
}