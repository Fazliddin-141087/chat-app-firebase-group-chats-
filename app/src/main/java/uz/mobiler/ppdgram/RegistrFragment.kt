package uz.mobiler.ppdgram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.ppdgram.adapters.ChekAdapters
import uz.mobiler.ppdgram.databinding.FragmentRegistrBinding
import uz.mobiler.ppdgram.models.ChekUser
import uz.mobiler.ppdgram.models.Group
import uz.mobiler.ppdgram.models.GroupMassage
import uz.mobiler.ppdgram.models.User


class RegistrFragment : Fragment() {

    lateinit var binding: FragmentRegistrBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var reference2: DatabaseReference
    lateinit var mList:ArrayList<GroupMassage>
    lateinit var list:ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegistrBinding.inflate(layoutInflater)

        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseAuth= FirebaseAuth.getInstance()

        reference=firebaseDatabase.getReference("users")
        reference2=firebaseDatabase.getReference("groups")

        val photoUrl = firebaseAuth.currentUser?.photoUrl
        val displayName = firebaseAuth.currentUser?.displayName
        val email = firebaseAuth.currentUser?.email
        val uid = firebaseAuth.currentUser?.uid
        val online=true
        val user=User(displayName,email, photoUrl.toString(),uid,online,false)

        mList= ArrayList()

        val reference1=firebaseDatabase.getReference("messages")
        reference1.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               mList.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(GroupMassage::class.java)
                    if (value != null) {
                        mList.add(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        if (uid != null) {
            reference.child(uid).setValue(user)
        }

        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                list= ArrayList()
                list.clear()
                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value!=null && value.uid!=uid){
                        list.add(value)
                    }
                }

                var isHas=false

                val chekAdapters=ChekAdapters(list,object : ChekAdapters.MyOnItemClickListener{
                    override fun onItemCheck(userList: ArrayList<ChekUser>) {
                        var chekUsers=ChekUser(firebaseAuth.currentUser?.uid,firebaseAuth.currentUser?.photoUrl.toString(),null)
                        for (chek in userList) {
                            if (chek.uid==chekUsers.uid){
                                isHas=true
                            }
                        }
                        if (!isHas){
                            userList.add(chekUsers)
                        }else{
                            isHas=false
                        }
                        val name = arguments?.getString("groupName")
                        binding.next.setOnClickListener {
                            if (userList.isNotEmpty()){
                                val key = reference2.push().key
                                val group=Group(key,userList,name)
                                if (key != null) {
                                    reference2.child(key).setValue(group)
                                    findNavController().popBackStack()
                                }
                            }else{
                                Toast.makeText(binding.root.context, "Foydalnuvchi qo'shilmagan!!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },mList)

                binding.addGroupUser.adapter=chekAdapters
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        return binding.root
    }

}