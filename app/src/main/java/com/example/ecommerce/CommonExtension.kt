package com.example.ecommerce

import androidx.navigation.NavController

fun NavController.navigateTo(destinationResId: Int) {
    if (currentDestination == null) {
        navigate(destinationResId)
    } else {
        currentDestination?.let {
            if (it.id != destinationResId) {
                navigate(destinationResId)
            }
        }
    }
}