package com.luteh.ecommerce.ui.screen.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.luteh.ecommerce.R
import com.luteh.ecommerce.common.AuthResultContract
import com.luteh.ecommerce.common.ResultState

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    onNavigateToMainScreen: () -> Unit,
    googleSignInClient: GoogleSignInClient,
    onNavigateToRegisterScreen: () -> Unit,
) {
    val context = LocalContext.current
    val state = vm.state.collectAsState()

    val authResultLauncher = rememberLauncherForActivityResult(
        contract = AuthResultContract(googleSignInClient)
    ) { result ->
        try {
            val account = result?.getResult(ApiException::class.java)
            if (account != null) {
                vm.processEvent(LoginViewModel.Event.OnSuccessGoogleSignIn(account))
            } else {
                vm.processEvent(LoginViewModel.Event.OnFailureGoogleSignIn("Google sign in failed"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            vm.processEvent(LoginViewModel.Event.OnFailureGoogleSignIn(e.message.toString()))
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                is LoginViewModel.Effect.ShowToast -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()

                LoginViewModel.Effect.LaunchGoogleSignIn -> {
                    authResultLauncher.launch(1)
                }

                LoginViewModel.Effect.NavigateToMainScreen -> onNavigateToMainScreen()
                LoginViewModel.Effect.NavigateToRegisterScreen -> onNavigateToRegisterScreen()
            }
        }
    }

    Scaffold { paddingValue ->
        Box(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state.value.loginState) {
                ResultState.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.value.email,
                            onValueChange = {
                                vm.processEvent(
                                    LoginViewModel.Event.OnChangeEmailText(
                                        it
                                    )
                                )
                            },
                            label = {
                                Text(text = stringResource(R.string.email))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.value.password,
                            onValueChange = {
                                vm.processEvent(
                                    LoginViewModel.Event.OnChangePasswordText(
                                        it
                                    )
                                )
                            },
                            label = {
                                Text(text = stringResource(R.string.password))
                            }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            onClick = { vm.processEvent(LoginViewModel.Event.OnClickLoginButton) }
                        ) {
                            Text(text = stringResource(R.string.login))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = stringResource(R.string.don_t_have_an_account))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = stringResource(R.string.register_here),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable {
                                        vm.processEvent(LoginViewModel.Event.OnClickRegisterButton)
                                    },
                                color = Color.Blue,
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.or),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        GoogleSignInButton(
                            onClick = { vm.processEvent(LoginViewModel.Event.OnClickGoogleSignInButton) }
                        )
                    }
                }
            }
        }
    }
}