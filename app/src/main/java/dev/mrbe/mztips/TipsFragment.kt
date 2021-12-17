package dev.mrbe.mztips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.mrbe.mztips.databinding.FragmentTipsBinding


class TipsFragment : Fragment() {
  private lateinit var binding: FragmentTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTipsBinding.inflate(inflater, container, false)

        return binding.root

    }


}