package com.example.tea.presentation.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tea.databinding.FragmentSlideshowBinding

import com.example.tea.presentation.home.MapViewModel

class MapFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private var token:String=""
    private val adminViewModel: MapViewModel by activityViewModels() //// Для обмена даннами между активити и фрагментами
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        val root: View = binding.root
        adminViewModel.messageFromactivityToken.observe(viewLifecycleOwner) {
            token = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


