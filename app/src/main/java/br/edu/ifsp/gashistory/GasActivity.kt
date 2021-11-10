package br.edu.ifsp.gashistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.gashistory.databinding.ActivityGasBinding
import br.edu.ifsp.gashistory.model.Gas
import java.util.*

class GasActivity : AppCompatActivity() {

    private val activityGasBinding: ActivityGasBinding by lazy {
        ActivityGasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityGasBinding.root)

        val datePicker = activityGasBinding.datePicker
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        datePicker.setMaxDate(today.timeInMillis);

        activityGasBinding.salvarBt.setOnClickListener { view: View? ->
            val text = "${datePicker.dayOfMonth}-${datePicker.month}-${datePicker.year}"
            val gas = Gas(
                text,
                activityGasBinding.valorEt.text.toString().toDouble()
            )
            val resultadoIntent = Intent()
            resultadoIntent.putExtra(MainActivity.EXTRA_GAS, gas)
            //se for edicao, devolver posicao
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }

    }
}