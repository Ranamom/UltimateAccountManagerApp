package com.example.ultimateaccountmanager.ui.characterdetails

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.models.Character
import com.example.ultimateaccountmanager.util.Utils
import kotlinx.android.synthetic.main.character_details_fragment.*

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
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logoutmenu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutButton -> {
                val builder = context?.let { AlertDialog.Builder(it) }
                builder!!.setMessage("Deseja sair do sistema?")
                builder.setPositiveButton("SAIR") { _, _ ->
                    Utils.clearAllData(context!!)
                }
                builder.setNegativeButton("NÃO") { _, _ ->
                    //do Nothing
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)

//        val observeCharId = Observer<Int> {txt_character_detail_name.text = it.toString()}
        arguments?.getInt("characterId")?.let {
            viewModel.setCharacterCurrentId(it)
        }
        val observeLiveCharacterData = Observer<Character> { character ->

            if (character != null) {

                characterDetailsProgressBar.visibility = View.GONE

                txt_character_details_name.text = character.name
                txt_character_details_vocation.text = character.vocation
                txt_character_details_level.text = "Level: ${character.level}"

                //Title set
                txt_character_details_experience_title.text = "Experience"
                txt_character_details_life_title.text = "Life"
                txt_character_details_mana_title.text = "Mana"

                //Character values Set
                txt_character_details_experience.text = character.experience.toString()
                txt_character_details_life.text = "${character.health}/${character.healthmax}"
                txt_character_details_mana.text = "${character.mana}/${character.manamax}"

                //Character Image Catch
                Glide.with(view!!.context).load(character.imageurlanimated)
                    .into(img_character_details_profile)
                //Title icon generate
                Glide.with(view!!.context).load(R.drawable.ic_character_experience)
                    .into(img_character_details_experience)
                Glide.with(view!!.context).load(R.drawable.ic_character_life_potion)
                    .into(img_character_details_life)
                Glide.with(view!!.context).load(R.drawable.ic_character_mana_potion)
                    .into(img_character_details_mana)
            }
        }

        viewModel.getLiveCharacterData().observe(viewLifecycleOwner, observeLiveCharacterData)

//        viewModel.getCharacterCurrentId().observe(viewLifecycleOwner, observeCharId)
    }

}
