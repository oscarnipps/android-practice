package com.android.android_practice.navigation_component

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentGraphADesABinding

class GraphADesA : Fragment() {
    private lateinit var binding : FragmentGraphADesABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_graph_a_des_a,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavToGraphADesB.setOnClickListener {
            //send args option 1 using the directions api :
            // val action = GraphADesADirections.actionGraphADesAToGraphADesB(
            //     graphADesBStringArg = "args from GraphA Destination A"
            // )
            // findNavController().navigate(action)

            //send args option 2 using bundle :
            findNavController().navigate(
                R.id.action_graphADesA_to_graphADesB,
                bundleOf("graphADesBStringArg" to "args from Graph A Destination A")
            )
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNavToGraphBDesA.setOnClickListener {
            findNavController().navigate(
                R.id.action_graphADesA_to_graphBDesA
            )
        }

        binding.btnNavToGraphCDesB.setOnClickListener {
            findNavController().navigate(
                Uri.parse("androidpractice://destinations/graphc/b")
            )
        }
    }
}