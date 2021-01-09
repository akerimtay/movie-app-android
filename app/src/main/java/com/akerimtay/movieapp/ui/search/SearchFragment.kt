package com.akerimtay.movieapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.databinding.FragmentSearchBinding
import com.akerimtay.movieapp.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        showToast(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        Timber.d(query)
        return true
    }

    private fun setupUI() {
        binding.arrowBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.searchView.onActionViewExpanded()
        binding.searchView.setOnQueryTextListener(this)
    }

//    private fun openVoice() {
//        val voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        voiceIntent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//
//        val manager = requireActivity().packageManager?.let { manager ->
//            voiceIntent.resolveActivity(manager)
//        }
//        if (manager != null) {
//            startActivityForResult(voiceIntent, 10)
//        } else {
//            showToast("your device does not support Speech Input")
//        }
//    }
}