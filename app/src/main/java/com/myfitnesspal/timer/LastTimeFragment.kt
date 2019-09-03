package com.myfitnesspal.timer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.myfitnesspal.searcharticles.R
import kotlinx.android.synthetic.main.last_time_fragment.*

class LastTimeFragment : Fragment() {

    companion object {
        fun newInstance() = LastTimeFragment()
    }

    private lateinit var viewModel: LastTimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.last_time_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(LastTimeViewModel::class.java)
            viewModel.lastTime.observe(this, Observer {
                tv_last_time.text = it.toString()
            })
        }
    }

}
