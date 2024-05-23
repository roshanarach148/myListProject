package com.example.sampleproject.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.data.repository.CountryRepository
import com.example.sampleproject.network.model.Country
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = CountryRepository()

    val countriesLiveData: LiveData<List<Country>> = repository.countries
    val errorMessageLiveData = MutableLiveData<String>()
    val isShowProgressLiveData = MutableLiveData<Boolean>()

    fun fetchCountries() {
        isShowProgressLiveData.value = true
        viewModelScope.launch {
            try {
                repository.getCountriesFromAPI()
            } catch (e: Exception) {
                errorMessageLiveData.value = e.message ?: "Unknown error occurred"
            } finally {
                isShowProgressLiveData.value = false
            }
        }
    }
}
