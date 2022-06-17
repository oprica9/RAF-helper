package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.viewpager.PagerAdapter
import timber.log.Timber


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewPager: ViewPager;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //// Timber.e("USLO")
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        initView(view)
        initViewPager(view)
        initNavigation(view)
        initListeners()
    }

    private fun initListeners() {

    }

    private fun initNavigation(view: View) {

        view.findViewById<BottomNavigationView>(R.id.bottomNavBar).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_1 -> {
                    viewPager.setCurrentItem(0, false)
                    true
                }
                R.id.navigation_2 -> {
                    viewPager.setCurrentItem(1, false)
                    true
                }
                else -> {
                    viewPager.setCurrentItem(2, false)
                    true
                }
            }
        }
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPagerMain)
        viewPager.adapter = PagerAdapter(childFragmentManager)


    }

    private fun initView(view: View) {

    }

}