package com.robosoft.virtuallearnproject.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.ActivityHomeScreenContainerBinding
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment

//import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment

class HomeScreenContainerActivity : AppCompatActivity() {
    lateinit var HomeScreenContainerbinding: ActivityHomeScreenContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeScreenContainerbinding = ActivityHomeScreenContainerBinding.inflate(layoutInflater)
        setContentView(HomeScreenContainerbinding.root)

        window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        if (savedInstanceState == null) {
            //startActivity(Intent(this, MainActivity::class.java))
            supportFragmentManager.beginTransaction().replace(
                HomeScreenContainerbinding.homescreenfragmentContainer.id,
                HomeScreenMainFragment()
            ).commit()
        }

//        Log.d("current fragment", getCurrentFragment().toString())
//        if (getCurrentFragment() != null) {
//            //set the current fragment when rotated
//            changeFragment(getCurrentFragment()!!)
//        }
//

//        val fragmentManager = supportFragmentManager
//        val count = fragmentManager.backStackEntryCount
//        for (i in 0..count) {
//            fragmentManager.popBackStackImmediate()
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("current fragment", getCurrentFragment().toString())
//        if (getCurrentFragment() != null) {
//            //set the current fragment when rotated
//            changeFragment(getCurrentFragment()!!)
//        }
//    }

    private fun changeFragment(currentFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(HomeScreenContainerbinding.homescreenfragmentContainer.id,currentFragment).commit()
    }

    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.homescreenfragment_container)
    }
}