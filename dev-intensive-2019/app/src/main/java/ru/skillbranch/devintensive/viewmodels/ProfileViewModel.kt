package ru.skillbranch.devintensive.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class ProfileViewModel : ViewModel() {

    private val repository = PreferencesRepository

    private val _appTheme = MutableLiveData<Int>()
    val theme: LiveData<Int> = _appTheme

    private val _profileData = MutableLiveData<Profile>()
    val profileData: LiveData<Profile> = _profileData

    init {
        _profileData.value = repository.getProfile()
        _appTheme.value = repository.getAppTheme()
    }

    fun saveProfileData(profile: Profile) {
        repository.saveProfile(profile)
        _profileData.value = profile
    }

    fun switchTheme() {
        if (_appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            _appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            _appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(_appTheme.value!!)
    }

}