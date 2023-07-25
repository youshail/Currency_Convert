package com.example.currency.convert.xml.compose

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currency.convert.xml.compose.common.CurrencyEvent
import com.example.currency.convert.xml.compose.databinding.ActivityMainBinding
import com.example.currency.convert.xml.compose.ui.CurrencyViewModel
import com.example.currency.convert.xml.compose.ui.compose.ConvertScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                ConvertScreen(viewModel = viewModel)
            }
        }



//Notice that launchWhenStarted API is now deprecated because it could cause work to hang
// for a very long time.

//Currently for a one-shot one-time delay it's recommended to lifecycleScope.launch()
// a new job and use withStarted{ } method within it to cause a suspension until started state
// is reached.

        lifecycleScope.launch {
            viewModel.currencyState.collect{ event ->
                when(event){
                    CurrencyEvent.Empty -> Unit
                    CurrencyEvent.Loading -> {
                        viewModel.isVisible = true

                    }
                    is CurrencyEvent.Failure -> {
                        viewModel.isVisible = false
                        //binding.txtResult.setTextColor(Color.RED)
                        viewModel.txtResult = event.errorText
                    }
                    is CurrencyEvent.Success -> {
                        viewModel.isVisible = false
                        //binding.txtResult.setTextColor(Color.BLACK)
                        viewModel.txtResult = event.resultText
                    }
                }
            }
        }


    }
}