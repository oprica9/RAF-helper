package rs.raf.projekat2.ognjen_prica_10620.presentation.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.LoginFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.NoteFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.ScheduleFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.StatisticsFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                ScheduleFragment()
            }
            1 -> {
                NoteFragment()
            }
            2 -> {
                StatisticsFragment()
            }
            else -> {
                LoginFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Tab 1"
            }
            1 -> {
                return "Tab 2"
            }
            2 -> {
                return "Tab 3"
            }
        }
        return super.getPageTitle(position)
    }

}