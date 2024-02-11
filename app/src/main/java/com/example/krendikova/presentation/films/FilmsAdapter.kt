package com.example.krendikova.presentation.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.krendikova.databinding.FilmsItemBinding
import com.example.krendikova.domain.model.Film
import com.example.krendikova.presentation.FilmUiModel

class FilmsAdapter(
    private val onLongClick: (filmId: String) -> Unit,
    private val onClick: (filmId: String) -> Unit,
) : ListAdapter<FilmUiModel, FilmsAdapter.FilmsViewHolder>(DiffCallback) {

    inner class FilmsViewHolder(
        private val binding: FilmsItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    onClick(getItem(adapterPosition).id)
                }
                root.setOnLongClickListener{
                    onLongClick(getItem(adapterPosition).id)
                    favoriteImg.isVisible = !favoriteImg.isVisible
                    true
                }
            }
        }

        fun bind(item: FilmUiModel) {
            with(binding) {
                filmName.text = item.name
                filmGenre.text = item.genres[0] + " (" + item.year.toString() + ")"
                bannerImg.load(item.posterUrlPreview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder =
        FilmsViewHolder(
            FilmsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FilmUiModel>() {
            override fun areItemsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel) = oldItem == newItem
        }
    }
}