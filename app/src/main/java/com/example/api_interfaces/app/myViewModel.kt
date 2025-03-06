package com.example.api_interfaces.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.example.api_interfaces.app.api.RetrofitClient
import com.example.api_interfaces.app.api.dtos.Direccion
import com.example.api_interfaces.app.api.dtos.LoginUsuarioDTO
import com.example.api_interfaces.app.api.dtos.UsuarioRegisterDTO
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _passwordRepeat = MutableLiveData("")
    val passwordRepeat: LiveData<String> = _passwordRepeat

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData("")
    val surname: LiveData<String> = _surname

    private val _calle = MutableLiveData("")
    val calle: LiveData<String> = _calle

    private val _num = MutableLiveData("")
    val num: LiveData<String> = _num

    private val _muni = MutableLiveData("")
    val muni: LiveData<String> = _muni

    private val _prov = MutableLiveData("")
    val prov: LiveData<String> = _prov

    private val _cp = MutableLiveData("")
    val cp: LiveData<String> = _cp

    private val _rol = MutableLiveData("")
    val rol : LiveData<String> = _rol

    private val _dismissed = MutableLiveData(false)
    val dismissed: LiveData<Boolean> = _dismissed

    fun changeUser(newUser: String) {
        _username.value = newUser
    }

    fun changePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun changePassRepeat(new: String) {
        _passwordRepeat.value = new
    }

    fun changeName(new: String) {
        _name.value = new
    }

    fun changeSurname(new: String) {
        _surname.value = new
    }

    fun changeCalle(new: String) {
        _calle.value = new
    }

    fun changeNum(new: String) {
        _num.value = new
    }

    fun changeProv(new: String) {
        _prov.value = new
    }

    fun changeMuni(new: String) {
        _muni.value = new
    }

    fun changeCP(new: String) {
        _cp.value = new
    }

    fun dismiss() {
        _dismissed.value = !_dismissed.value!!
    }

    private val _loginResult = MutableLiveData("notlogged")
    val loginResult: LiveData<String> = _loginResult

    fun changeLogginResult(result: String) {
        _loginResult.value=result
        _dismissed.value = true
        _dismissed.value = false
    }

    private val _registerResult = MutableLiveData("")
    val registerResult:LiveData<String> = _registerResult

    fun changeRegisterResult(result: String) {
        _registerResult.value=result
        changeLogginResult("")
    }

    private val _token = MutableLiveData("")
    val token: LiveData<String> = _token

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.login(LoginUsuarioDTO(username, password))
                if (response.isSuccessful) {
                    _token.value= response.body()?.token
                    _rol.value = JWT.decode(token.value).getClaim("roles").asString()
                    changeLogginResult("logged")
                } else {
                    _error.value = response.errorBody()?.string()
                    changeLogginResult("errorLogin")
                }
            } catch (e: Exception) {
                changeLogginResult("error")
            }
        }
    }


    private val _error = MutableLiveData("")
    val error : LiveData<String> = _error

    fun register(
        username:String,
        password: String,
        passwordRepeat: String,
        name:String,
        surname:String,
        calle:String,
        num:String,
        prov:String,
        mun:String,
        cp:String) {
        val usuario = UsuarioRegisterDTO(
            username,
            password,
            passwordRepeat,
            name,
            surname,
            direccion = Direccion(calle, num, mun, prov, cp),
            rol = "USER"
        )
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.register(usuario)
                if (response.isSuccessful){
                    changeRegisterResult("OK")
                    loginUser(username, password)
                }else{
                    _error.value = response.errorBody()?.string()
                    changeRegisterResult("Not OK")
                }
            }catch (e:Exception){
                changeRegisterResult(e.message!!)
            }
        }
    }

    private val _comps = MutableLiveData(listOf<String>())
    val comps: LiveData<List<String>> = _comps

    private val _screen = MutableLiveData("")
    val screen : LiveData<String> = _screen

    fun changeScreen(screen: String){
        _screen.value=screen
        _comps.value = checkComponents(screen)
    }

    private fun checkComponents(screen: String) : List<String>{
        val list = listOf<String>()
        when (screen) {
            "insertSelf" -> {
                listOf("name","descrip")
            }
            "insertA" -> {
                listOf("name","descrip","username")
            }
            "getSelf" -> {
                listOf("getSelf")
            }
            "getA" -> {
                listOf("username")
            }
            "updateN" -> {
                listOf("name","newName","descrip")
            }
            "updateA" -> {
                listOf("name","newName","descrip","username")
            }
            "completeN" -> {
                listOf("name")
            }
            "completeA" -> {
                listOf("name")
            }
            "uncompleteN" -> {
                listOf("name")
            }
            "uncompleteA" -> {
                listOf("name")
            }
            "deleteN" -> {
                listOf("name")
            }
            "deleteA" -> {
                listOf("name")
            }
            else -> {
                println("Error raro")
            }
        }

        return list
    }

    fun reset(){
        _username.value=""
        _password.value=""
        _passwordRepeat.value=""
        _name.value=""
        _surname.value=""
        _calle.value=""
        _num.value=""
        _muni.value=""
        _prov.value=""
        _cp.value=""
    }
}