package dev.mrbe.mztips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.mrbe.mztips.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //set nav for odds button
        binding.buttonDailyOdds.setOnClickListener {
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTipsFragment()
            )
        }
        return  binding.root
    }


}