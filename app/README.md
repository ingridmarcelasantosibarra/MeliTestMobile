# Requerimientos Tecnicos

1. Construida con Android Studio Chipmunk 2021.2.1 Patch 1
2. Minimo SDK de Android version API 26.
3. Target SDK y compile SDK 33

# Bibliotecas usadas

1. Coil 2.2.2
2. Coroutines 1.6.4
3. Dagger Hilt 2.44
4. Retrofit 2.9.0
5. Lifecycle Live Data 2.5.0
6. Moshi 2.9.0
7. Navigation compose 2.5.3
8. Junit 4.13.2
9. Core testing 2.2.0
10. Mockk 1.12.1
11. Kotlinx Coroutines Test 1.5.2
12. Compose 1.2.1
13. Accompanist pager 0.22.0-rc

# Propuesta

La aplicacion implementa el patron MVVM propuesto en la Arquitectura de Componentes de Google, se divide en tres partes junto con los casos de uso.

1. Modelo: Encargo de la logica de datos, en la cual hacemos uso de los repositorios que sera el encargado de proporcionar los datos y asi mismo el encargado de hacer la solicitud de datos.

2. Vista: Interfaz de usuario (UI). Es la encargada de la interaccion con el usuario.

3. ViewModel: Es la encargada de solicitar datos al caso de uso y prepararlos para ser enviados a la vista. Tambien se hace uso de LiveData o los stateFlow donde podemos subscribimos a un estado y escuchar cuando este se actualice.

4. Caso de uso: Encargado de la logica de negocio.

La aplicacion tambien se enfoca en hacer uso de Los principio SOLID, para poder desarrollar un software de calidad, a continuacion se hace mencion de los principios que se estan implementando. 
1. Principio de responsabilidad unica, con el fin de que las clases realicen una unica cosa.
2. Segregacion de interfaces, con el fin de tener los modulos desacoplados y tambien tener los metodos relevantes para las clases que implementen la interfaz.
3. Inversion de dependencias, con el fin de tomar las dependencias de una clase y proporcionarlas en lugar de hacer que la instancia de la clase las obtenga por su cuenta. Haciendo uso de la biblioteca de Hilt.


La aplicacion implementa Clean Arquitecture, la cual se divide en tres capas o tres modulos que serian:
1. Presentation: Encargada de contener los elementos que nos permiten mostrar informacion a usuario y tambien recibir datos, en la cual encontramos las funciones correspondientes con compose en el caso de que se haga uso de este, adicional a esto encontramos el viewmodel y la clase de estado (la cual observamos para poder realizar la actualizacion de nuestra vista)
2. Domain: es la encargada de contener la logica de negocio y de la aplicacion, donde se tienen las clases de dominio como son en este caso las entidades, y tambien los componentes de caso de uso y interfaz.
3. Data: es la encargada de incluir las dependencias de networking y persistencia de datos, en este caso retrofit, los modelos correspondientes a data, y las implementacion de los repositorios.

La aplicacion implemente el sistema de vista declarativo Jetpack compose

Todo esto se implementa con el fin de implementar buenas practicas de desarrollo, poder tener una aplicacion escalable, tambien nos permite poder tener test unitarios y pruebas de instrumentacion.

