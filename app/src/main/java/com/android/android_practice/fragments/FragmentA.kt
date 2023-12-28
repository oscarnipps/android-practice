package com.android.android_practice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.android.android_practice.R
import com.android.android_practice.databinding.FragmentABinding

class FragmentA : Fragment(){

    private lateinit var binding :FragmentABinding
    private var nameValue :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setFragmentResultListener("fragment-b-request-key"){requestKey, bundle ->
             bundle.containsKey("name")
             bundle.get("name")
            nameValue = bundle.get("name").toString()
            val size = nameValue?.length

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_a,container,false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.nextBtn.setOnClickListener {
            val bundle = bundleOf("session" to "user1212")

            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("")
                .replace(R.id.container_view,FragmentB::class.java,bundle)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.resultTitle.text = nameValue
    }


}