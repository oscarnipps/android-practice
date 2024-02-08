package com.android.android_practice.navigation_component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentGraphCDesABinding

class GraphCDesA : Fragment() {

    private lateinit var binding : FragmentGraphCDesABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_c_des_a,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavToGraphCDesA.setOnClickListener {

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}