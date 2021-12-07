package uz.mobiler.ppdgram


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tab.view.*
import uz.mobiler.ppdgram.adapters.ViewPagerAdapters
import uz.mobiler.ppdgram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var viewPagerAdapters: ViewPagerAdapters
    lateinit var list: ArrayList<String>
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")

        binding.toolTv.text=firebaseAuth.currentUser?.displayName

        Picasso.get().load(firebaseAuth.currentUser?.photoUrl).into(binding.avatarMain)


        list = ArrayList()
        list.add("Chats")
        list.add("Groups")

        viewPagerAdapters = ViewPagerAdapters(list, childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapters

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = list[position]
                }
                1 -> {
                    tab.text = list[position]
                }
            }
        }.attach()

        setTab()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.circle?.setBackgroundColor(Color.WHITE)
                view?.tv?.setTextColor(Color.BLACK)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.circle?.setBackgroundColor(Color.parseColor(getString(R.color.grey.toInt())))
                view?.tv?.setTextColor(Color.DKGRAY)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


        binding.toolBar.inflateMenu(R.menu.log_out)

        binding.toolBar.setOnMenuItemClickListener {
            if (it.itemId==R.id.log_out){
                firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(false) }
                firebaseAuth.signOut()
                var intent= Intent(requireContext(),RegistrActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            true
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(true) }
    }

    private fun setTab() {
        for (i in 0 until binding.tabLayout.tabCount) {
            val tabBind =
                LayoutInflater.from(binding.root.context).inflate(R.layout.item_tab, null, false)
            val tab = binding.tabLayout.getTabAt(i)
            tab?.customView = tabBind
            tabBind.tv.text = list[i]
            if (i == 0) {
                tabBind.circle.setBackgroundColor(Color.WHITE)
                tabBind.tv.setTextColor(Color.BLACK)
            } else {
                tabBind.circle.setBackgroundColor(Color.parseColor(getString(R.color.grey.toInt())))
                tabBind.tv.setTextColor(Color.LTGRAY)
            }
        }
    }

    override fun onDestroy() {
        firebaseAuth.currentUser?.uid?.let { reference.child(it).child("online").setValue(false) }
        super.onDestroy()
    }

}