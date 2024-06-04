package com.app.d2vassigment.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.domain.usecase.LoginUseCase
import com.app.d2vassigment.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserViewState {
    object Loading : UserViewState()
    data class Success(val user: UserModel) : UserViewState()
    data class Error(val message: String) : UserViewState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _userState = MutableLiveData<UserViewState>()
    val userState: LiveData<UserViewState> = _userState

    fun signUp(user: UserModel) {
        viewModelScope.launch {
            _userState.value = UserViewState.Loading
            val success = signUpUseCase.execute(user)
            if (success) {
                _userState.value = UserViewState.Success(user)
            } else {
                _userState.value = UserViewState.Error("User name already exist! Try different user name")
            }
        }
    }

    fun login(user: UserModel) {
        viewModelScope.launch {
            _userState.value = UserViewState.Loading
            val user = loginUseCase.execute(user.username, user.password)
            if (user != null) {
                _userState.value = UserViewState.Success(user)
            } else {
                _userState.value = UserViewState.Error("User name or password is incorrect")
            }
        }
    }
}
