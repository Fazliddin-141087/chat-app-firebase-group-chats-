package uz.mobiler.ppdgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.ppdgram.databinding.ItemGroupBinding
import uz.mobiler.ppdgram.models.Group

class GroupAdapters(var list: ArrayList<Group>,var myItemOnClickListener: MyItemOnClickListener) :RecyclerView.Adapter<GroupAdapters.Vh>() {

    inner class Vh(var itemGroupBinding: ItemGroupBinding) :RecyclerView.ViewHolder(itemGroupBinding.root){
            fun onBind(group: Group){
                itemGroupBinding.name.text=group.groupName

                itemGroupBinding.root.setOnClickListener {
                    myItemOnClickListener.onItemClick(group)
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MyItemOnClickListener{
        fun onItemClick(group: Group)
    }
}