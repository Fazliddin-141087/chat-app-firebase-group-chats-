package uz.mobiler.ppdgram.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.mobiler.ppdgram.databinding.ItemUsersBinding
import uz.mobiler.ppdgram.models.User

class UserAdapters(var list: ArrayList<User>, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapters.Vh>() {

    inner class Vh(var itemUsersBinding: ItemUsersBinding) :
        RecyclerView.ViewHolder(itemUsersBinding.root) {
        fun onBind(user: User) {

            Picasso.get().load(user.photoUrl).into(itemUsersBinding.avatar)
            itemUsersBinding.name.text = user.displayName
            itemUsersBinding.email.text = user.email

            if (user.online == true) {
                itemUsersBinding.avatar.showBadge = true
            } else if (user.online == false) {
                itemUsersBinding.avatar.showBadge = false
            }

            itemUsersBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(user)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

}