package com.food_project.application

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.food_project.R
import com.food_project.databinding.ActivityMainBinding
import com.food_project.feature.favorites.presentation.ui.FavoritesFragment
import com.food_project.feature.home.presentation.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeFragmentTag = "HOME_FRAGMENT"
    private val favoritesFragmentTag = "FAVORITES_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyTheme(ThemeManager.getSavedTheme(this))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runThemeChangeAnimation()

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment, homeFragmentTag)
                .setPrimaryNavigationFragment(homeFragment)
                .commit()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
        setupBottomNavigation()
    }

    private fun runThemeChangeAnimation() {
        val bitmap = ThemeManager.transitionBitmap ?: return
        ThemeManager.transitionBitmap = null // Clear reference

        val decorView = window.decorView as ViewGroup
        val imageView = ImageView(this).apply {
            setImageBitmap(bitmap)
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        decorView.addView(imageView)

        val animator = ValueAnimator.ofInt(0, decorView.height).apply {
            duration = 500
            interpolator = AccelerateInterpolator()
            addUpdateListener { animation ->
                val top = animation.animatedValue as Int
                imageView.clipBounds = Rect(0, top, decorView.width, decorView.height)
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    decorView.removeView(imageView)
                }
            })
        }
        animator.start()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragmentManager = supportFragmentManager
            val currentFragment = fragmentManager.primaryNavigationFragment ?: return@setOnItemSelectedListener false

            val (targetFragment, targetTag) = when (item.itemId) {
                R.id.navigation_home -> {
                    (fragmentManager.findFragmentByTag(homeFragmentTag) ?: HomeFragment()) to homeFragmentTag
                }
                R.id.navigation_favorites -> {
                    (fragmentManager.findFragmentByTag(favoritesFragmentTag) ?: FavoritesFragment()) to favoritesFragmentTag
                }
                R.id.navigation_search -> {
                    (currentFragment as? SearchFocusable)?.focusSearchView()
                    return@setOnItemSelectedListener false
                }
                else -> return@setOnItemSelectedListener false
            }

            if (currentFragment.tag == targetTag) {
                return@setOnItemSelectedListener true
            }

            val transaction = fragmentManager.beginTransaction()
            transaction.hide(currentFragment)

            if (targetFragment.isAdded) {
                transaction.show(targetFragment)
            } else {
                transaction.add(R.id.fragment_container, targetFragment, targetTag)
            }

            transaction.setPrimaryNavigationFragment(targetFragment).commit()
            true
        }
    }
}