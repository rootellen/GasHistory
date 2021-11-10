package br.edu.ifsp.gashistory.controller

import br.edu.ifsp.gashistory.MainActivity
import br.edu.ifsp.gashistory.model.Gas
import br.edu.ifsp.gashistory.model.GasDao
import br.edu.ifsp.gashistory.model.GasFirebase

class GasController(mainActivity: MainActivity) {
    private val gasDao: GasDao = GasFirebase()

    fun criarGas(gas: Gas) = gasDao.criarGas(gas)
    fun buscarGas() = gasDao.recuperarGas()
}