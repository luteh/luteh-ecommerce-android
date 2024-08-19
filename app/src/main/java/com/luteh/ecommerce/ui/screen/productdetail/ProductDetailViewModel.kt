package com.luteh.ecommerce.ui.screen.productdetail

import androidx.lifecycle.viewModelScope
import com.luteh.ecommerce.common.BaseViewModel
import com.luteh.ecommerce.common.ResultState
import com.luteh.ecommerce.domain.model.ProductDetailModel
import com.luteh.ecommerce.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel<ProductDetailViewModel.State, ProductDetailViewModel.Event, ProductDetailViewModel.Effect>(
        State()
    ) {

    override fun processEvent(event: Event) {
        when (event) {
            Event.OnClickBackButton -> {}
        }
    }

    fun getProductById(id: String) {
        updateState {
            it.copy(getProductResult = ResultState.Loading)
        }
        viewModelScope.launch {
            productRepository.getProduct(id).fold(
                { exception ->
                    updateState {
                        it.copy(getProductResult = ResultState.Error(exception))
                    }
                },
                { data ->
                    updateState {
                        it.copy(getProductResult = ResultState.Success(data))
                    }
                }
            )
        }
    }

    data class State(
        val getProductResult: ResultState<ProductDetailModel> = ResultState.Idle
    )

    sealed interface Event {
        data object OnClickBackButton : Event
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}