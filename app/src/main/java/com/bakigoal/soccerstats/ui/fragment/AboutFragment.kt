package com.bakigoal.soccerstats.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bakigoal.soccerstats.R

class AboutFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? =
        inflater.inflate(R.layout.fragment_about, container, false)
}