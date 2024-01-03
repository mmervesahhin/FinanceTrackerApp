package com.example.financetrackerapp_ctis487_team6

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.ctis487.retrofit.Currency
import com.example.financetrackerapp_ctis487_team6.databinding.FragmentTopBinding

class TopFragment : Fragment() {
    private lateinit var binding: FragmentTopBinding

    interface TopFragmentListener {
        fun onButtonClick(text: String)
    }

    var activityCallback: TopFragment.TopFragmentListener? = null

    fun updateCurrencyData(currencyData: List<Currency>) {
        val firstCurrency = currencyData[0]
        val secondCurrency = currencyData[1]
    //    val thirdCurrency = currencyData[2]

        val resultString = "Currency: ${firstCurrency.name},  Value: ${firstCurrency.value} ₺\n\nCurrency: ${secondCurrency.name},  Value: ${secondCurrency.value } ₺\n"


        val textViewCurrency = view?.findViewById<TextView>(R.id.textView)
        textViewCurrency?.text = resultString
        binding.btTopFragmentOk.setOnClickListener {
            Toast.makeText(context, "Button Ok is clicked",Toast.LENGTH_LONG).show()
            activityCallback!!.onButtonClick("\n\nDetail:${firstCurrency.details}\n\nDetail: ${secondCurrency.details}")

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityCallback = context as TopFragmentListener
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}