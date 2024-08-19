package com.luteh.ecommerce.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luteh.ecommerce.R
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.ui.component.ErrorView
import com.luteh.ecommerce.ui.component.LoadingView
import com.luteh.ecommerce.ui.screen.register.component.RegisterForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(vm: RegisterViewModel = hiltViewModel(), onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val state = vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                RegisterViewModel.Effect.NavigateBack -> onNavigateBack()
                is RegisterViewModel.Effect.ShowToast -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.register),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            navigationIcon = {
                IconButton(onClick = { vm.processEvent(RegisterViewModel.Event.OnClickBackButton) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            state.value.getRolesResult.let { result ->
                when (result) {
                    is ResultState.Error -> ErrorView(message = result.exception.message.orEmpty()) {
                        vm.processEvent(RegisterViewModel.Event.OnClickRefreshButton)
                    }

                    is ResultState.Success -> RegisterForm(state.value, processEvent = { event ->
                        vm.processEvent(event)
                    })

                    else -> {
                        LoadingView(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}


