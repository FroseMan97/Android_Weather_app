package com.iazarevsergey.lessons.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.iazarevsergey.lessons.factory.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}
