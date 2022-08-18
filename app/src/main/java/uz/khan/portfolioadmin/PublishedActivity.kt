package uz.khan.portfolioadmin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.portfolio.data.FirebaseDataReceiver
import uz.khan.portfolioadmin.data.models.ProjectModel
import uz.khan.portfolioadmin.databinding.ActivityDetailBinding
import uz.khan.portfolioadmin.databinding.ActivityPublishedBinding

class PublishedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishedBinding
    private lateinit var adapter: ProjectAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublishedBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        adapter = ProjectAdapter()
        binding.rv.adapter = adapter

        adapter.onClick = OnClick {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("project", it)
            startActivity(intent)
        }

        FirebaseDataReceiver().getPublishedProjects(onSuccess = {
            adapter.submitData(it)
        }, )
    }
}