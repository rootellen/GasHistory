package br.edu.ifsp.gashistory.model

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class GasFirebase: GasDao {

    companion object {
        private val BD_GAS = "gas"
    }
    private val gasRtDb = Firebase.database.getReference(BD_GAS)

    private val gasList = mutableListOf<Gas>()
    init {
        gasRtDb.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novoGas: Gas? = snapshot.value as? Gas
                novoGas?.apply {
                    if (gasList.find {
                        it.data == this.data
                        } == null) {
                        gasList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        gasRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                gasList.clear()
                snapshot.children.forEach {
                    val gas: Gas = it.getValue<Gas>()?: Gas()
                    gasList.add(gas)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        } )
    }

    override fun criarGas(gas: Gas): Long {
        gasRtDb.child(gas.data).setValue(gas)
        return 0L
    }

    override fun recuperarGas(): MutableList<Gas> = gasList
}