package com.luteh.ecommerce.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.luteh.ecommerce.domain.model.ProductFilterModel
import com.luteh.ecommerce.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    private val queryProductFlow = MutableStateFlow(
        ProductFilterModel(
            productName = ""
        )
    )

    val productPager = queryProductFlow.debounce(500).flatMapLatest { query ->
        productRepository.productsPagingDataSource(productName = query.productName)
            .cachedIn(viewModelScope)
    }

    private fun onQueryChanged(query: String) {
        _state.value =
            _state.value.copy(productFilterModel = _state.value.productFilterModel.copy(productName = query))
        queryProductFlow.value = queryProductFlow.value.copy(productName = query.ifBlank { "" })
    }

    fun processEvent(event: Event) {
        when (event) {
            is Event.OnChangeSearch -> onQueryChanged(event.value)
            is Event.OnClickApplyFilterButton -> {
            }

            Event.OnClickFilterIcon -> _state.value = _state.value.copy(showFilterSheet = true)
            Event.OnDismissFilterSheet -> _state.value = _state.value.copy(showFilterSheet = false)
        }
    }

    data class State(
        val showFilterSheet: Boolean = false,
        val productFilterModel: ProductFilterModel = ProductFilterModel(productName = "")
    )

    sealed interface Event {
        data class OnChangeSearch(val value: String) : Event
        data object OnClickFilterIcon : Event
        data class OnClickApplyFilterButton(
            val location: String,
            val fulltime: Boolean,
        ) : Event

        data object OnDismissFilterSheet : Event
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
        data object NavigateToMainScreen : Effect
        data object LaunchGoogleSignIn : Effect
    }
}