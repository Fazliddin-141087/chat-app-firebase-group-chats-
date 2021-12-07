package uz.mobiler.ppdgram.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.ppdgram.databinding.ItemCheckBinding
import uz.mobiler.ppdgram.models.ChekUser
import uz.mobiler.ppdgram.models.GroupMassage
import uz.mobiler.ppdgram.models.User

class ChekAdapters(val list: ArrayList<User>,val myOnItemClickListener: MyOnItemClickListener,val mList:ArrayList<GroupMassage>) : RecyclerView.Adapter<ChekAdapters.Vh>() {

    private lateinit var userList: ArrayList<ChekUser>
    fun setList(userList: ArrayList<ChekUser>) {
        this.userList = userList
    }

    inner class Vh(val itemCheckBinding: ItemCheckBinding) :
        RecyclerView.ViewHolder(itemCheckBinding.root) {
        fun onBind(user: User, position: Int) {
            Picasso.get().load(user.photoUrl).into(itemCheckBinding.photo)
            itemCheckBinding.tv.text = user.displayName

            var chek=ChekUser(user.uid,user.photoUrl,position)
            userList= ArrayList()
            userList.clear()

            itemCheckBinding.root.setOnClickListener {
                if (user.onCheck == false){
                    itemCheckBinding.chek.visibility = View.VISIBLE
                    userList.add(chek)
                    user.onCheck=true
                }else {
                    itemCheckBinding.chek.visibility = View.GONE
                    userList.remove(chek)
                    user.onCheck=false
                }
                myOnItemClickListener.onItemCheck(userList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MyOnItemClickListener {
        fun onItemCheck(userList: ArrayList<ChekUser>)
    }

}