package com.example.enishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.enishop.bo.Article
import com.example.enishop.form.FormScreen
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.theme.EniShopTheme
import com.example.enishop.vm.ArticleListViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import android.content.Intent
import android.net.Uri
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "articleList") {
                    composable("articleList") {
                        ArticleListScreen(navController, ArticleRepository)
                    }
                    composable("articleDetail/{articleId}") { backStackEntry ->
                        val articleId = backStackEntry.arguments?.getString("articleId")?.toLongOrNull()
                        val article = articleId?.let { ArticleRepository.getArticle(it) }
                        if (article != null) {
                            ArticleDetailScreen(navController, article)
                        } else {
                            Text("Article not found")
                        }
                    }
                    composable("formScreen") {
                        FormScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleCard(article: Article, onDetailClick: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
            Text(text = article.name)
            Text(text = "Price: ${article.price} €")
            Button(
                onClick = { onDetailClick(article) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("View Details")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    article: Article
) {

    val context = LocalContext.current as Activity
    var isFavorite by remember { mutableStateOf(false) }
    val query = article.name
    val searchUrl = "https://www.bing.com/search?q=$query"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart, contentDescription = "Cart",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "ENI Shop",
                            color = Color.Magenta,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text(
                text = article.name,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                style = TextStyle(lineHeight = 28.sp)
            )
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
            Text(text = article.description, modifier = Modifier.padding(top = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Prix : ${article.price} €",
                    modifier = Modifier.padding(top = 8.dp).testTag("prixText")
                )
                Text(
                    text = "Date : ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(article.date)}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it }
                )
                Text(text = "Favori", modifier = Modifier.padding(start = 8.dp))
            }
            Button(
                onClick = {
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Comparer les pris sur internet de : ${article.name}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(navController: NavController, articleRepository: ArticleRepository) {
    val viewModel: ArticleListViewModel = viewModel(
        factory = ArticleListViewModel.provideFactory(articleRepository)
    )

    val articles by viewModel.articles.observeAsState(emptyList())
    val categories by viewModel.categories.observeAsState(emptyList())

    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart", modifier = Modifier.size(40.dp))
                        Text(
                            text = "ENI Shop",
                            color = Color.Magenta,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                        )
                        IconButton(onClick = { navController.navigate("formScreen")}) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "send")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    Button(
                        onClick = { selectedCategory = category },
                        colors = if (selectedCategory == category) ButtonDefaults.buttonColors(
                            containerColor = Color.Magenta,
                            contentColor = Color.White
                        ) else ButtonDefaults.buttonColors()
                    ) {
                        Text(category)
                    }
                }
            }

            val filteredArticles = if (selectedCategory == "All") articles else articles.filter { it.category == selectedCategory }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredArticles) { article ->
                    ArticleCard(article) { selectedArticle ->
                        navController.navigate("articleDetail/${selectedArticle.id}")
                    }
                }
            }
        }
    }
}
