package com.android.android_practice.navigation_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentBaseDesABinding

class BaseDesA : Fragment() {

    private lateinit var binding : FragmentBaseDesABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_base_des_a,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavToGraphADesA.setOnClickListener {
            findNavController().navigate(R.id.action_baseDesA_to_graph_a)
        }
    }
}