package uz.mobiler.ppdgram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.ppdgram.adapters.UserAdapters
import uz.mobiler.ppdgram.databinding.FragmentUsersBinding
import uz.mobiler.ppdgram.models.User

class UsersFragment : Fragment() {

    lateinit var binding: FragmentUsersBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var userAdapters: UserAdapters
    var list = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")

        val currentUser = firebaseAuth.currentUser
        val displayName = currentUser?.displayName
        val email = currentUser?.email
        val photoUrl = currentUser?.photoUrl
        val uid = currentUser?.uid

        val user = User(displayName, email, photoUrl.toString(), uid, true, false)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                var filterList = arrayListOf<User>()
                val children = snapshot.children

                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value != null && uid != value.uid) {
                        list.add(value)
                    }

                    if (value != null && value.uid == uid) {
                        filterList.add(value)
                    }
                }

                if (filterList.isEmpty()) {
                    if (uid != null) {
                        reference.child(uid).setValue(user)
                    }
                }

                userAdapters = UserAdapters(list, object : UserAdapters.OnItemClickListener {
                    override fun onItemClick(user: User) {
                        var bundle = Bundle()
                        bundle.putSerializable("user", user)
                        findNavController().navigate(R.id.messegesFragment, bundle)
                    }
                })
                binding.userRv.adapter = userAdapters
            }

            override fun onCancelled(error: DatabaseError) {}
        })



        return binding.root
    }


    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(true) }
    }

}