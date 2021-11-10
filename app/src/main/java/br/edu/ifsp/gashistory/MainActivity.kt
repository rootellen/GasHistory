package br.edu.ifsp.gashistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.gashistory.adapter.GasRvAdapter
import br.edu.ifsp.gashistory.controller.GasController
import br.edu.ifsp.gashistory.databinding.ActivityMainBinding
import br.edu.ifsp.gashistory.model.Gas

class MainActivity : AppCompatActivity(), OnGasClickListener {

    companion object Extras {
        const val EXTRA_GAS = "EXTRA_GAS"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var gasActivityResultLauncher: ActivityResultLauncher<Intent>

    private val gasList: MutableList<Gas> by lazy {
        gasController.buscarGas()
    }

    private val gasController: GasController by lazy {
        GasController(this)
    }

    private val gasAdapter: GasRvAdapter by lazy {
       GasRvAdapter(this, gasList)
    }

    private val gasLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        activityMainBinding.GasHistoryRv.adapter = gasAdapter
        activityMainBinding.GasHistoryRv.layoutManager = gasLayoutManager

        gasActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val gas = resultado.data?.getParcelableExtra<Gas>(EXTRA_GAS)?.apply {
                    var flag = 0
                    for (i in gasList) {
                        if (i.data == this.data){
                            flag = 1;
                            val msg = "Não foi possível adicionar valor: Data já registrada";
                            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (flag != 1) {
                        gasController.criarGas(this)
                        gasList.add(this)
                        gasAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        activityMainBinding.adicionarGasFab.setOnClickListener {
            gasActivityResultLauncher.launch(Intent(this, GasActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.atualizarMi -> {
            gasAdapter.notifyDataSetChanged()
            true
        }
        else -> { false }
    }

}