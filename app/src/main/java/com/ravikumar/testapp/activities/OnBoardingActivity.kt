package com.ravikumar.testapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ravikumar.testapp.R
import com.ravikumar.testapp.adapters.OnBoardingPagerAdapter
import com.ravikumar.testapp.databinding.ActivityOnBoardingBinding
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.misc.*
import com.ravikumar.testapp.models.OnBoardingPageModel
import com.ravikumar.testapp.viewModels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: OnBoardingViewModel by viewModels()
    private lateinit var binding: ActivityOnBoardingBinding
    private var currentPosition = 0
    private var dataList: List<OnBoardingPageModel>? = null
    private val buttonAnimationForward: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.get_started_button_animation_forward
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWhiteStatusBarColor()
        if (checkSeenState()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        addOnServerResponseLiveDataObserver()
        viewModel.getOnBoardingData()
        binding.nextButton.setOnClickListener(this)
        binding.getStartedButton.setOnClickListener(this)
    }

    private fun addOnServerResponseLiveDataObserver() {
        viewModel.getOnServerResponseLiveData().observe(this, {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    dataList = it.data
                    setupViewPager()
                }
                is Resource.Loading -> {
                    // The loading will be shown for 3000 mills, to imitate the behaviour of loading
                    showLoading()
                }
                is Resource.Error -> {
                    hideLoading()
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.makeVisible()
        binding.onBoardingViewPager.makeInvisible()
        binding.tabIndicator.makeInvisible()
        binding.nextButton.setImageResource(0)
    }

    private fun hideLoading() {
        binding.progressBar.makeInvisible()
        binding.onBoardingViewPager.makeVisible()
        binding.tabIndicator.makeVisible()
        binding.nextButton.setImageResource(R.drawable.ic_arrow_right)
    }

    private fun setupViewPager() {
        val adapter = OnBoardingPagerAdapter(this, dataList ?: listOf())
        binding.onBoardingViewPager.adapter = adapter
        binding.tabIndicator.setupWithViewPager(binding.onBoardingViewPager)
        addTabIndicatorListener()
    }

    private fun addTabIndicatorListener() {
        binding.tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                dataList?.let {
                    if (tab?.position == it.size - 1) {
                        loadLastScreen()
                    } else {
                        unloadLastScreen()
                    }
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.getStartedButton -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                saveSeenState()
                finish()
            }
            R.id.nextButton -> {
                dataList?.let {
                    currentPosition = binding.onBoardingViewPager.currentItem
                    if (currentPosition < it.size) {
                        currentPosition += 1
                        binding.onBoardingViewPager.currentItem = currentPosition
                    }
                    if (currentPosition == it.size - 1) {
                        loadLastScreen()
                    }
                }
            }
        }
    }

    private fun loadLastScreen() {
        binding.nextButton.makeInvisible()
        binding.tabIndicator.makeInvisible()
        binding.getStartedButton.makeVisible()
        binding.getStartedButton.animation = buttonAnimationForward
    }

    fun unloadLastScreen() {
        binding.nextButton.makeVisible()
        binding.tabIndicator.makeVisible()
        binding.getStartedButton.makeInvisible()
    }
}