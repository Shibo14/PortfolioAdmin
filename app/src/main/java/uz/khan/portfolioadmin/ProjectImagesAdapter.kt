package uz.khan.portfolioadmin

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import uz.khan.portfolioadmin.databinding.ImageItemBinding

/**
 * @Author: Temur
 * @Date: 01/08/2022
 */

class ProjectImagesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val currentList = ArrayList<ImageModel>()

    init {
        currentList.add(ImageModel(null, isImage = false))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun submitData(image: ImageModel) {
        currentList.add(image)
        currentList.removeIf { !it.isImage }
        if (currentList.size < 10) {
            currentList.add(ImageModel(null, isImage = false))
        }
        notifyDataSetChanged()
    }

    var onClick: OnClick<Unit>? = null
    var onRemove: OnClick<ImageModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VhImages(ImageItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false),
            onClick, onRemove)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VhImages).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}

private class VhImages(
    private val binding: ImageItemBinding,
    private val onClick: OnClick<Unit>?,
    private val onRemove: OnClick<ImageModel>?,
) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(image: ImageModel) {
        if (image.isImage) {
            binding.icon.setImageURI(image.uri)
        } else {
            binding.icon.setImageResource(image.addImage)
        }

        binding.imgCard.isGone = !image.isImage

        binding.removeBtn.setOnClickListener {
            onRemove?.onClick(image)
        }

        binding.icon.setOnClickListener {
            if (!image.isImage) {
                onClick?.onClick(Unit)
            }
        }
    }
}
