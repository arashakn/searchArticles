package com.myfitnesspal.timer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.myfitnesspal.searcharticles.R
import kotlinx.android.synthetic.main.timmer_fragment.*

class TimmerFragment : Fragment() {

    companion object {
        fun newInstance() = TimmerFragment()
    }

    private lateinit var viewModel: TimmerViewModel
    private lateinit var lastTimeViewModel: LastTimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timmer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TimmerViewModel::class.java)
        observe()
        setViews()
    }

    fun observe(){
        viewModel.curTime.observe(this, Observer {
            curTime.text = "current time is : ${it.toString()}"
        } )
    }

    fun setViews(){
        btn_next.setOnClickListener{
            activity?.let {

                lastTimeViewModel = ViewModelProviders.of(it).get(LastTimeViewModel::class.java)
                lastTimeViewModel.lastTime.value = viewModel.curTime.value

                it.supportFragmentManager.beginTransaction().replace(R.id.screen_container,
                    LastTimeFragment()
                ).addToBackStack(null).commit()

            }
        }
    }

}
