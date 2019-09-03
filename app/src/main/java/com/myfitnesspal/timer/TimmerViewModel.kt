package com.myfitnesspal.timer

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class TimmerViewModel : ViewModel() {


    val lisOfBooks = listOf<String>("T", "a", "as", "as" , "bb" , "dd")
    val curTime = MutableLiveData<Long>()
    val isFinished = MutableLiveData<Boolean>()



    companion object {
        private const val  DONE = 0L
        private const val  ONE_SEC = 1000L
        private const val COUNTDOWN_TIME = 100000L
    }

     val timer : CountDownTimer


    init {
        timer = object  : CountDownTimer(
            COUNTDOWN_TIME,
            ONE_SEC
        ){
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                curTime.value = millisUntilFinished
            }
        }
        timer.start()

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}
