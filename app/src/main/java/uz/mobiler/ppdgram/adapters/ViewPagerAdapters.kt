package uz.mobiler.ppdgram.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.mobiler.ppdgram.GroupsFragment
import uz.mobiler.ppdgram.UsersFragment

class ViewPagerAdapters(var list:ArrayList<String>,fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
          when(position){
              0->{
                  return UsersFragment()
              }
              1->{
                  return GroupsFragment()
              }
          }
        return UsersFragment()
    }

}