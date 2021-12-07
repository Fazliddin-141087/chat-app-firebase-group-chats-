package uz.mobiler.ppdgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.ppdgram.adapters.GroupMessagesAdapter
import uz.mobiler.ppdgram.databinding.FragmentGroupMessegesBinding
import uz.mobiler.ppdgram.models.Group
import uz.mobiler.ppdgram.models.GroupMassage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class GroupMessegesFragment : Fragment() {

    lateinit var binding: FragmentGroupMessegesBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var groupMessagesAdapter: GroupMessagesAdapter
    lateinit var mList: ArrayList<GroupMassage>
    lateinit var list:ArrayList<String>
     var userImg=""
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupMessegesBinding.inflate(layoutInflater)

        val group = arguments?.getSerializable("group") as Group
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()


        if (group != null) {

            mList= ArrayList()

            reference = firebaseDatabase.getReference("groups")

            binding.toolBar.title=group.groupName

            groupMessagesAdapter=GroupMessagesAdapter(mList,firebaseAuth.currentUser!!.uid)
            binding.massageRv.adapter=groupMessagesAdapter


            group.userList?.forEach { it->
               if (it.uid==firebaseAuth.currentUser!!.uid){
                  userImg=it.photoUrl.toString()
               }
            }

            binding.send.setOnClickListener {
                val massage = binding.massage.text.toString().trim()
                if (massage.isNotEmpty()){
                    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                    val date = simpleDateFormat.format(Date())
                    val groupMassage=GroupMassage(massage,date,firebaseAuth.currentUser?.uid, userImg)
                    val key = reference.push().key
                    group.keys?.let { it1 ->
                        if (key != null) {
                            reference.child(it1).child("massages").child(key).setValue(groupMassage)
                        }
                    }
                    binding.massage.text?.clear()
                }else{
                    Toast.makeText(binding.root.context, "Iltimos bo'sh xabar jo'natmang!!!", Toast.LENGTH_SHORT).show()
                }
            }


            group.keys?.let {
                reference.child(it).child("massages")
                    .addValueEventListener(object :ValueEventListener{
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onDataChange(snapshot: DataSnapshot) {
                            mList.clear()
                            val children = snapshot.children
                            for (child in children) {
                                val value = child.getValue(GroupMassage::class.java)
                                if (value != null) {
                                    mList.add(value)
                                }
                            }
                            binding.massageRv.scrollToPosition(mList.size-1)
                            groupMessagesAdapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
            }

        }


        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}