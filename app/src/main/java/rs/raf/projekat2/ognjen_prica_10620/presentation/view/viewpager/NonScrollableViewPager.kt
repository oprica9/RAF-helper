package rs.raf.projekat2.ognjen_prica_10620.presentation.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonScrollableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false;
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false;
    }
}