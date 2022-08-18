package uz.khan.portfolioadmin

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import uz.khan.portfolioadmin.data.DataModifier
import uz.khan.portfolioadmin.data.models.ProjectModel
import uz.khan.portfolioadmin.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailImagesAdapter: DetailImagesAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val project = intent?.getSerializableExtra("project") as ProjectModel
        binding.publicControlBtn.isChecked = project.available
        binding.descTv.text = project.description
        binding.textView.text = project.title

        detailImagesAdapter = DetailImagesAdapter()
        binding.rv.adapter = detailImagesAdapter

        detailImagesAdapter.submitData(project.images.toImageList())
        val modifier = DataModifier()

        binding.publicControlBtn.setOnCheckedChangeListener { compoundButton, b ->
            modifier.control(b,project.id)
        }
    }
}