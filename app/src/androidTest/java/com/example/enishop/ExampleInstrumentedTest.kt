package com.example.enishop

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.enishop.repository.ArticleRepository

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.enishop", appContext.packageName)
    }

    @Test
    fun testArticleDetailScreen(){
        val article = ArticleRepository.getArticle(1)

        composeTestRule.setContent {
            if (article != null) {
                ArticleDetailScreen(
                    navController = rememberNavController(),
                    article = article)
            }
        }

        composeTestRule
            .onNodeWithTag("prixText")
            .assertTextEquals("Prix : 109.95 €")

    }
}