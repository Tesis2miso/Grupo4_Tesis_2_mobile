package com.example.dermoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoapp.R
import com.example.dermoapp.adapters.HomeConsultsAdapter
import com.example.dermoapp.databinding.FragmentConfigurationBinding
import com.example.dermoapp.databinding.FragmentHomeBinding
import com.example.dermoapp.models.Consult
import com.example.dermoapp.views.HomeActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: HomeConsultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.homeConsultsList
        recyclerView.layoutManager = GridLayoutManager(
            activity, 1, GridLayoutManager.VERTICAL, false
        )
        recyclerViewAdapter = HomeConsultsAdapter(ArrayList<Consult>())
        recyclerView.adapter = recyclerViewAdapter
        loadConsults()

        return view
    }

    fun loadConsults() {
        val activity: HomeActivity = requireActivity() as HomeActivity
        activity.viewModel.loadConsults { consults ->
            recyclerViewAdapter.consults = consults
            recyclerViewAdapter.notifyDataSetChanged()

            if(consults.isEmpty()) {
                setEmptyState()
            } else {
                setNonEmptyState()
            }
        }
    }

    fun setEmptyState() {
        binding.homeConsultsList.visibility = View.GONE
        binding.homeDontHaveConsultsLabel.visibility = View.VISIBLE
    }

    fun setNonEmptyState() {
        binding.homeConsultsList.visibility = View.VISIBLE
        binding.homeDontHaveConsultsLabel.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}