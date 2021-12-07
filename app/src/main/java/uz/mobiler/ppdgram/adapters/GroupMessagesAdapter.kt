package uz.mobiler.ppdgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.ppdgram.databinding.ItemFromGroupBinding
import uz.mobiler.ppdgram.databinding.ItemToGroupBinding
import uz.mobiler.ppdgram.models.GroupMassage

class GroupMessagesAdapter(var list: ArrayList<GroupMassage>,var uid:String) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FromVh(var itemFromGroupBinding: ItemFromGroupBinding):RecyclerView.ViewHolder(itemFromGroupBinding.root){
        fun onBind(groupMassage: GroupMassage){
            itemFromGroupBinding.massageTv.text=groupMassage.massages
            itemFromGroupBinding.dateTv.text=groupMassage.date
        }
    }

    inner class Vh(var itemToGroupBinding: ItemToGroupBinding):RecyclerView.ViewHolder(itemToGroupBinding.root){
        fun onBind(groupMassage: GroupMassage){
            Picasso.get().load(groupMassage.photoUrl).into(itemToGroupBinding.img)
            itemToGroupBinding.massageTv.text=groupMassage.massages
            itemToGroupBinding.dateTv.text=groupMassage.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            return FromVh(ItemFromGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            return Vh(ItemToGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==1){
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        }else{
            val vh = holder as Vh
            vh.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid==uid){
            return 1
        }else{
            return 2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}