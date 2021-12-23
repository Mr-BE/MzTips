package dev.mrbe.mztips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import dev.mrbe.mztips.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val topAdsView = binding.homeTopAds
        val bottomAdsView = binding.homeBottomAds

        val adRequest = AdRequest.Builder()
            .build()

        topAdsView.loadAd(adRequest)
        bottomAdsView.loadAd(adRequest)

        //set nav for Odds button
        binding.buttonDailyOdds.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTipsFragment()
            )
        }
        //set nav for previous odds button
        binding.buttonPreviousOdds.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPrviousOddsFragment()
            )
        }
        return binding.root
    }
}