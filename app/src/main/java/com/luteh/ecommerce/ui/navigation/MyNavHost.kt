package com.luteh.ecommerce.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.luteh.ecommerce.ui.screen.login.LoginScreen
import com.luteh.ecommerce.ui.screen.main.MainScreen
import com.luteh.ecommerce.ui.screen.productdetail.ProductDetailScreen
import com.luteh.ecommerce.ui.screen.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Main

sealed class MainBottomNav(val route: String) {
    data object Home : MainBottomNav("home_menu")
    data object Profile : MainBottomNav("profile_menu")
}

@Serializable
data class ProductDetail(val id: String)

data class MainBottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    companion object {
        fun items(): List<MainBottomNavigationItem> {
            return listOf(
                MainBottomNavigationItem(
                    label = "Home",
                    icon = Icons.Filled.Home,
                    route = MainBottomNav.Home.route
                ),
                MainBottomNavigationItem(
                    label = "Profile",
                    icon = Icons.Filled.AccountCircle,
                    route = MainBottomNav.Profile.route
                ),
            )
        }
    }
}

@Composable
fun MyNavHost(
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient,
) {
    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(onNavigateToMainScreen = {
                navController.navigate(
                    route = Main,
                ) {
                    popUpTo(Login) {
                        inclusive = true
                    }
                }
            }, googleSignInClient = googleSignInClient, onNavigateToRegisterScreen = {
                navController.navigate(route = Register)
            })
        }
        composable<Main> {
            MainScreen(onNavigateToProductDetailScreen = { id ->
                navController.navigate(route = ProductDetail(id))
            }, onNavigateToLoginScreen = {
                navController.navigate(route = Login) {
                    popUpTo(Login) {
                        inclusive = true
                    }
                }
            })
        }
        composable<ProductDetail> {
            val productDetail = it.toRoute<ProductDetail>()
            ProductDetailScreen(id = productDetail.id, onPopBackStack = { navController.popBackStack() })
        }

        composable<Register> {
            RegisterScreen(onNavigateBack = {
                navController.popBackStack()
            })
        }
    }
}
