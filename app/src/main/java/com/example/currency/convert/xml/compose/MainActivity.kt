package com.example.currency.convert.xml.compose

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currency.convert.xml.compose.common.CurrencyEvent
import com.example.currency.convert.xml.compose.databinding.ActivityMainBinding
import com.example.currency.convert.xml.compose.ui.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding.root)


        binding.btnConvert.setOnClickListener{
            viewModel.convert(
                binding.amountText.text.toString(),
                binding.idSpinnerFrom.selectedItem.toString(),
                binding.idSpinnerTo.selectedItem.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.currencyState.collect{ event ->
                when(event){
                    CurrencyEvent.Empty -> Unit
                    CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true

                    }
                    is CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.txtResult.setTextColor(Color.RED)
                        binding.txtResult.text = event.errorText
                    }
                    is CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.txtResult.setTextColor(Color.BLACK)
                        binding.txtResult.text = event.resultText
                    }
                }
            }
        }


    }
}