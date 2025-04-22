package com.example.api_interfaces.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.example.api_interfaces.app.api.RetrofitClient
import com.example.api_interfaces.app.api.dtos.Direccion
import com.example.api_interfaces.app.api.dtos.LoginUsuarioDTO
import com.example.api_interfaces.app.api.dtos.TareaAddADTO
import com.example.api_interfaces.app.api.dtos.TareaAddNDTO
import com.example.api_interfaces.app.api.dtos.TareaDTO
import com.example.api_interfaces.app.api.dtos.UsuarioRegisterDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Clase encargada de almacenar todos los datos del programa
 */
class MyViewModel : ViewModel() {

    private val _tareas = MutableLiveData<List<TareaDTO>>()
    val tareas :LiveData<List<TareaDTO>> = _tareas

    fun update(){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAll(_token.value!!)
                if (response.isSuccessful){
                    _opRes.value=true
                    _msg.value = response.message()
                    _tareas.value= response.body()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
        }
    }

    //El nombre de la tarea
    private val _tName = MutableLiveData<String>()
    val tName : LiveData<String> = _tName

    //El nuevo nombre de la tarea
    private val _tNName = MutableLiveData<String>()
    val tNName : LiveData<String> = _tNName

    //La descripción de la tarea
    private val _desc = MutableLiveData<String>()
    val desc : LiveData<String> = _desc

    //El nombre de usuario, para las tareas
    private val _tUsername = MutableLiveData<String>()
    val tUsername : LiveData<String> = _tUsername

    //El nombre de usuario para el login o registro
    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    //La contraseña
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    //La repetición de la contraseña
    private val _passwordRepeat = MutableLiveData("")
    val passwordRepeat: LiveData<String> = _passwordRepeat

    //El nombre del usuario
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    //El apellido del usuario
    private val _surname = MutableLiveData("")
    val surname: LiveData<String> = _surname

    //La calle
    private val _calle = MutableLiveData("")
    val calle: LiveData<String> = _calle

    //El numero
    private val _num = MutableLiveData("")
    val num: LiveData<String> = _num

    //El municipio
    private val _muni = MutableLiveData("")
    val muni: LiveData<String> = _muni

    //La provincia
    private val _prov = MutableLiveData("")
    val prov: LiveData<String> = _prov

    //El Codigo postal
    private val _cp = MutableLiveData("")
    val cp: LiveData<String> = _cp

    //El Rol del usuario
    private val _rol = MutableLiveData("")
    val rol : LiveData<String> = _rol

    //El estado de las alertas
    private val _dismissed = MutableLiveData(false)
    val dismissed: LiveData<Boolean> = _dismissed

    //Metodo para cambiar el nombre de usuario
    fun changeUser(newUser: String) {
        _username.value = newUser
    }

    //Metodo para cambiar la contraseña
    fun changePassword(newPassword: String) {
        _password.value = newPassword
    }

    //Metodo para cambiar la repeticion de contraseña
    fun changePassRepeat(new: String) {
        _passwordRepeat.value = new
    }

    //Metodo para cambiar el nombre del usuario
    fun changeName(new: String) {
        _name.value = new
    }

    //Metodo para cambiar el apellido del usuario
    fun changeSurname(new: String) {
        _surname.value = new
    }

    //Metodo para cambiar la calle
    fun changeCalle(new: String) {
        _calle.value = new
    }

    //Metodo para cambiar el numero
    fun changeNum(new: String) {
        _num.value = new
    }

    //Metodo para cambiar la provincia
    fun changeProv(new: String) {
        _prov.value = new
    }

    //Metodo para cambiar el municipio
    fun changeMuni(new: String) {
        _muni.value = new
    }

    //Metodo para cambiar el codigo postal
    fun changeCP(new: String) {
        _cp.value = new
    }

    //Metodo para quitar las alertas
    fun dismiss() {
        _dismissed.value = true
    }

    //Estado del login
    private val _loginResult = MutableLiveData("notlogged")
    val loginResult: LiveData<String> = _loginResult

    //Metodo para cambiar el estado del login
    fun changeLogginResult(result: String) {
        _loginResult.value=result
        _dismissed.value = true
        _dismissed.value = false
    }

    //Estado del registro
    private val _registerResult = MutableLiveData("")
    val registerResult:LiveData<String> = _registerResult

    //Metodo para cambiar el estado del registro
    fun changeRegisterResult(result: String) {
        _registerResult.value=result
        changeLogginResult("")
    }

    //El token de usuario
    private val _token = MutableLiveData("")
    val token: LiveData<String> = _token


    //El mensaje de error
    private val _error = MutableLiveData("")
    val error : LiveData<String> = _error


