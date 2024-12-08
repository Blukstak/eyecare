package com.example.eyecare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class StepsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_steps, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val images = listOf(R.drawable.signinstep1, R.drawable.signinstep2, R.drawable.signinstep3)
        viewPager.adapter = StepsPagerAdapter(images)

        val signInButton = view.findViewById<View>(R.id.signInButton)
        signInButton.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.dynamicContentContainer, LoginFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}