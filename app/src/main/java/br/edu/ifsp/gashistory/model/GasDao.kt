package br.edu.ifsp.gashistory.model

interface GasDao {

    fun criarGas(gas: Gas): Long
    fun recuperarGas(): MutableList<Gas>

}