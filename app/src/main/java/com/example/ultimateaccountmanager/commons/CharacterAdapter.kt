package com.example.ultimateaccountmanager.commons

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.models.Character
import kotlinx.android.synthetic.main.lista_characters_details.view.*

class CharacterAdapter(val dataSet: List<Character>, val context: Context) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(character: Character) {
            with(itemView) {
                txt_character_list_lvl.text = character.level.toString()
                txt_character_list_name.text = character.name
                txt_character_list_vocation.text = character.vocation.toString()
                Glide.with(this).load(character.imageurl).into(img_character_list_profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutCharacters = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_characters_details, parent, false)

        return ViewHolder(layoutCharacters)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataSet[position])
    }
}