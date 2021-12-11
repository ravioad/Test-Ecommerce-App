package com.ravikumar.testapp.repositories

import com.ravikumar.testapp.R
import com.ravikumar.testapp.helperClasses.Resource
import com.ravikumar.testapp.models.OnBoardingPageModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class OnBoardingRepository @Inject constructor() {
    suspend fun getOnBoardingData(): Resource<List<OnBoardingPageModel>> {
        val sampleDescription =
            "Lorem ipsum dolor sit amet, eu affert ornatus eam, no sint augue necessitatibus quo. No sea vero malis disputando, meis offendit eos ea."
        val onBoardingList = listOf(
            OnBoardingPageModel(
                "Fresh Food",
                sampleDescription,
                R.drawable.img1
            ),
            OnBoardingPageModel(
                "Fast Delivery",
                sampleDescription,
                R.drawable.img2
            ),
            OnBoardingPageModel(
                "Easy Payment",
                sampleDescription,
                R.drawable.img3
            )
        )
        delay(3000) // To imitate the process of loading
        return Resource.Success(onBoardingList)
    }
}