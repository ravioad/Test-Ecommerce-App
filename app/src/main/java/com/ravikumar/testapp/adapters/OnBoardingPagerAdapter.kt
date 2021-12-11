package com.ravikumar.testapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.ravikumar.testapp.databinding.OnBoardingPageLayoutBinding
import com.ravikumar.testapp.models.OnBoardingPageModel

class OnBoardingPagerAdapter(
    private val context: Context,
    private val mList: List<OnBoardingPageModel>
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = OnBoardingPageLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        Glide.with(context).load(mList[position].image).into(binding.onBoardingPageImage)
        binding.onBoardingPageTitle.text = mList[position].title
        binding.onBoardingPageDescription.text = mList[position].description
        container.addView(binding.root)
        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}