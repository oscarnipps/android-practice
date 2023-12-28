package com.android.android_practice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentBBinding

class FragmentB : Fragment() {

    private lateinit var binding : FragmentBBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.resultTitle.text = it.getString("session")
        }

        binding.finishBtn.setOnClickListener {
            setFragmentResult(
                "fragment-b-request-key", bundleOf("name" to binding.commentBox.text.trim())
            )

            parentFragmentManager.popBackStack()
        }


    }
}