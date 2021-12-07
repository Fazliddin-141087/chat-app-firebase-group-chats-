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
import uz.mobiler.ppdgram.adapters.MessagesAdapters
import uz.mobiler.ppdgram.databinding.FragmentMessegesBinding
import uz.mobiler.ppdgram.models.Massages
import uz.mobiler.ppdgram.models.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MessegesFragment : Fragment() {


    lateinit var binding: FragmentMessegesBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var messagesAdapters: MessagesAdapters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessegesBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("messages")

        val user = arguments?.getSerializable("user") as User

        if (user != null) {

            binding.send.setOnClickListener {
                val massage = binding.massage.text.toString().trim()
                if (massage.isNotEmpty()) {
                    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                    val date = simpleDateFormat.format(Date())
                    var massages = Massages(massage, date, firebaseAuth.currentUser?.uid, user.uid)

                    val key = reference.push().key

                    reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.uid}/$key")
                        .setValue(massages)

                    reference.child("${user.uid}/messages/${firebaseAuth.currentUser?.uid}/$key")
                        .setValue(massages)
                    binding.massage.text?.clear()
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "Iltimos bo'sh xabar jo'natmang???",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            reference.child("${firebaseAuth.currentUser?.uid}/messages/${user.uid}")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var list = ArrayList<Massages>()
                        val children = snapshot.children
                        for (child in children) {
                            val value = child.getValue(Massages::class.java)
                            if (value != null) {
                                list.add(value)
                            }
                        }
                        messagesAdapters = MessagesAdapters(list, firebaseAuth.currentUser!!.uid)
                        binding.massageRv.adapter = messagesAdapters
                        binding.massageRv.scrollToPosition(list.size - 1)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}