package br.edu.ifsp.gashistory.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.gashistory.OnGasClickListener
import br.edu.ifsp.gashistory.databinding.LayoutGasBinding
import br.edu.ifsp.gashistory.model.Gas

class GasRvAdapter(
    private val onGasClickListener: OnGasClickListener,
    private val gasList: MutableList<Gas>
): RecyclerView.Adapter<GasRvAdapter.GasLayoutHolder>() {

    //poscao que sera recuperada pelo menu de contexto
    var posicao: Int = -1

    //ViewHolder
    inner class GasLayoutHolder(layoutGasBinding: LayoutGasBinding): RecyclerView.ViewHolder(
        layoutGasBinding.root) {
        val dataTv: TextView = layoutGasBinding.dataTv
        val valorTv: TextView = layoutGasBinding.valorTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GasLayoutHolder {
        val layoutGasBinding = LayoutGasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //criar um viewholder associado a nova celula
        val viewHolder: GasLayoutHolder = GasLayoutHolder(layoutGasBinding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: GasLayoutHolder, position: Int) {
        //busca gas
        val gas = gasList[position]

        with(holder){
            valorTv.text = gas.valor.toString()
            dataTv.text = gas.data
            itemView.setOnClickListener {
                onGasClickListener.onGasClick(position)
            }
            itemView.setOnLongClickListener {
                posicao = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return gasList.size
    }
}