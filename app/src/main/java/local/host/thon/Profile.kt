package local.host.thon

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import local.host.thonon.Fragment1
import local.host.thonon.Fragment2
import local.host.thonon.Fragment3

class Profile  : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        val view =  inflater.inflate(R.layout.profile, container, false)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        val adapter =MyViewPagerAdapter(childFragmentManager,lifecycle)
        adapter.addFragment(Fragment1(), "Home")
        adapter.addFragment(Fragment2(), "Device")
        adapter.addFragment(Fragment3(), "My Account")
        viewPager.adapter =adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
        return view;
    }

}
class MyViewPagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) :  FragmentStateAdapter(manager,lifecycle ){
    private val fragmentList : MutableList<Fragment> =ArrayList()
    private val titleList : MutableList<String> =ArrayList()
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return  fragmentList[position]
    }


    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

    fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}