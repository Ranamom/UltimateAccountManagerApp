package com.example.ultimateaccountmanager.ui.characterdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.util.Utils
import kotlinx.android.synthetic.main.character_details_fragment.*
import timber.log.Timber

class CharacterDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterDetailsFragment()
    }

    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logoutmenu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logoutButton) {
            val builder = context?.let { AlertDialog.Builder(it) }
            builder!!.setMessage("You realy want to logoff?")
            builder.setPositiveButton("yes") { dialog, which ->
                Utils.clearAllData(context!!, "Logoff success!!")
            }
            builder.setNegativeButton("no") { dialog, which ->
                //do Nothing
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)

        val observeCharId = Observer<String> {
            txt_character_detail_name.text = it.toString()
        }

        viewModel.appRepository.characterCurrentId.value = arguments?.getString("characterId")

        viewModel.appRepository.characterCurrentId.observe(viewLifecycleOwner, observeCharId)
    }

}
