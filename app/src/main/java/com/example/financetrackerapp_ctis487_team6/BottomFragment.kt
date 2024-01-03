package com.example.financetrackerapp_ctis487_team6

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


import com.example.financetrackerapp_ctis487_team6.databinding.FragmentBottomBinding

class BottomFragment : Fragment() {
        private lateinit var binding: FragmentBottomBinding

        override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            binding = FragmentBottomBinding.inflate(inflater, container, false)

            return binding.root

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }

    fun updateText(s: String) {
        binding.tvResultBottomFragment.text=s
        Log.i("FRAGMENT","updateText() in BottomFragment")
    }
    fun updateDetailsText(details: String) {
        val textViewDetails = view?.findViewById<TextView>(R.id.tvResultBottomFragment)
        textViewDetails?.text = "Details in Bottom Fragment: $details"
    }
}