package com.example.ultimateaccountmanager.ui.listcharacters

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ultimateaccountmanager.R

class ListCharactersFragment : Fragment() {

    companion object {
        fun newInstance() = ListCharactersFragment()
    }

    private lateinit var viewModel: ListCharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_characters_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListCharactersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
