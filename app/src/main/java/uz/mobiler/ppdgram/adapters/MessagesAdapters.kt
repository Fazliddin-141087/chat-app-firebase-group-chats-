package uz.mobiler.ppdgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.ppdgram.databinding.ItemFromBinding
import uz.mobiler.ppdgram.databinding.ItemToBinding
import uz.mobiler.ppdgram.models.Massages

class MessagesAdapters(var list: List<Massages>, var uid: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FromVh(var itemFromBinding: ItemFromBinding) : RecyclerView.ViewHolder(itemFromBinding.root) {

        fun onBind(massages: Massages) {
            itemFromBinding.massageTv.text = massages.massages
            itemFromBinding.dateTv.text = massages.date
            massages.massages
        }
    }

    inner class ToVh(var itemToBinding: ItemToBinding) : RecyclerView.ViewHolder(itemToBinding.root) {

        fun onBind(massages: Massages) {
            itemToBinding.massageTv.text = massages.massages
            itemToBinding.dateTv.text = massages.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVh(ItemFromBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            return ToVh(ItemToBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==1){
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        }else{
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid == uid) {
            return 1
        } else {
            return 2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}