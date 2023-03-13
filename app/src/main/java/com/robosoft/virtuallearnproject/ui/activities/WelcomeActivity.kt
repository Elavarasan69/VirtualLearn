package com.robosoft.virtuallearnproject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.adapter.WelcomeViewPagerAdapter
import com.robosoft.virtuallearnproject.databinding.ActivityWelcomeBinding
import com.robosoft.virtuallearnproject.dataclass.WelcomeDataClass

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding
    var pageCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: List<WelcomeDataClass> = listOf(
            WelcomeDataClass(
                R.drawable.img_onboarding_illustration1,
                getString(R.string.learner_engagement_title),
                getString(R.string.learner_engagement)
            ),
            WelcomeDataClass(
                R.drawable.img_onboarding_illustration2,
                getString(R.string.seamless_workflow_titel),
                getString(R.string.seamless_workflow)
            ),
            WelcomeDataClass(
                R.drawable.img_onboarding_illustration3,
                getString(R.string.account_tracking_title),
                getString(R.string.account_tracking)
            ),
        )

       val viewpagerAdapter = WelcomeViewPagerAdapter(data, this)
        binding.viewPager.adapter = viewpagerAdapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 2){
                    binding.nextViewBtn.visibility = View.GONE
                    binding.nextScreemBtn.visibility = View.VISIBLE
                    binding.skipBtn.visibility = View.GONE
                    pageCount = 0

                }

                else{
                    binding.nextViewBtn.visibility = View.VISIBLE
                    binding.nextScreemBtn.visibility = View.GONE
                    binding.skipBtn.visibility = View.VISIBLE
                }
            }
        })

        binding.nextViewBtn.setOnClickListener{
            binding.viewPager.setCurrentItem(++pageCount,true)
        }

        binding.nextScreemBtn.setOnClickListener{
//            startActivity(Intent(this, LoginRegisterContainerActivity::class.java))
            startActivity(Intent(this, LoginRegisterContainerActivity::class.java))
            finish()
        }

        binding.skipBtn.setOnClickListener{
            startActivity(Intent(this, LoginRegisterContainerActivity::class.java))
            finish()
        }

    }
}