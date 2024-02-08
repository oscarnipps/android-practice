package com.android.android_practice.navigation_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.android_practice.R
import com.android.android_practice.R.layout.fragment_graph_a_des_b
import com.android.android_practice.databinding.FragmentBaseDesABinding
import com.android.android_practice.databinding.FragmentGraphADesBBinding

class GraphADesB : Fragment() {

    private lateinit var binding : FragmentGraphADesBBinding
    private val args : GraphADesBArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, fragment_graph_a_des_b,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavToGraphA.setOnClickListener {
            findNavController().navigate(
                R.id.action_graphADesA_to_graphADesB,
                bundleOf("graphADesBStringArg" to "args from Graph A Destination A")
            )
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}