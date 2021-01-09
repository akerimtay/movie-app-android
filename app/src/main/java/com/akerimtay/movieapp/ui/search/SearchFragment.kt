package com.akerimtay.movieapp.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.transition.TransitionInflater
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.databinding.FragmentSearchBinding
import com.akerimtay.movieapp.extensions.showToast
import com.akerimtay.movieapp.utils.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SearchFragment : Fragment() {

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

    private fun setupUI() {
        binding.arrowBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.imgVoiceSearch.setOnClickListener { openVoice() }
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.onActionViewExpanded()

        val debounceTextChange =
            debounce(DELAY_TIME_MILLIS, viewModel.viewModelScope, viewModel::search)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                query.let(debounceTextChange)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openVoice() {
        val voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        voiceIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.say_movie_name))

        try {
            requireActivity().packageManager?.let { voiceIntent.resolveActivity(it) }
            startActivityForResult(voiceIntent, CODE_VOICE_RECOGNIZER)
        } catch (e: Exception) {
            showToast(e.localizedMessage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODE_VOICE_RECOGNIZER -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val voiceQuery = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    binding.searchView.setQuery(voiceQuery?.firstOrNull(), true)
                }
            }
        }
    }

    companion object {
        private const val CODE_VOICE_RECOGNIZER = 10
        private const val DELAY_TIME_MILLIS = 300L
    }
}