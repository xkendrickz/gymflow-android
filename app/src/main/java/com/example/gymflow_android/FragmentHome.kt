package com.example.gymflow_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymflow_android.adapters.ScheduleAdapter

class FragmentHome : Fragment() {

    private lateinit var scheduleRecyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView)
        scheduleAdapter = ScheduleAdapter()
        scheduleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        scheduleRecyclerView.adapter = scheduleAdapter
        loadSchedule()
    }

    private fun loadSchedule() {
        // Replace with actual API call when endpoint is ready
        // viewLifecycleOwner.lifecycleScope.launch {
        //     val response = RetrofitClient.api.getSchedule()
        //     if (response.isSuccessful) scheduleAdapter.setData(response.body()!!.data)
        // }
        scheduleAdapter.setData(emptyList())
    }
}