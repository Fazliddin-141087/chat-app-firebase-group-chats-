package uz.mobiler.ppdgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.ppdgram.adapters.GroupAdapters
import uz.mobiler.ppdgram.databinding.DialogItemBinding
import uz.mobiler.ppdgram.databinding.FragmentGroupsBinding
import uz.mobiler.ppdgram.models.Group

class GroupsFragment : Fragment() {


    lateinit var binding: FragmentGroupsBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var groupAdapters: GroupAdapters
    lateinit var groupList: ArrayList<Group>
    lateinit var list:ArrayList<Group>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        reference = firebaseDatabase.getReference("groups")

        groupList = ArrayList()
        list= ArrayList()

        groupAdapters = GroupAdapters(groupList, object : GroupAdapters.MyItemOnClickListener {
            override fun onItemClick(group: Group) {
                var bundle = Bundle()
                bundle.putSerializable("group", group)
                findNavController().navigate(R.id.groupMessegesFragment, bundle)
            }
        })
        binding.groupRv.adapter = groupAdapters

        var isHas = false

        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                groupList.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Group::class.java)
                    if (value != null) {
                        list.add(value)
                    }
                }

                for (i in 0 until list.size) {
                    for (k in 0 until list[i].userList!!.size) {
                        if (list[i].userList!![k].uid == firebaseAuth.currentUser?.uid) {
                            groupList.add(list[i])
                        }
                    }
                }
                groupAdapters.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        binding.addGroup.setOnClickListener {

            var alertDialog =
                MaterialAlertDialogBuilder(binding.root.context, R.style.AppBottomSheetDialogTheme)
            val dialog = alertDialog.create()
            var dialogView =
                DialogItemBinding.inflate(LayoutInflater.from(binding.root.context), null, false)

            dialogView.addNameGroup.setOnClickListener {
                val groupName = dialogView.groupNameEt.text.toString()
                if (groupName.isNotEmpty()) {
                    for (group in list) {
                        if (group.groupName == groupName) {
                            isHas = true
                        }
                    }

                    if (!isHas) {
                        val bundle = Bundle()
                        bundle.putString("groupName", groupName)
                        findNavController().navigate(R.id.registrFragment, bundle)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(binding.root.context, "Bunday guruh mavjud boshqa nom kiriting!!!", Toast.LENGTH_SHORT).show()
                        isHas = false
                    }
                } else {
                    Toast.makeText(binding.root.context, "Iltimos guruh nomini kiriting???", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setView(dialogView.root)
            dialog.show()
        }


        return binding.root
    }


}