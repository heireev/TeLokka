package com.reev.telokkaapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.reev.telokkaapp.ui.navigation.Screen
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeLokkaAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            TeLokkaAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                TeLokkaApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, currentRoute)
    }
}