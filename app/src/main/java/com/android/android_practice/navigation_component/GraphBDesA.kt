package com.android.android_practice.navigation_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentGraphADesBBinding
import com.android.android_practice.databinding.FragmentGraphBDesABinding

class GraphBDesA : Fragment() {

    private lateinit var binding : FragmentGraphBDesABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_b_des_a,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavToGraphCDesA.setOnClickListener {
            findNavController().navigate(R.id.action_graphBDesA_to_graphCDesA)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}