package com.sopt.famfam.fragment

import androidx.fragment.app.Fragment
import com.sopt.famfam.network.ApplicationController
import com.sopt.famfam.network.NetworkService

open class RootFragment : Fragment() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
}