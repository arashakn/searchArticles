package com.myfitnesspal.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class LastTimeViewModel : ViewModel() {

    val lastTime = MutableLiveData<Long>()

}
