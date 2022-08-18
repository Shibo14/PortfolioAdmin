package uz.khan.portfolioadmin

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.khan.portfolioadmin.data.models.ProjectModel
import uz.khan.portfolioadmin.databinding.AndroidItemBinding

/**
 * @Author: Temur
 * @Date: 01/08/2022
 */

class ProjectAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val currentList = ArrayList<ProjectModel>()

    fun submitData(list: List<ProjectModel>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()
    }

    var onClick: OnClick<ProjectModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VhProject(AndroidItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false),
            onClick)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VhProject).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}

private class VhProject(
    private val binding: AndroidItemBinding,
    private val onClick: OnClick<ProjectModel>?,
) :
    RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(project: ProjectModel) {
        binding.descTv.text = project.description
        binding.titleTv.text = project.title
        Picasso.get().load(project.images.toImageList()[0]).into(binding.img)
        binding.root.setOnClickListener {
            onClick?.onClick(project)
        }
    }
}
