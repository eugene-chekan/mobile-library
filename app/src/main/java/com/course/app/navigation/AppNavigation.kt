package com.course.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.course.app.LibraryApplication
import com.course.app.presentation.LibraryViewModel
import com.course.app.presentation.screens.ItemDetailScreen
import com.course.app.presentation.screens.ItemEditScreen
import com.course.app.presentation.screens.LibraryListScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val factory = (context.applicationContext as LibraryApplication).container.viewModelFactory
    val viewModel: LibraryViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Routes.LIST) {

        composable(Routes.LIST) {
            LibraryListScreen(
                uiState = uiState,
                onItemClick  = { id -> navController.navigate(Routes.detail(id)) },
                onAddClick   = { navController.navigate(Routes.addNew()) },
                onToggleView = { viewModel.toggleViewMode() }
            )
        }

        composable(Routes.DETAIL) { entry ->
            val itemId = entry.arguments?.getString("itemId")?.toIntOrNull() ?: return@composable
            val item = uiState.items.firstOrNull { it.id == itemId } ?: return@composable
            ItemDetailScreen(
                item     = item,
                onBack   = { navController.popBackStack() },
                onEdit   = { navController.navigate(Routes.edit(itemId)) },
                onDelete = { viewModel.delete(item); navController.popBackStack() }
            )
        }

        composable(Routes.EDIT) { entry ->
            val itemId = entry.arguments?.getString("itemId")?.toIntOrNull() ?: 0
            val item = uiState.items.firstOrNull { it.id == itemId }
            ItemEditScreen(
                item   = item,
                onSave = { updated -> viewModel.save(updated); navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
