package com.luteh.ecommerce.ui.screen.register.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteh.ecommerce.R
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.ui.component.LoadingView
import com.luteh.ecommerce.ui.component.RoundedDropdownField
import com.luteh.ecommerce.ui.component.RoundedTextField
import com.luteh.ecommerce.ui.screen.register.RegisterViewModel
import com.luteh.ecommerce.ui.theme.LutehTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterForm(
    state: RegisterViewModel.State,
    processEvent: (event: RegisterViewModel.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        RoundedTextField(
            modifier = Modifier.fillMaxWidth(),
            valueText = state.email,
            onValueChange = {
                processEvent(
                    RegisterViewModel.Event.OnChangeEmailText(
                        it
                    )
                )
            },
            labelText = stringResource(R.string.email),
            singleLine = true,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundedTextField(
            modifier = Modifier.fillMaxWidth(),
            valueText = state.password,
            onValueChange = {
                processEvent(
                    RegisterViewModel.Event.OnChangePasswordText(
                        it
                    )
                )
            },
            labelText = stringResource(R.string.password),
            visualTransformation = PasswordVisualTransformation('*'),
            singleLine = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundedTextField(
            modifier = Modifier.fillMaxWidth(),
            valueText = state.name,
            onValueChange = {
                processEvent(
                    RegisterViewModel.Event.OnChangeNameText(
                        it
                    )
                )
            },
            labelText = stringResource(R.string.name),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundedTextField(
            modifier = Modifier.fillMaxWidth(),
            valueText = state.phone,
            onValueChange = {
                processEvent(
                    RegisterViewModel.Event.OnChangePhoneText(
                        it
                    )
                )
            },
            labelText = stringResource(R.string.phone_number),
            singleLine = true,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundedDropdownField(
            modifier = Modifier.fillMaxWidth(),
            items = state.getRolesResult.getOrNull().orEmpty(),
            selectedItemText = state.selectedRole?.name.orEmpty(),
            onItemSelected = {
                processEvent(RegisterViewModel.Event.OnSelectRoleOption(it))
            },
            labelText = stringResource(R.string.your_role),
            dropdownItemText = { text, _ ->
                Text(text = text.name)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        state.registerResult.let { registerResult ->
            when (registerResult) {
                ResultState.Loading -> LoadingView()
                else -> {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = state.enableRegisterButton,
                        onClick = { processEvent(RegisterViewModel.Event.OnClickRegisterButton) }
                    ) {
                        Text(text = stringResource(R.string.register))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RegisterFormPreview() {
    LutehTheme {
        RegisterForm(RegisterViewModel.State(), {})
    }
}