    //Metodo para logear a un usuario
    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.login(LoginUsuarioDTO(username, password))
                if (response.isSuccessful) {
                    _token.value= response.body()?.token
                    _rol.value = JWT.decode(token.value).getClaim("roles").asString()
                    _token.value  = "bearer ${_token.value}"
                    changeLogginResult("logged")
                } else {
                    _error.value = response.errorBody()?.string()
                    changeLogginResult("errorLogin")
                }
            } catch (e: Exception) {
                _error.value = e.message
                changeLogginResult("error")
            }
            _loading.value=false
        }
    }

    //Metodo para registrar un usuario
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
            _loading.value=true
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
                _error.value = e.message
                changeRegisterResult("Not OK")
            }
            _loading.value=false
        }
    }

    //Resultado de la operacion anterior
    private val _opRes = MutableLiveData<Boolean>()
    val opRes : LiveData<Boolean> = _opRes

    //Mensaje de la operación anterior
    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    //Metodo para insertar tareas desde usuario normal
    fun insertTareaN(tarea:TareaAddNDTO, token:String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.insertN(tarea,token)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Metodo para insertar tareas desde usuario administrador
    fun insertTareaA(tarea: TareaAddADTO, token:String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.insertA(tarea,token)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Metodo para actualizar tareas desde usuario normal
    fun updateTareaN(token: String,tarea:String,newTarea:TareaAddNDTO){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.updateN(token,tarea,newTarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Metodo para actualizar tareas desde usuario administrador
    fun updateTareaA(token: String,tarea:String,newTarea:TareaAddADTO){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.updateA(token,tarea,newTarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }


    //Metodo para completar tareas desde usuario normal
    fun completeTareaN(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.completeN(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }


    //Metodo para completar tareas desde usuario administrador
    fun completeTareaA(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.completeA(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }


    //Metodo para descompletar tareas desde usuario normal
    fun uncompleteTareaN(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.uncompleteN(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }


    //Metodo para descompletar tareas desde usuario administrador
    fun uncompleteTareaA(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.uncompleteA(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Metodo para borrar  tareas desde usuario normal
    fun deleteTareaN(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.deleteN(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Metodo para borrar tareas desde usuario administrador
    fun deleteTareaA(token: String,tarea: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                val response = RetrofitClient.api.deleteA(token,tarea)
                if (response.isSuccessful) {
                    _opRes.value=true
                    _msg.value = response.body()?.tarea.toString()
                    update()
                } else {
                    _error.value = response.errorBody()?.string()
                    _opRes.value=false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _opRes.value=false
            }
            _loading.value=false
        }
    }

    //Variable para almacenar los componentes
    private val _comps = MutableLiveData(listOf<String>())
    val comps: LiveData<List<String>> = _comps

    //Variable para almacenar la operación a realizar
    private val _screen = MutableLiveData("")
    val screen : LiveData<String> = _screen

    //Metodo para cambiar de operación
    fun changeScreen(screen: String){
        _screen.value=screen
        _comps.value = checkComponents(screen)
    }

    //Metodo para saber que componentes se deben mostrar
    private fun checkComponents(screen: String): List<String> {
        return when (screen) {
            "insertN" -> listOf("name", "descrip")
            "insertA" -> listOf("name", "descrip", "username")
            "updateN" -> listOf("name", "newName", "descrip")
            "updateA" -> listOf("name", "newName", "descrip", "username")
            else -> emptyList()
        }
    }

    //Metodo para reiniciar todos los valores
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
        _tName.value=""
        _tNName.value=""
        _tUsername.value=""
        _desc.value=""
        _rol.value=""
    }

    //Metodo para reiniciar el valor del nombre de la tarea
    fun changeTName(new: String) {
        _tName.value=new
    }

    //Metodo para reiniciar el valor del nombre nuevo de la tarea
    fun changeTNName(it: String) {
        _tNName.value=it
    }

    //Metodo para reiniciar el valor de la descripcion de la tarea
    fun changeDes(it: String) {
        _desc.value=it
    }

    //Metodo para reiniciar el valor del nombre de usuario
    fun changeTusername(it: String) {
        _tUsername.value=it
    }

    //Metodo para reiniciar el valor del mensaje asi como del resultado de la operación y el estado de las alertas
    fun clearMsg() {
        _msg.value = ""
        _opRes.value = false
        _dismissed.value = false
    }

    //Metodo para reiniciar el valor del mensaje de error asi como del resultado de la operación y el estado de las alertas
    fun clearError() {
        _error.value = ""
        _opRes.value = false
        _dismissed.value = false
    }

    fun resetOP() {
        _tName.value=""
        _tNName.value=""
        _desc.value=""
        _tUsername.value=""
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

}