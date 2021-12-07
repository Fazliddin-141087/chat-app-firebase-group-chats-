package uz.mobiler.ppdgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.mobiler.ppdgram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("users")


    }
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this,R.id.my_nav_host_fragment).navigateUp()
    }


    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(true) }
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(false) }
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(false) }
    }
}


