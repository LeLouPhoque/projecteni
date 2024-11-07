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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.enishop.bo.Article
import com.example.enishop.form.FormScreen
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.theme.EniShopTheme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "articleList") {
                    composable("articleList") {
                        ArticleListScreen(navController)
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
                        FormScreen(
                            name = remember { mutableStateOf("") },
                            email = remember { mutableStateOf("") },
                            isFormSubmitted = remember { mutableStateOf(false) },
                            onSubmit = { /* Logique de soumission du formulaire */ }
                        )
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
    var isFavorite by remember { mutableStateOf(false) }

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
                    modifier = Modifier.padding(top = 8.dp)
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(navController: NavController) {
    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }

    LaunchedEffect(Unit) {
        articles = ArticleRepository.getArticles()
    }

    val onButtonClick = {
        navController.navigate("formScreen")
    }

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
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(articles) { article ->
                    ArticleCard(article) { selectedArticle ->
                        navController.navigate("articleDetail/${selectedArticle.id}")
                    }
                }
            }
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Ajouter un article")
            }
        }
    }
}
