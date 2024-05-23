package com.example.sampleproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleproject.databinding.ActivityMainBinding
import com.example.sampleproject.ui.adapters.CountriesRecyclerAdapter
import com.example.sampleproject.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CountriesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()

        viewModel.countriesLiveData.observe(this, Observer { countries ->
            countries?.let {
                adapter.submitCountriesList(it)
            }
        })

        viewModel.isShowProgressLiveData.observe(this, Observer { isShowProgress ->
            binding.mainProgressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })

        if (savedInstanceState == null) {
            viewModel.fetchCountries()
        }
    }

    private fun setupRecyclerView() {
        adapter = CountriesRecyclerAdapter()
        binding.countryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}
